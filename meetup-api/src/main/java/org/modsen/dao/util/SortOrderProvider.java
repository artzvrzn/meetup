package org.modsen.dao.util;

import javax.persistence.criteria.Order;
import java.util.LinkedList;

public interface SortOrderProvider {
    Order[] get(LinkedList<SortParam> params);
}
