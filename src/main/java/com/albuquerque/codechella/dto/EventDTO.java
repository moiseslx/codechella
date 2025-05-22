package com.albuquerque.codechella.dto;

import com.albuquerque.codechella.enums.EventType;
import com.albuquerque.codechella.model.Event;

import java.time.LocalDate;

public record EventDTO(
    Long id,
    EventType type,
    String title,
    LocalDate dateEvent,
    String description
) {

    public EventDTO(Event event) {
        this(event.getId(), event.getType(), event.getTitle(), event.getDateEvent(), event.getDescription());
    }

    public Event toEvent() {
        return new Event(id, type, title, dateEvent, description);
    }
}
