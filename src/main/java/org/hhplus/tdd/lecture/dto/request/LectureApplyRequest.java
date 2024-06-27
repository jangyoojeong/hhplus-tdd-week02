package org.hhplus.tdd.lecture.dto.request;

import lombok.*;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplyRequest {

    private Long userId;            // 사용자ID
    private Long scheduleId;        // 스케쥴ID

}
