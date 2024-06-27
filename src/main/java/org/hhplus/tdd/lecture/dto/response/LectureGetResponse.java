package org.hhplus.tdd.lecture.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureGetResponse {

    private Long scheduleId;              // 일정ID
    private Long lectureId;               // 특강ID
    private LocalDate lectureDate;        // 특강일자
    private int capacity;                 // 특강정원
    private int enrollmentCount;          // 특강등록인원
}
