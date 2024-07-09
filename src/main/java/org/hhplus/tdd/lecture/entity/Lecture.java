package org.hhplus.tdd.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.LectureDomain;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lecture")
public class Lecture {

    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;         // 특강ID

    @Column(name = "title")
    private String title;           // 특강제목

    public Lecture(String title) {
        this.title = title;
    }



    public static Lecture toEntity(LectureDomain domain) {
        return Lecture.builder()
                .lectureId(domain.getLectureId())
                .title(domain.getTitle())
                .build();
    }


    public static LectureDomain toDomain(Lecture entity) {
        return LectureDomain.builder()
                .lectureId(entity.getLectureId())
                .title(entity.getTitle())
                .build();
    }

    public static List<LectureDomain> toDomainList(List<Lecture> entityList) {
        return entityList.stream()
                .map(Lecture::toDomain)
                .collect(Collectors.toList());
    }
}