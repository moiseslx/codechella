package com.albuquerque.codechella.controller;

import com.albuquerque.codechella.dto.EventDTO;
import com.albuquerque.codechella.enums.EventType;
import com.albuquerque.codechella.service.EventService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final Sinks.Many<EventDTO> sink;

    public EventController(EventService eventService)  {
        this.eventService = eventService;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventDTO> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<EventDTO> findById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @GetMapping("/{id}/translate/{language}")
    public Mono<String> getTranslate(@PathVariable Long id, @PathVariable String language) {
        return eventService.getTranslate(id, language);
    }

    @PostMapping
    public Mono<EventDTO> save(@RequestBody EventDTO eventDTO) {
        return eventService.saveOrUpdate(eventDTO)
                .doOnSuccess(sink::tryEmitNext);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return eventService.delete(id);
    }

    @GetMapping(value = "/type/{type}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventDTO> findByType(@PathVariable EventType type) {
        return Flux.merge(eventService.findByType(type), sink.asFlux())
                .delayElements(Duration.ofSeconds(5));
    }
}
