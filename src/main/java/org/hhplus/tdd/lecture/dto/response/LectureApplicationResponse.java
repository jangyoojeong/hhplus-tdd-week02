package org.hhplus.tdd.lecture.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureApplicationResponse {

    private boolean applicationStatus;    // 특강신청 성공여부 상태값

}