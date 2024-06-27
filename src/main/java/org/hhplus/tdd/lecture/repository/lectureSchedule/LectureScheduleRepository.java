package org.hhplus.tdd.lecture.repository.lectureSchedule;

import org.hhplus.tdd.lecture.entity.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long> {

}
