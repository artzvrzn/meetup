package org.modsen.dao.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modsen.domain.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EventSortOrderProvider implements SortOrderProvider {
    private final Root<Event> root;
    private final CriteriaBuilder cb;

    @Override
    public Order[] get(LinkedList<SortParam> params) {
        List<Order> orders = new ArrayList<>();
        for (SortParam param: params) {
            Order order = getOrder(param.getField(), param.getDirection());
            if (order != null) {
                orders.add(order);
            }
        }
        return orders.isEmpty() ? new Order[] {defaultOrder()} : orders.toArray(new Order[0]);
    }

    private Order getOrder(String column, SortDirection direction) {
        if (direction == null) {
            throw new IllegalArgumentException("SortDirection is null");
        }
        if (SortDirection.ASC.equals(direction)) {
            return cb.asc(root.get(column));
        }
        return cb.desc(root.get(column));
    }

    private Order defaultOrder() {
        return cb.desc(root.get("scheduledTime"));
    }
}
