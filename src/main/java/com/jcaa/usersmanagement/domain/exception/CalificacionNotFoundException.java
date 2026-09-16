package com.jcaa.usersmanagement.domain.exception;

public final class CalificacionNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID = "The calificacion with id '%d' was not found.";

  private CalificacionNotFoundException(final String message) {
    super(message);
  }

  public static CalificacionNotFoundException becauseIdWasNotFound(final int calificacionId) {
    return new CalificacionNotFoundException(String.format(MESSAGE_BY_ID, calificacionId));
  }
}
