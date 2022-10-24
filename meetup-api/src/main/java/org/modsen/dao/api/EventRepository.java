package org.modsen.dao.api;

import org.modsen.domain.Event;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EventRepository {

    List<Event> findAll();

    List<Event> findAll(Map<String, String> params);

    Event findById(UUID id);

    void create(Event event);

    void update(UUID id, Event event);

    void delete(UUID id);
}
