package org.hhplus.tdd.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

}
