package org.hhplus.tdd.lecture.repository.lectureSchedule;

import jakarta.persistence.LockModeType;
import org.hhplus.tdd.lecture.entity.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ls FROM LectureSchedule ls WHERE ls.scheduleId = :scheduleId")
    LectureSchedule findByScheduleIdWithPessimisticLock(@Param("scheduleId") Long scheduleId);
}
