package com.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@WebMvcTest //web 과 관련된 모든 bean 들이 등록됨
@SpringBootTest //모든 bean 들을 등록
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc; //테스트에 필요한 가짜 객체를 만들어준다

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception{
        Event event = Event.builder()
                .id(100)
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
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        //Mockito.when(eventRepository.save(event)).thenReturn(event); //엔티티 저장시 객체를 리턴(mocking)

        /**
         * perform - 요청을 전송하는 역할
         * andExpect - 응답을 검증
         * contentType - http 요청과 응답 데이터의 형식
         * accept - 브라우저에서 서버로 요청시 메세지에 담기는 헤더(설정한 데이터 형식으로 응답 해달라고 하는것)
         * andDo - 실행 결과를 처리
         * andExpect - 실행 결과를 검증
         *   ㄴ status - HTTP 상태 코드 검증
         *   ㄴ header - 응답 헤더의 상태 검증
         *   ㄴ cookie - 쿠키 상태 검증
         *   ㄴ content - 응답 본문 내용 검증
         *   ㄴ view - 컨트롤러가 반환한 뷰 이름 검증
         *   ㄴ forwardedUrl - 이동 대상의 경로를 검증
         *   ㄴ redirectedUrl - 리다이렉트 대상의 경로를 검증
         *   ㄴ model - spring mvc model 상태 검증
         *   ㄴ flash - 플래시 스코프의 상태 검증
         *   ㄴ request - 비동기 처리의 상태나 요청 스코프의 상태, 세션 스코프 상태 검증
         *   ㄴ jsonPath - json 응답 결과 검증
         * andReturn - 실행 결과를 반환
         */
        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))) //hypertext application language
                .andDo(print()) //요청 결과를 콘솔에 출력
                .andExpect(status().isCreated()) //201
                .andExpect(jsonPath("id").exists()) //id가 있는지 확인
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }


}
