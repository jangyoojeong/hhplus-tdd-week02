package org.hhplus.tdd.lecture.domain.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.RegistrationDomain;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplyResult {

    private Long registrationId;    // 신청ID
    private Long userId;            // 사용자ID
    private Long scheduleId;        // 스케쥴ID

    public static LectureApplyResult of(RegistrationDomain domain) {
        return LectureApplyResult.builder()
                .registrationId(domain.getRegistrationId())
                .userId(domain.getUserId())
                .scheduleId(domain.getScheduleId())
                .build();
    }
}
   