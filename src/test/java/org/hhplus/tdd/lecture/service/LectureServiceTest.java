package org.hhplus.tdd.lecture.service;

import org.hhplus.tdd.lecture.domain.LectureDomain;
import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;
import org.hhplus.tdd.lecture.domain.RegistrationDomain;
import org.hhplus.tdd.lecture.domain.command.LectureApplicationCommand;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;
import org.hhplus.tdd.lecture.domain.result.LectureApplicationResult;
import org.hhplus.tdd.lecture.domain.result.LectureApplyResult;
import org.hhplus.tdd.lecture.domain.result.LectureGetResult;
import org.hhplus.tdd.lecture.dto.request.LectureApplyRequest;
import org.hhplus.tdd.lecture.repository.lecture.LecturePersistence;
import org.hhplus.tdd.lecture.repository.lectureSchedule.LectureSchedulePersistence;
import org.hhplus.tdd.lecture.repository.registration.RegistrationPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
public class LectureServiceTest {

    @Mock
    private LecturePersistence lecturePersistence;
    @Mock
    private LectureSchedulePersistence lectureSchedulePersistence;
    @Mock
    private RegistrationPersistence registrationPersistence;
    @InjectMocks
    private LectureService lectureService;

    private Long userId;
    private Long scheduleId;

    @BeforeEach
    void setUp() {
        // 초기화
        MockitoAnnotations.openMocks(this);

        userId = 1L;
        scheduleId = 1L;

        lecturePersistence.save(new LectureDomain("TDD", 30));
        lecturePersistence.save(new LectureDomain("Spring Boot", 25));

        lectureSchedulePersistence.save(new LectureScheduleDomain(1L, LocalDate.of(2024, 6, 29), 30, 0));
        lectureSchedulePersistence.save(new LectureScheduleDomain(1L, LocalDate.of(2024, 6, 30), 30, 0));

    }

    @Test
    @DisplayName("[성공테스트] 특강_신청_테스트_신청_후_저장정보가_정상적으로_리턴된다")
    public void applyForLectureTest_특강_신청_테스트_신청_후_저장정보가_정상적으로_리턴된다 () {
        // Given
        LectureApplyCommand command = new LectureApplyCommand(userId, scheduleId);
        LectureScheduleDomain scheduleDomain = new LectureScheduleDomain(scheduleId, 1L, LocalDate.of(2024, 6, 29), 30, 0);
        RegistrationDomain legistrationDomain = RegistrationDomain.of(command);
        legistrationDomain.setRegistrationId(1L);

        given(registrationPersistence.existsByUserIdAndScheduleId(any(RegistrationDomain.class))).willReturn(false);
        given(lectureSchedulePersistence.applyForLectureWithPessimisticLock(any(LectureScheduleDomain.class))).willReturn(scheduleDomain);
        given(registrationPersistence.save(any(RegistrationDomain.class))).willReturn(legistrationDomain);

        // When
        LectureApplyResult result = lectureService.applyForLecture(command);

        // Then
        assertThat(result).isNotNull();
        assertNotNull(result.getRegistrationId());
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getScheduleId()).isEqualTo(scheduleId);

    }

    @Test
    @DisplayName("[실패테스트] 특강_신청_테스트_존재하지_않는_특강_신청시_예외반환")
    public void applyForLectureTest_특강_신청_테스트_존재하지_않는_특강_신청시_예외반환 () {
        // Given
        LectureApplyRequest request = new LectureApplyRequest(userId, 99L);
        LectureApplyCommand command = request.toCommand();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            lectureService.applyForLecture(command);
        });
    }

    @Test
    @DisplayName("[성공테스트] 특강_목록_조회_테스트_2건의_목록이_정상적으로_조회된다")
    public void getAllLecturesTest_특강_목록_조회_테스트_2건의_목록이_정상적으로_조회된다 () {
        // Given
        List<LectureScheduleDomain> lectureScheduleList = new ArrayList<>(Arrays.asList(
                new LectureScheduleDomain(1L, LocalDate.of(2024, 6, 29), 30, 0),
                new LectureScheduleDomain(1L, LocalDate.of(2024, 6, 30), 30, 0)
        ));

        given(lectureSchedulePersistence.findAll()).willReturn(lectureScheduleList);

        // When
        List<LectureGetResult> resultList = lectureService.getAllLectures();

        // Then
        assertEquals(lectureScheduleList.size(), resultList.size());

        // 리턴된 리스트 for 문을 통해 검증
        for (int i = 0; i < lectureScheduleList.size(); i++) {
            LectureGetResult expected = LectureGetResult.of(lectureScheduleList.get(i));
            LectureGetResult actual = resultList.get(i);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("[성공테스트] 특강_신청_완료_여부_조회_테스트_1유저가_신청한_1특강은_TRUE를_리턴한다")
    public void getLectureApplicationStatusTest_특강_신청_완료_여부_조회_테스트_1유저가_신청한_1특강은_TRUE를_리턴한다 () {
        // Given
        LectureApplicationCommand command = new LectureApplicationCommand(userId, scheduleId);
        boolean applicationStatus = true;

        given(registrationPersistence.existsByUserIdAndScheduleId(any(RegistrationDomain.class))).willReturn(applicationStatus);

        // When
        LectureApplicationResult result = lectureService.getLectureApplicationStatus(command);

        // Then
        assertThat(result.getApplicationStatus()).isTrue();
    }

    @Test
    @DisplayName("[성공테스트] 특강_신청_완료_여부_조회_테스트_1유저가_신청하지_않은_2특강은_FALSE를_리턴한다")
    public void getLectureApplicationStatusTest_특강_신청_완료_여부_조회_테스트_1유저가_신청하지않은_2특강은_FALSE를_리턴한다 () {
        // Given
        LectureApplicationCommand command = new LectureApplicationCommand(userId, 2L);
        boolean applicationStatus = false;

        given(registrationPersistence.existsByUserIdAndScheduleId(any(RegistrationDomain.class))).willReturn(applicationStatus);

        // When
        LectureApplicationResult result = lectureService.getLectureApplicationStatus(command);

        // Then
        assertThat(result.getApplicationStatus()).isFalse();
    }

    @Test
    @DisplayName("[실패테스트] 동일한_유저가_동일특강을_두번의_수강신청시_예외반환")
    public void applyForLectureTest_동일한_유저가_동일특강을_두번의_수강신청시_예외반환 () {
        // Given
        LectureApplyCommand command = new LectureApplyCommand(userId, scheduleId);
        LectureScheduleDomain scheduleDomain = new LectureScheduleDomain(1L, 1L, LocalDate.of(2024, 6, 29), 30, 0);

        given(registrationPersistence.existsByUserIdAndScheduleId(any(RegistrationDomain.class))).willReturn(true);
        given(lectureSchedulePersistence.applyForLectureWithPessimisticLock(any(LectureScheduleDomain.class))).willReturn(scheduleDomain);

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            lectureService.applyForLecture(command);
        });
    }
}