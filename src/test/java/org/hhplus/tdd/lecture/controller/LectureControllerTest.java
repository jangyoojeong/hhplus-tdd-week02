package org.hhplus.tdd.lecture.controller;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    @DisplayName("[성공테스트] 특강_신청_API_테스트")
    public void applyForLectureTest_특강_신청_API_테스트 () throws Exception {

        // Given
        LectureApplyRequest request = new LectureApplyRequest(userId, scheduleId);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/lectures/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // Then > 테스트를 검증
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())                                                    // 상태 코드 200인 성공적인 응답을 기대
                .andExpect(jsonPath("$.userId").value(request.getUserId()))          // userId 필드 비교
                .andExpect(jsonPath("$.scheduleId").value(request.getScheduleId()))  // scheduleId 필드 비교
                .andDo(print())                                                                // 응답값 print
                .andReturn();
    }

    @Test
    @DisplayName("[성공테스트] 특강_목록_조회_API_테스트_2건의_목록이_정상적으로_조회된다")
    public void getAllLecturesTest_특강_목록_조회_API_테스트_2건의_목록이_정상적으로_조회된다 () throws Exception {

        // Given
        List<LectureGetResponse> expectedResponse = new ArrayList<>(Arrays.asList(
                new LectureGetResponse(1L, 1L, LocalDate.of(2024, 6, 29), 30, 0),
                new LectureGetResponse(2L, 1L, LocalDate.of(2024, 6, 30), 30, 0)
        ));

        // When
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/lectures/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        // Then
        String responseBody = mvcResult.getResponse().getContentAsString();
        LectureGetResponse[] actualResponse = objectMapper.readValue(responseBody, LectureGetResponse[].class);

        assertThat(actualResponse).isEqualTo(expectedResponse.toArray(new LectureGetResponse[0]));
    }


    @Test
    @DisplayName("[성공테스트] 특강_신청_완료_여부_조회_API_테스트")
    public void getLectureApplicationStatusTest_특강_신청_완료_여부_조회_API_테스트 () throws Exception {

        // Given
        registrationPersistence.save(new RegistrationDomain(1L, 1L));
        boolean applicationStatus = true;

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/lectures/application/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("scheduleId", scheduleId.toString()));

        // Then > 테스트를 검증
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())                      // 상태 코드 200인 성공적인 응답을 기대
                .andExpect(jsonPath("$.applicationStatus").value(applicationStatus))
                .andDo(print())                                  // 응답값 print
                .andReturn();
    }

    @Test
    @DisplayName("[실패테스트] 동일한_유저가_동일특강을_두번의_수강신청시_예외반환")
    public void applyForLectureTest_동일한_유저가_동일특강을_두번의_수강신청시_예외반환 () throws Exception {
        LectureApplyRequest request = new LectureApplyRequest(userId, scheduleId);

        // When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/lectures/apply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // Then
        assertThrows(Exception.class, () -> {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/lectures/apply")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andReturn();
        });
    }
}