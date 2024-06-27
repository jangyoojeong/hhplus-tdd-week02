package org.hhplus.tdd.lecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.tdd.lecture.domain.RegistrationDomain;

import java.util.List;
import java.util.stream.Collectors;

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

    public Registration(Long userId, Long scheduleId) {
        this.userId = userId;
        this.scheduleId = scheduleId;
    }

    public static Registration toEntity(RegistrationDomain domain) {
        return Registration.builder()
                .registrationId(domain.getRegistrationId())
                .userId(domain.getUserId())
                .scheduleId(domain.getScheduleId())
                .build();
    }

    public static RegistrationDomain toDomain(Registration entity) {
        return RegistrationDomain.builder()
                .registrationId(entity.getRegistrationId())
                .userId(entity.getUserId())
                .scheduleId(entity.getScheduleId())
                .build();
    }

    public static List<RegistrationDomain> toDomainList(List<Registration> entityList) {
        return entityList.stream()
                .map(Registration::toDomain)
                .collect(Collectors.toList());
    }
}