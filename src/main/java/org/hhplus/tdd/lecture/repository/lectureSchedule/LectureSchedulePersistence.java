package org.hhplus.tdd.lecture.repository.lectureSchedule;

import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;

import java.util.List;

public interface LectureSchedulePersistence {
    /**
     * 특강 스케쥴 단건 조회 (비관적락)
     * @param
     * @return 특강 도메인 객체
     */
    LectureScheduleDomain applyForLectureWithPessimisticLock(LectureScheduleDomain domain);

    /**
     * 특강 스케쥴 목록 조회
     * @param
     * @return 특강 목록 도메인 객체 리스트
     */
    List<LectureScheduleDomain> findAll();

    /**
     * 특강 스케쥴 저장
     * @param domain 저장할 특강 정보 도메인 객체
     * @return 저장된 특강 정보의 도메인 객체
     */
    LectureScheduleDomain save(LectureScheduleDomain domain);
}
