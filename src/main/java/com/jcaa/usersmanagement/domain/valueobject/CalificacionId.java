package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidCalificacionIdException;

public record CalificacionId(int value) {

  public CalificacionId {
    validatePositive(value);
  }

  private static void validatePositive(final int value) {
    if (value < 0) {
      throw InvalidCalificacionIdException.becauseValueIsNegative();
    }
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
