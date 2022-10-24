package org.modsen.controller.rest;

import lombok.RequiredArgsConstructor;
import org.modsen.dto.EventDto;
import org.modsen.service.api.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAll(@RequestParam(required = false) Map<String, String> params) {
        return eventService.findAll(params);
    }

    @GetMapping(value = {"/{id}", "/{id}/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EventDto getById(@PathVariable("id") UUID id) {
        return eventService.findById(id);
    }

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid EventDto dto) {
        eventService.create(dto);
    }

    @PutMapping(
        value = {"/{id}", "/{id}/"},
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") UUID id, @RequestBody @Valid EventDto dto) {
        eventService.update(id, dto);
    }

    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        eventService.delete(id);
    }
}
