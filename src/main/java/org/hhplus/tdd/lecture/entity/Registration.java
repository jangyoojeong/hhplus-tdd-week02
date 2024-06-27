package org.hhplus.tdd.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "registration")
public class Registration {

    @Id
    @Column(name = "registration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;   // 신청ID

    @Column(name = "user_id")
    private Long userId;           // 사용자ID

    @Column(name = "schedule_id")
    private Long scheduleId;        // 스케쥴ID


}