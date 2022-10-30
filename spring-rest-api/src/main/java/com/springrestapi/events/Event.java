package com.springrestapi.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter @Setter
@Entity
/**
 * 엔티티에 @data 사용하지 않는 것이 좋다
 * EqualsAndHashCode 가 모든 필드를 참조하기 때문
 */
public class Event {
    /**
     * 경매 이벤트
     */
    @Id
    @GeneratedValue
    private Integer id; //식별 id

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

    private boolean offline; //오프라인 유무

    private boolean free; //무료 유무

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus; //이벤트 상태

}
