package org.hhplus.tdd.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lecture_schedule", uniqueConstraints = {@UniqueConstraint(columnNames = {"lecture_id", "lecture_date"})})
public class LectureSchedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;          // 일정ID

    @Column(name = "lecture_id")
    private Long lectureId;           // 특강ID

    @Column(name = "lecture_date")
    private LocalDate lectureDate;    // 특강일자

    @Column(name = "capacity")
    private int capacity;             // 특강정원

    @Column(name = "enrollment_count")
    private int enrollmentCount;      // 특강등록인원

    public LectureSchedule(Long lectureId, LocalDate lectureDate, int capacity, int enrollmentCount) {
        this.lectureId = lectureId;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
        this.enrollmentCount = enrollmentCount;
    }

    public static LectureSchedule toEntity(LectureScheduleDomain domain) {
        return LectureSchedule.builder()
                .scheduleId(domain.getScheduleId())
                .lectureId(domain.getLectureId())
                .lectureDate(domain.getLectureDate())
                .capacity(domain.getCapacity())
                .enrollmentCount(domain.getEnrollmentCount())
                .build();
    }

    public static LectureScheduleDomain toDomain(LectureSchedule entity) {
        return LectureScheduleDomain.builder()
                .scheduleId(entity.getScheduleId())
                .lectureId(entity.getLectureId())
                .lectureDate(entity.getLectureDate())
                .capacity(entity.getCapacity())
                .enrollmentCount(entity.getEnrollmentCount())
                .build();
    }

    public static List<LectureScheduleDomain> toDomainList(List<LectureSchedule> entityList) {
        return entityList.stream()
                .map(LectureSchedule::toDomain)
                .collect(Collectors.toList());
    }
}
