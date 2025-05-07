package com.albuquerque.codechella.repository;

import com.albuquerque.codechella.model.Event;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventRepository extends ReactiveCrudRepository<Event, Long> {
}
