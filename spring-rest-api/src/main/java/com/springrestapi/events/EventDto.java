package com.springrestapi.events;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto {

    private String name; //이름

    private String description; //설명

    private LocalDateTime beginEnrollmentDataTime; //등록 시작일시

    private LocalDateTime closeEnrollmentDateTime; //등록 종료일시

    private LocalDateTime beginEventDateTime; //시작일시

    private LocalDateTime endEventDataTime; //종료일시

    private String location; //장소 (없으면 온라인 모임)

    private int basePrice; //등록비

    private int maxPrice; //최대 등록비

    private int limitOfEnrollment; //참가 제한 수

}
