package com.albuquerque.codechella.repository;

import com.albuquerque.codechella.enums.EventType;
import com.albuquerque.codechella.model.Event;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventRepository extends ReactiveCrudRepository<Event, Long> {
    Flux<Event> findByType(EventType type);
}
