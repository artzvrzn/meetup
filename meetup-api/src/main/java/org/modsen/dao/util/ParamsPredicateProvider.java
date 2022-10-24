package org.modsen.dao.util;

import javax.persistence.criteria.Predicate;
import java.util.Map;

public interface ParamsPredicateProvider {
    Predicate get(Map<String, String> filters);
}
