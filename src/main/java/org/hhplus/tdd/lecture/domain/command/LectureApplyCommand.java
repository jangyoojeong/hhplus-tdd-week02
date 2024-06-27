package org.hhplus.tdd.lecture.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplyCommand {

    private Long userId;            // 사용자ID
    private Long scheduleId;        // 스케쥴ID

}
   