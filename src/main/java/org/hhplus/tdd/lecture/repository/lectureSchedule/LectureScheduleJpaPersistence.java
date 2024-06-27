package org.hhplus.tdd.lecture.repository.lectureSchedule;

import org.springframework.stereotype.Component;

@Component
public class LectureScheduleJpaPersistence implements LectureSchedulePersistence {

    private final LectureScheduleRepository repository;

    public LectureScheduleJpaPersistence(LectureScheduleRepository repository) {
        this.repository = repository;
    }

}
