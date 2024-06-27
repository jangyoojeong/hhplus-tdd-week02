package org.hhplus.tdd.lecture.dto.response;

import lombok.*;
import org.hhplus.tdd.lecture.domain.result.LectureApplyResult;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplyResponse {

    private Long registrationId;    // 신청ID
    private Long userId;            // 사용자ID
    private Long scheduleId;        // 스케쥴ID

    public static LectureApplyResponse toResponse(LectureApplyResult result) {
        return LectureApplyResponse.builder()
                .registrationId(result.getRegistrationId())
                .userId(result.getUserId())
                .scheduleId(result.getScheduleId())
                .build();
    }

}
