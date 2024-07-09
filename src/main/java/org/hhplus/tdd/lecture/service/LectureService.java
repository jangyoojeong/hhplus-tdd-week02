package org.hhplus.tdd.lecture.service;

import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;
import org.hhplus.tdd.lecture.domain.RegistrationDomain;
import org.hhplus.tdd.lecture.domain.command.LectureApplicationCommand;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;
import org.hhplus.tdd.lecture.domain.result.LectureApplicationResult;
import org.hhplus.tdd.lecture.domain.result.LectureApplyResult;
import org.hhplus.tdd.lecture.domain.result.LectureGetResult;
import org.hhplus.tdd.lecture.repository.lecture.LecturePersistence;
import org.hhplus.tdd.lecture.repository.lectureSchedule.LectureSchedulePersistence;
import org.hhplus.tdd.lecture.repository.registration.RegistrationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LectureService {

    private static final Logger log = LoggerFactory.getLogger(LectureService.class);

    private final LecturePersistence lecturePersistence;
    private final LectureSchedulePersistence lectureSchedulePersistence;
    private final RegistrationPersistence registrationPersistence;

    public LectureService(LecturePersistence lecturePersistence, LectureSchedulePersistence lectureSchedulePersistence, RegistrationPersistence registrationPersistence) {
        this.lecturePersistence = lecturePersistence;
        this.lectureSchedulePersistence = lectureSchedulePersistence;
        this.registrationPersistence = registrationPersistence;
    }

    /**
     * 특강에 대한 신청을 처리하는 메서드
     *
     * @param command 신청할 특강의 정보를 담은 커멘드 객체
     * @return 처리된 신청 정보를 담은 도메인 객체
     */
    @Transactional
    public LectureApplyResult applyForLecture(LectureApplyCommand command) {

        // 동일한 신청자가 동일한 특강을 신청한 내용이 있는지 확인
        if (registrationPersistence.existsByUserIdAndScheduleId(RegistrationDomain.of(command))) {
            throw new RuntimeException("이미 신청완료된 특강입니다.");
        }

        // 스케쥴 정보 조회 (동시성 제어를 위한 비관적 Lock 설정 > 선착순)
        LectureScheduleDomain scheduleDomain = lectureSchedulePersistence.applyForLectureWithPessimisticLock(LectureScheduleDomain.of(command));

        if (scheduleDomain == null) {
            throw new NoSuchElementException("해당 스케쥴 정보가 존재하지 않습니다.");
        }

        // 특강등록인원 갱신
        scheduleDomain.checkEnrollmentCapacity();
        lectureSchedulePersistence.save(scheduleDomain);

        return LectureApplyResult.of(registrationPersistence.save(RegistrationDomain.of(command)));
    }

    /**
     * 특강 목록을 조회하는 메서드
     *
     * @param
     * @return 특강 목록을 담은 도메인 객체리스트
     */
    public List<LectureGetResult> getAllLectures() {
        return LectureGetResult.ofList(lectureSchedulePersistence.findAll());
    }

    /**
     * 특강 신청 완료 여부를 조회하는 메서드
     *
     * @param command 신청정보를 확인할 특강의 정보를 담은 커멘드 객체
     * @return 신청 성공 여부 (True, False)
     */
    public LectureApplicationResult getLectureApplicationStatus(LectureApplicationCommand command) {
        LectureApplicationResult result = new LectureApplicationResult(registrationPersistence.existsByUserIdAndScheduleId(RegistrationDomain.of(command)));
        return result;
    }
}
