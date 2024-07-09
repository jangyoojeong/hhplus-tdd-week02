package org.hhplus.tdd.lecture.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.command.LectureApplyCommand;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureDomain {

    private Long lectureId;         // 특강ID
    private String title;           // 특강제목
    private int capacity;           // 수강정원

    public LectureDomain(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
    }

    public static LectureDomain of(LectureApplyCommand command) {
        return LectureDomain.builder()
                .lectureId(command.getUserId())
                .build();
    }
}