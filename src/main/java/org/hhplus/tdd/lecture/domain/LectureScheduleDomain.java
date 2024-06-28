package org.hhplus.tdd.lecture.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureScheduleDomain {

    private Long scheduleId;              // 일정ID
    private Long lectureId;               // 특강ID
    private LocalDate lectureDate;        // 특강일자
    private int capacity;                 // 특강정원
    private int enrollmentCount;          // 특강등록인원

    public LectureScheduleDomain(Long lectureId, LocalDate lectureDate, int capacity, int enrollmentCount) {
        this.lectureId = lectureId;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
        this.enrollmentCount = enrollmentCount;
    }

    public static LectureScheduleDomain of(LectureApplyCommand command) {
        return LectureScheduleDomain.builder()
                .scheduleId(command.getScheduleId())
                .build();
    }
    
    // 특강등록인원 갱신
    public void checkEnrollmentCapacity() {

        if (this.getEnrollmentCount() >= this.getCapacity()) {
            throw new RuntimeException("수강정원이 초과되었습니다.");
        }

        this.setEnrollmentCount(this.getEnrollmentCount() + 1);
    }
}