package com.jcaa.usersmanagement.domain.exception;

import java.math.BigDecimal;

public final class InvalidCalificacionNotaException extends DomainException {

  private static final String MESSAGE_NEGATIVE = "The calificacion nota must not be negative.";
  private static final String MESSAGE_EXCEEDS_MAX =
      "The calificacion nota must not exceed %s.";

  private InvalidCalificacionNotaException(final String message) {
    super(message);
  }

  public static InvalidCalificacionNotaException becauseValueIsNegative() {
    return new InvalidCalificacionNotaException(MESSAGE_NEGATIVE);
  }

  public static InvalidCalificacionNotaException becauseValueExceedsMax(
      final BigDecimal maxValue) {
    return new InvalidCalificacionNotaException(
        String.format(MESSAGE_EXCEEDS_MAX, maxValue.toPlainString()));
  }
}
