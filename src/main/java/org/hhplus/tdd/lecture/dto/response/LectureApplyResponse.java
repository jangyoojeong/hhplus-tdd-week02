package org.hhplus.tdd.lecture.dto.response;

import lombok.*;

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


}
