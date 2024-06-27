package org.hhplus.tdd.lecture.repository.lecture;

import org.hhplus.tdd.lecture.domain.LectureDomain;
import org.hhplus.tdd.lecture.entity.Lecture;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LectureJpaPersistence implements LecturePersistence {

    private final LectureRepository lectureRepository;

    public LectureJpaPersistence(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public LectureDomain findById(LectureDomain lectureDomain) {
        return Lecture.toDomain(lectureRepository.findById(lectureDomain.getLectureId()).orElseThrow(()
                -> new IllegalArgumentException("특강이 존재하지 않습니다.")));
    }

    @Override
    public List<LectureDomain> findAll() {
        return Lecture.toDomainList(lectureRepository.findAll());
    }

    @Override
    public LectureDomain save(LectureDomain lectureDomain) {
        return Lecture.toDomain(lectureRepository.save(Lecture.toEntity(lectureDomain)));
    }

}
