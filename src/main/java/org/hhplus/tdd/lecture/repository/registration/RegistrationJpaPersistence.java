package org.hhplus.tdd.lecture.repository.registration;

import org.springframework.stereotype.Component;

@Component
public class RegistrationJpaPersistence implements RegistrationPersistence {

    private final RegistrationRepository repository;

    public RegistrationJpaPersistence(RegistrationRepository repository) {
        this.repository = repository;
    }


}
