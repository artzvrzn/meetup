package org.modsen.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modsen.dao.api.EventRepository;
import org.modsen.dao.util.*;
import org.modsen.domain.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
    private final static LinkedList<SortParam> EMPTY_LINKED_LIST = new LinkedList<>();
    private final EntityManagerFactory emf;
    private final ObjectMapper objectMapper;

    @Override
    public List<Event> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Event> query = em.createQuery("from Event ", Event.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Event> findAll(Map<String, String> params) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Event> query = cb.createQuery(Event.class);
            Root<Event> root = query.from(Event.class);

            ParamsPredicateProvider paramsPredicateProvider =
                new EventParamsPredicateProvider(root, cb, objectMapper);
            query.where(paramsPredicateProvider.get(params));

            SortOrderProvider sortOrderProvider = new EventSortOrderProvider(root, cb);
            query.orderBy(sortOrderProvider.get(getSortParamsFromMap(params)));

            return em.createQuery(query).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Event findById(UUID id) {
        EntityManager em = emf.createEntityManager();
        try {
            return getEntityById(id, em);
        } finally {
            em.close();
        }
    }

    @Override
    public void create(Event event) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(event);
            UUID generatedId = event.getId();
            transaction.commit();
            log.info("Event with id {} has been created", generatedId);
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(UUID id, Event event) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Event entity = getEntityById(id, em);
            entity.setSubject(event.getSubject());
            entity.setOrganizer(event.getOrganizer());
            entity.setDescription(event.getDescription());
            entity.setLocation(event.getLocation());
            entity.setScheduledTime(event.getScheduledTime());
            transaction.commit();
            log.info("Event with id {} has been updated", id);
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(UUID id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Event entity = getEntityById(id, em);
            em.remove(entity);
            transaction.commit();
            log.info("Event with id {} has been deleted", id);
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private LinkedList<SortParam> getSortParamsFromMap(Map<String, String> params) {
        String expression = params.get("sort");
        if (expression == null || expression.isBlank()) {
            return EMPTY_LINKED_LIST;
        }
        LinkedList<SortParam> sortParams = new LinkedList<>();
        for (String exp: expression.split(",")) {
            sortParams.add(SortParam.fromExpression(exp));
        }
        return sortParams;
    }

    private Event getEntityById(UUID id, EntityManager em) {
        Event entity = em.find(Event.class, id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("Event with id %s doesn't exist", id));
        }
        return entity;
    }
}
