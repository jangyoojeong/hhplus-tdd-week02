package org.hhplus.tdd.lecture.repository.lectureSchedule;

import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;
import org.hhplus.tdd.lecture.entity.LectureSchedule;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<LectureScheduleDomain> findAll() {
        return LectureSchedule.toDomainList(repository.findAll());
    }

    @Override
    public LectureScheduleDomain save(LectureScheduleDomain domain) {
        return LectureSchedule.toDomain(repository.save(LectureSchedule.toEntity(domain)));
    }
}
