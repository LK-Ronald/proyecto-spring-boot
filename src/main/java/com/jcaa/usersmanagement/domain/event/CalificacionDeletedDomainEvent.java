package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import java.util.Map;
import lombok.Getter;

@Getter
public final class CalificacionDeletedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "calificacion.deleted";

  private final CalificacionId calificacionId;

  public CalificacionDeletedDomainEvent(final CalificacionId calificacionId) {
    super(EVENT_NAME);
    this.calificacionId = calificacionId;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of("cid", String.valueOf(calificacionId.value()));
  }
}
