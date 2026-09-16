package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidCalificacionFieldException;
import java.util.Objects;

public record CalificacionDocente(String value) {

  private static final int MAX_LENGTH = 100;
  private static final String FIELD_NAME = "docente";

  public CalificacionDocente {
    final String normalizedValue =
        Objects.requireNonNull(value, "Docente cannot be null").trim();
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
