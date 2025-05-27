package com.albuquerque.codechella;

import com.albuquerque.codechella.dto.EventDTO;
import com.albuquerque.codechella.enums.EventType;
import com.albuquerque.codechella.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodechellaApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void newEvent() {
		Event event = new Event(null, EventType.CONCERTO, "Beethoven", LocalDate.of(2025, 6, 10), "Concerto de Beethoven");
		webTestClient.post()
				.uri("/event")
				.bodyValue(event)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(Event.class)
				.value(response -> {
					assertNotNull(response.getId());
					assertEquals(event.getType(), response.getType());
					assertEquals(event.getTitle(), response.getTitle());
					assertEquals(event.getDateEvent(), response.getDateEvent());
					assertEquals(event.getDescription(), response.getDescription());
				});
	}

	@Test
	void getEvent() {
		Event event = new Event(13L, EventType.SHOW, "The Weeknd", LocalDate.of(2025, 11, 2), "Um show eletrizante ao ar livre com muitos efeitos especiais.");
		webTestClient.get()
				.uri("/event")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBodyList(Event.class)
				.value(response -> {
					EventDTO eventDTO = new EventDTO(response.get(12));
					assertEquals(event.getId(), eventDTO.id());
					assertEquals(event.getType(), eventDTO.type());
					assertEquals(event.getTitle(), eventDTO.title());
					assertEquals(event.getDateEvent(), eventDTO.dateEvent());
					assertEquals(event.getDescription(), eventDTO.description());
				});
	}
}
