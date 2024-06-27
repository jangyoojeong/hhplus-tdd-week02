package org.hhplus.tdd.lecture.repository.registration;

import org.hhplus.tdd.lecture.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}