package com.jcaa.usersmanagement.domain.exception;

public final class InvalidCalificacionFieldException extends DomainException {

  private static final String MESSAGE_EMPTY = "The calificacion field '%s' must not be empty.";
  private static final String MESSAGE_TOO_LONG =
      "The calificacion field '%s' must not exceed %d characters.";

  private InvalidCalificacionFieldException(final String message) {
    super(message);
  }

  public static InvalidCalificacionFieldException becauseValueIsEmpty(final String fieldName) {
    return new InvalidCalificacionFieldException(String.format(MESSAGE_EMPTY, fieldName));
  }

  public static InvalidCalificacionFieldException becauseLengthExceedsMax(
      final String fieldName, final int maxLength) {
    return new InvalidCalificacionFieldException(
        String.format(MESSAGE_TOO_LONG, fieldName, maxLength));
  }
}
