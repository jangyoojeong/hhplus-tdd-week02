package org.hhplus.tdd.lecture.repository.lecture;
import org.hhplus.tdd.lecture.domain.LectureDomain;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LecturePersistence {

    /**
     * 특강 조회
     * @param
     * @return 특강 도메인 객체
     */
    LectureDomain findById(LectureDomain domain);
    
    /**
     * 특강 목록 조회
     * @param
     * @return 특강 목록 도메인 객체 리스트
     */
    List<LectureDomain> findAll();

    /**
     * 특강 저장
     * @param domain 저장할 특강 정보 도메인 객체
     * @return 저장된 특강 정보의 도메인 객체
     */
    LectureDomain save(LectureDomain domain);
}
