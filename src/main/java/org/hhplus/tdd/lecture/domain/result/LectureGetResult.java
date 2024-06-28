package org.hhplus.tdd.lecture.domain.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureGetResult {

    private Long scheduleId;              // 일정ID
    private Long lectureId;               // 특강ID
    private LocalDate lectureDate;        // 특강일자
    private int capacity;                 // 특강정원
    private int enrollmentCount;          // 특강등록인원

    public static LectureGetResult of(LectureScheduleDomain domain) {
        return LectureGetResult.builder()
                .scheduleId(domain.getScheduleId())
                .lectureId(domain.getLectureId())
                .lectureDate(domain.getLectureDate())
                .capacity(domain.getCapacity())
                .enrollmentCount(domain.getEnrollmentCount())
                .build();
    }

    public static List<LectureGetResult> ofList(List<LectureScheduleDomain> lectures) {
        return lectures.stream()
                .map(LectureGetResult::of)
                .collect(Collectors.toList());
    }
}
   