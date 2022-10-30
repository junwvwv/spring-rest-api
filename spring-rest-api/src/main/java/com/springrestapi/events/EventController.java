package com.springrestapi.events;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event newEvent = eventRepository.save(event);
        /**
         * linkTo - 컨트롤러 클래스를 가리키는 webMvcLinkBuilder 객체를 반환
         * methodOn - 실제로 메소드를 호출하지 않고 가짜로 호출하는 컨트롤러 프록시 클래스를 생성
         * slash - uri 의 "/" 기능
         */
        //URI createdUri =  linkTo(methodOn(EventController.class).createEvent(null)).slash("{id}").toUri();
        URI createdUri =  linkTo(EventController.class).slash(newEvent.getId()).toUri();
        event.setId(10);

        return ResponseEntity.created(createdUri).body(event);
    }

}
