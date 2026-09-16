package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import java.util.Map;
import lombok.Getter;

@Getter
public final class CalificacionUpdatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "calificacion.updated";

  private final CalificacionModel calificacion;

  public CalificacionUpdatedDomainEvent(final CalificacionModel calificacion) {
    super(EVENT_NAME);
    this.calificacion = calificacion;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of(
        "cid", String.valueOf(calificacion.getCid().value()),
        "estudiante", calificacion.getEstudiante().value(),
        "docente", calificacion.getDocente().value(),
        "asignatura", calificacion.getAsignatura().value(),
        "nota", calificacion.getNota().value().toPlainString());
  }
}
