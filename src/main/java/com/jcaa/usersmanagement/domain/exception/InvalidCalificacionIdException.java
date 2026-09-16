package com.jcaa.usersmanagement.domain.exception;

public final class InvalidCalificacionIdException extends DomainException {

  private static final String MESSAGE_NEGATIVE = "The calificacion id must not be negative.";

  private InvalidCalificacionIdException(final String message) {
    super(message);
  }

  public static InvalidCalificacionIdException becauseValueIsNegative() {
    return new InvalidCalificacionIdException(MESSAGE_NEGATIVE);
  }
}
