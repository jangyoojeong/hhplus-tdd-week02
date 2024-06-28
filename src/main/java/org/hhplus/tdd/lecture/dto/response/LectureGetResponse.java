package org.hhplus.tdd.lecture.dto.response;

import lombok.*;
import org.hhplus.tdd.lecture.domain.result.LectureGetResult;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public static LectureGetResponse toResponse(LectureGetResult result) {
        return LectureGetResponse.builder()
                .scheduleId(result.getScheduleId())
                .lectureId(result.getLectureId())
                .lectureDate(result.getLectureDate())
                .capacity(result.getCapacity())
                .enrollmentCount(result.getEnrollmentCount())
                .build();
    }

    public static List<LectureGetResponse> toResponseList(List<LectureGetResult> lectures) {
        return lectures.stream()
                .map(LectureGetResponse::toResponse)
                .collect(Collectors.toList());
    }
}
