package org.hhplus.tdd.lecture.domain.result;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplicationResult {
    private boolean applicationStatus;    // 특강신청 성공여부 상태값

    public boolean getApplicationStatus() {
        return applicationStatus;
    }
}