package org.hhplus.tdd.lecture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.tdd.lecture.domain.LectureDomain;
import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;
import org.hhplus.tdd.lecture.domain.RegistrationDomain;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;
import org.hhplus.tdd.lecture.dto.request.LectureApplyRequest;
import org.hhplus.tdd.lecture.dto.response.LectureGetResponse;
import org.hhplus.tdd.lecture.repository.lecture.LecturePersistence;
import org.hhplus.tdd.lecture.repository.lectureSchedule.LectureSchedulePersistence;
import org.hhplus.tdd.lecture.repository.registration.RegistrationPersistence;
import org.hhplus.tdd.lecture.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LectureConcurrencyTest {
    @Autowired
    private LectureService lectureService;
    @Autowired
    private LecturePersistence lecturePersistence;
    @Autowired
    private LectureSchedulePersistence lectureSchedulePersistence;
    @Autowired
    private RegistrationPersistence registrationPersistence;

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
    @DisplayName("[성공테스트] 비관적락_테스트_동시_특강신청_확인_동시에_30명신청시_순차적으로_신청확인")
    public void concurrencyApplyForLectureTest_비관적락_테스트_동시_특강신청_확인_동시에_30명신청시_순차적으로_신청확인 () throws InterruptedException, ExecutionException {

        // Given
        final int numberOfThreads = 30;

        // When > CompletableFuture 리스트 생성
        List<CompletableFuture<Void>> futures = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    try {
                        lectureService.applyForLecture(new LectureApplyCommand(userId + i, scheduleId));
                    } catch (Exception e) {
                        System.out.println("유저 " + (userId + i) + " 신청 중 오류 발생: " + e.getMessage());
                    }
                })).collect(Collectors.toList());

        // 모든 CompletableFuture가 완료될 때까지 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        // Then
        List<RegistrationDomain> registrations = registrationPersistence.findAll();
        assertThat(registrations.size()).isEqualTo(numberOfThreads);

        LectureScheduleDomain schedule = lectureSchedulePersistence.findById(LectureScheduleDomain.of(new LectureApplyCommand(userId, scheduleId)));
        assertThat(schedule.getEnrollmentCount()).isEqualTo(numberOfThreads);
    }

    @Test
    @DisplayName("[실패테스트] 비관적락_테스트_동시_특강신청_확인_동시에_31명신청시_1명은_수강신청_실패_확인")
    public void concurrencyApplyForLectureTest_비관적락_테스트_동시_특강신청_확인_동시에_31명신청시_1명은_수강신청_실패_확인 () throws InterruptedException, ExecutionException {

        // Given
        final int numberOfThreads = 31;

        // When > CompletableFuture 리스트 생성
        List<CompletableFuture<Void>> futures = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    try {
                        lectureService.applyForLecture(new LectureApplyCommand(userId + i, scheduleId));
                    } catch (Exception e) {
                        System.out.println("유저 " + (userId + i) + " 신청 중 오류 발생: " + e.getMessage());
                    }
                })).collect(Collectors.toList());

        // 모든 CompletableFuture가 완료될 때까지 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        // Then
        List<RegistrationDomain> registrations = registrationPersistence.findAll();
        assertThat(registrations.size()).isEqualTo(numberOfThreads - 1);
    }
}