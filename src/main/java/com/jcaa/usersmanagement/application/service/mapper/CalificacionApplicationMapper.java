package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionActividadEvaluada;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionAsignatura;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionCarrera;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionDocente;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionEstudiante;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionNota;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionPeriodo;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionUniversidad;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CalificacionApplicationMapper {

  public CalificacionModel fromCreateCommandToModel(final CreateCalificacionCommand command) {
    return CalificacionModel.create(
        new CalificacionEstudiante(command.estudiante()),
        new CalificacionDocente(command.docente()),
        new CalificacionAsignatura(command.asignatura()),
        new CalificacionCarrera(command.carrera()),
        new CalificacionUniversidad(command.universidad()),
        new CalificacionPeriodo(command.periodo()),
        new CalificacionActividadEvaluada(command.actividadEvaluada()),
        new CalificacionNota(command.nota()));
  }

  public CalificacionModel fromUpdateCommandToModel(
      final UpdateCalificacionCommand command, final LocalDateTime currentFecha) {
    return new CalificacionModel(
        new CalificacionId(command.cid()),
        currentFecha,
        new CalificacionEstudiante(command.estudiante()),
        new CalificacionDocente(command.docente()),
        new CalificacionAsignatura(command.asignatura()),
        new CalificacionCarrera(command.carrera()),
        new CalificacionUniversidad(command.universidad()),
        new CalificacionPeriodo(command.periodo()),
        new CalificacionActividadEvaluada(command.actividadEvaluada()),
        new CalificacionNota(command.nota()));
  }

  public CalificacionId fromGetCalificacionByIdQueryToCalificacionId(
      final GetCalificacionByIdQuery query) {
    return new CalificacionId(query.cid());
  }

  public CalificacionId fromDeleteCommandToCalificacionId(
      final DeleteCalificacionCommand command) {
    return new CalificacionId(command.cid());
  }
}
