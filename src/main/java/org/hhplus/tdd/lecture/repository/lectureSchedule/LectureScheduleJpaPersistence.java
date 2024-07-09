package org.hhplus.tdd.lecture.repository.lectureSchedule;

import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;
import org.hhplus.tdd.lecture.entity.LectureSchedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class LectureScheduleJpaPersistence implements LectureSchedulePersistence {

    private final LectureScheduleRepository repository;

    public LectureScheduleJpaPersistence(LectureScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public LectureScheduleDomain applyForLectureWithPessimisticLock(LectureScheduleDomain domain) {
        return LectureSchedule.toDomain(repository.findByScheduleIdWithPessimisticLock(domain.getScheduleId()));
    }

    @Override
    public LectureScheduleDomain findById(LectureScheduleDomain domain) {
        return LectureSchedule.toDomain(repository.findById(domain.getScheduleId()).orElseThrow(()
                -> new IllegalArgumentException("스케쥴이 존재하지 않습니다.")));
    }

    @Override
    public List<LectureScheduleDomain> findAll() {
        return LectureSchedule.toDomainList(repository.findAll());
    }

    @Override
    public LectureScheduleDomain save(LectureScheduleDomain domain) {
        return LectureSchedule.toDomain(repository.save(LectureSchedule.toEntity(domain)));
    }
}
