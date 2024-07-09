package org.hhplus.tdd.lecture.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.command.LectureApplicationCommand;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDomain {

    private Long registrationId;   // 신청ID
    private Long userId;           // 사용자ID
    private Long scheduleId;       // 스케쥴ID

    public RegistrationDomain(Long userId, Long scheduleId) {
        this.userId = userId;
        this.scheduleId = scheduleId;
    }

    public static RegistrationDomain of(LectureApplyCommand command) {
        return RegistrationDomain.builder()
                .userId(command.getUserId())
                .scheduleId(command.getScheduleId())
                .build();
    }

    public static RegistrationDomain of(LectureApplicationCommand command) {
        return RegistrationDomain.builder()
                .userId(command.getUserId())
                .scheduleId(command.getScheduleId())
                .build();
    }
}