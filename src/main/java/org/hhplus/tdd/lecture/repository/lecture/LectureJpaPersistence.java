package org.hhplus.tdd.lecture.repository.lecture;

import org.springframework.stereotype.Component;

@Component
public class LectureJpaPersistence implements LecturePersistence {

    private final LectureRepository lectureRepository;

    public LectureJpaPersistence(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

}
