package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidCalificacionNotaException;
import java.math.BigDecimal;
import java.util.Objects;

public record CalificacionNota(BigDecimal value) {

  private static final BigDecimal MIN_VALUE = BigDecimal.ZERO;
  private static final BigDecimal MAX_VALUE = new BigDecimal("99.99");

  public CalificacionNota {
    Objects.requireNonNull(value, "Nota cannot be null");
    validateRange(value);
  }

  private static void validateRange(final BigDecimal value) {
    if (value.compareTo(MIN_VALUE) < 0) {
      throw InvalidCalificacionNotaException.becauseValueIsNegative();
    }
    if (value.compareTo(MAX_VALUE) > 0) {
      throw InvalidCalificacionNotaException.becauseValueExceedsMax(MAX_VALUE);
    }
  }

  @Override
  public String toString() {
    return value.toPlainString();
  }
}
