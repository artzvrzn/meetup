package org.modsen.service.api;

import org.modsen.dto.EventDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EventService {

    List<EventDto> findAll(Map<String, String> queryParams);

    EventDto findById(UUID id);

    void create(EventDto dto);

    void update(UUID id, EventDto eventDto);

    void delete(UUID id);
}
