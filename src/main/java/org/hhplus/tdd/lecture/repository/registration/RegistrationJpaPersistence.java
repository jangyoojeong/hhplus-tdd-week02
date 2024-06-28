package org.hhplus.tdd.lecture.repository.registration;

import org.hhplus.tdd.lecture.domain.RegistrationDomain;
import org.hhplus.tdd.lecture.entity.Registration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationJpaPersistence implements RegistrationPersistence {

    private final RegistrationRepository repository;

    public RegistrationJpaPersistence(RegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegistrationDomain save(RegistrationDomain domain) {
        return Registration.toDomain(repository.save(Registration.toEntity(domain)));
    }

    @Override
    public List<RegistrationDomain> findAll() {
        return Registration.toDomainList(repository.findAll());
    }

    @Override
    public boolean existsByUserIdAndScheduleId(RegistrationDomain domain) {
        return repository.existsByUserIdAndScheduleId(domain.getUserId(), domain.getScheduleId());
    }

}
