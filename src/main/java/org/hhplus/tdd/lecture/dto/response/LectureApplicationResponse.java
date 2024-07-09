package org.hhplus.tdd.lecture.dto.response;

import lombok.*;
import org.hhplus.tdd.lecture.domain.result.LectureApplicationResult;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplicationResponse {

    private boolean applicationStatus;    // 특강신청 성공여부 상태값

    public static LectureApplicationResponse toResponse(LectureApplicationResult result) {
        return LectureApplicationResponse.builder()
                .applicationStatus(result.getApplicationStatus())
                .build();
    }
}