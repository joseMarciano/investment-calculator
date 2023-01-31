package com.investment.managment.validation.exception;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DomainException extends RuntimeException {
    private final List<Error> errors;

    public static DomainException of(final Error error) {
        return new DomainException(List.of(error));
    }

    DomainException(final List<Error> errors) {
        super("Domain Exception occurred");
        this.errors = requireNonNull(errors);
    }

    public Error getError() {
        return !this.errors.isEmpty()
                ? this.errors.get(0)
                : null;
    }
}
