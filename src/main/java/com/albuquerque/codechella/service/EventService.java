package com.albuquerque.codechella.service;

import com.albuquerque.codechella.dto.EventDTO;
import com.albuquerque.codechella.enums.EventType;
import com.albuquerque.codechella.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Flux<EventDTO> findAll() {
        return eventRepository.findAll().map(EventDTO::new);
    }

    public Mono<EventDTO> findById(Long id) {
        return eventRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventDTO::new);
    }

    public Mono<EventDTO> saveOrUpdate(EventDTO eventDTO) {
        if (eventDTO.id() != null) {
            return eventRepository.findById(eventDTO.id())
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                    .flatMap(eventoExistente -> {
                        eventoExistente.setType(eventDTO.type());
                        eventoExistente.setTitle(eventDTO.title());
                        eventoExistente.setDateEvent(eventDTO.dateEvent());
                        eventoExistente.setDescription(eventDTO.description());
                        return eventRepository.save(eventoExistente);
                    })
                    .map(EventDTO::new);
        }

        return eventRepository.save(eventDTO.toEvent())
                    .map(EventDTO::new);
    }


    public Mono<Void> delete(Long id) {
        return eventRepository.deleteById(id);
    }

    public Flux<EventDTO> findByType(EventType type) {
        return eventRepository.findByType(type).map(EventDTO::new);
    }
}
