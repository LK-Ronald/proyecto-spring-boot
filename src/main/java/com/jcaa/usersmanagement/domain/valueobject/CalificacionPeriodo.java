package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidCalificacionFieldException;
import java.util.Objects;

public record CalificacionPeriodo(String value) {

  private static final int MAX_LENGTH = 20;
  private static final String FIELD_NAME = "periodo";

  public CalificacionPeriodo {
    final String normalizedValue =
        Objects.requireNonNull(value, "Periodo cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMaxLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidCalificacionFieldException.becauseValueIsEmpty(FIELD_NAME);
    }
  }

  private static void validateMaxLength(final String normalizedValue) {
    if (normalizedValue.length() > MAX_LENGTH) {
      throw InvalidCalificacionFieldException.becauseLengthExceedsMax(FIELD_NAME, MAX_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
