package org.hhplus.tdd.lecture.controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.tdd.lecture.domain.LectureScheduleDomain;
import org.hhplus.tdd.lecture.domain.command.LectureApplicationCommand;
import org.hhplus.tdd.lecture.dto.request.LectureApplyRequest;
import org.hhplus.tdd.lecture.dto.response.LectureApplicationResponse;
import org.hhplus.tdd.lecture.dto.response.LectureApplyResponse;
import org.hhplus.tdd.lecture.dto.response.LectureGetResponse;
import org.hhplus.tdd.lecture.repository.lectureSchedule.LectureSchedulePersistence;
import org.hhplus.tdd.lecture.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private static final Logger log = LoggerFactory.getLogger(LectureController.class);

    private final LectureService lectureService;

    /**
     * 특강 신청 API
     */
    @PostMapping("/apply")
    public ResponseEntity<LectureApplyResponse> applyForLecture (@RequestBody LectureApplyRequest request) {
        LectureApplyResponse response = LectureApplyResponse.toResponse(lectureService.applyForLecture(request.toCommand()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 특강 목록 조회 API
     */
    @GetMapping("/")
    public ResponseEntity<List<LectureGetResponse>> getAllLectures () {
        List<LectureGetResponse> response = LectureGetResponse.toResponseList(lectureService.getAllLectures());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 특강 신청 완료 여부 조회 API
     */
    @GetMapping("/application/{userId}")
    public ResponseEntity<LectureApplicationResponse> getLectureApplicationStatus (@PathVariable ("userId") Long userId, @RequestParam Long scheduleId) {
        LectureApplicationResponse response = LectureApplicationResponse.toResponse(lectureService.getLectureApplicationStatus(new LectureApplicationCommand(userId, scheduleId)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
