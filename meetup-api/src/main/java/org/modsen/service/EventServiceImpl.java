package org.modsen.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modsen.dao.api.EventRepository;
import org.modsen.domain.Event;
import org.modsen.dto.EventDto;
import org.modsen.service.api.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    @Override
    public List<EventDto> findAll(Map<String, String> queryParams) {
        List<Event> entities;
        if (queryParams == null || queryParams.isEmpty()) {
            entities = eventRepository.findAll();
        } else {
            entities = eventRepository.findAll(queryParams);
        }
        return entities
            .stream()
            .map(e -> mapper.map(e, EventDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(UUID id) {
        return mapper.map(eventRepository.findById(id), EventDto.class);
    }

    @Override
    public void create(EventDto dto) {
        Event entity = mapper.map(dto, Event.class);
        eventRepository.create(entity);
    }

    @Override
    public void update(UUID id, EventDto dto) {
        Event entity = mapper.map(dto, Event.class);
        eventRepository.update(id, entity);
    }

    @Override
    public void delete(UUID id) {
        eventRepository.delete(id);
    }
}
