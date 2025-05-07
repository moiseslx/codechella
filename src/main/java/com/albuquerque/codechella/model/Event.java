package com.albuquerque.codechella.model;

import com.albuquerque.codechella.enums.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("event")
public class Event {

    @Id
    private Long id;
    private EventType type;
    private String title;
    private LocalDate dateEvent;
    private String description;

    public Event() {}

    public Event(Long id, EventType type, String title, LocalDate dateEvent, String description) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.dateEvent = dateEvent;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDate dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
