package com.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest //web 과 관련된 모든 bean 들이 등록됨
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc; //웹서버를 띄우지 않고 테스트할 수 있도록 필요한 가짜 객체를 만들어준다

    @Autowired
    ObjectMapper objectMapper;

    /**
     * perform - 요청을 전송하는 역할
     * andExpect - 응답을 검증
     * contentType - http 요청과 응답 데이터의 형식
     * accept - 브라우저에서 서버로 요청시 메세지에 담기는 헤더(설정한 데이터 형식으로 응답 해달라고 하는것)
     */
    @Test
    public void createEvent() throws Exception{
        Event event = Event.builder()
                .name("spring")
                .description("rest api")
                .beginEnrollmentDataTime(LocalDateTime.of(2022, 10, 30, 16, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 10, 31, 16, 0))
                .beginEventDateTime(LocalDateTime.of(2022, 11, 1, 16, 0))
                .endEventDataTime(LocalDateTime.of(2022, 11, 2, 16, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("부산 광안리")
                .build();

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))) //hypertext application language
                .andDo(print()) //요청 결과를 콘솔에 출력
                .andExpect(status().isCreated()) //201
                .andExpect(jsonPath("id").exists()); //id가 있는지 확인
    }


}
