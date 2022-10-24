package org.modsen.dao.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modsen.domain.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EventParamsPredicateProvider implements ParamsPredicateProvider {
    private static final String ORGANIZER_COL = "organizer";
    private static final String SUBJECT_COL = "subject";
    private static final String SCHEDULED_TIME_COL = "scheduledTime";
    private static final String ORGANIZER_PARAM = ORGANIZER_COL;
    private static final String SUBJECT_PARAM = SUBJECT_COL;
    private static final String TO_PARAM = "to";
    private static final String FROM_PARAM = "from";

    private Root<Event> root;
    private CriteriaBuilder cb;
    private ObjectMapper mapper;

    @Override
    public Predicate get(Map<String, String> params) {
        List<Predicate> predicates = new ArrayList<>();
        addDatePredicate(params, predicates);
        addOrganizerPredicate(params, predicates);
        addSubjectPredicate(params, predicates);
        return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(Predicate[]::new));
    }
    private void addDatePredicate(Map<String, String> params, List<Predicate> predicates) {
        LocalDateTime from = getDateFromParams(params, FROM_PARAM);
        LocalDateTime to = getDateFromParams(params, TO_PARAM);
        if (from != null && to != null) {
            predicates.add(cb.between(root.get(SCHEDULED_TIME_COL), from, to));
        } else {
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(SCHEDULED_TIME_COL), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get(SCHEDULED_TIME_COL), to));
            }
        }
    }

    private LocalDateTime getDateFromParams(Map<String, String> params, String key) {
        String stringDateTime = params.get(key);
        if (stringDateTime != null && !stringDateTime.isBlank()) {
            try {
                return mapper.convertValue(stringDateTime, LocalDateTime.class);
            } catch (IllegalArgumentException exc) {
                throw new IllegalArgumentException("Wrong time format");
            }
        }
        return null;
    }

    private void addSubjectPredicate(Map<String, String> params, List<Predicate> predicates) {
        String subject = params.get(SUBJECT_PARAM);
        if (subject != null) {
            predicates.add(stringLikePredicate(SUBJECT_COL, subject));
        }
    }

    private void addOrganizerPredicate(Map<String, String> params, List<Predicate> predicates) {
        String organizer = params.get(ORGANIZER_PARAM);
        if (organizer != null) {
            predicates.add(stringLikePredicate(ORGANIZER_COL, organizer));
        }
    }

    private Predicate stringLikePredicate(String key, String value) {
        if ("null".equals(value)) {
            return cb.isNull(root.get(key));
        }
        return cb.like(cb.upper(root.get(key)), ("%" + value + "%").toUpperCase());
    }

}
