package org.hhplus.tdd.lecture.repository.registration;

import org.hhplus.tdd.lecture.domain.RegistrationDomain;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RegistrationPersistence {
    /**
     * 특강 신청 정보를 저장
     * @param domain 저장할 등록 정보 도메인 객체
     * @return 저장된 등록 정보의 도메인 객체
     */
    RegistrationDomain save(RegistrationDomain domain);

    /**
     * 특강 신청 목록 조회
     * @param
     * @return 특강 신청 도메인 객체 리스트
     */
    List<RegistrationDomain> findAll();


    /**
     * 특강 신청 정보 조회
     * @param domain 조회할 등록 정보
     * @return boolean (true, false)
     */
    boolean existsByUserIdAndScheduleId(RegistrationDomain domain);
}
