package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateCalificacionRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateCalificacionRequest;
import java.util.List;

public final class CalificacionDesktopMapper {

  private CalificacionDesktopMapper() {}

  public static CreateCalificacionCommand toCreateCommand(final CreateCalificacionRequest request) {
    return new CreateCalificacionCommand(
        request.estudiante(),
        request.docente(),
        request.asignatura(),
        request.carrera(),
        request.universidad(),
        request.periodo(),
        request.actividadEvaluada(),
        request.nota());
  }

  public static UpdateCalificacionCommand toUpdateCommand(final UpdateCalificacionRequest request) {
    return new UpdateCalificacionCommand(
        request.cid(),
        request.estudiante(),
        request.docente(),
        request.asignatura(),
        request.carrera(),
        request.universidad(),
        request.periodo(),
        request.actividadEvaluada(),
        request.nota());
  }

  public static DeleteCalificacionCommand toDeleteCommand(final int cid) {
    return new DeleteCalificacionCommand(cid);
  }

  public static GetCalificacionByIdQuery toGetByIdQuery(final int cid) {
    return new GetCalificacionByIdQuery(cid);
  }

  public static CalificacionResponse toResponse(final CalificacionModel model) {
    return new CalificacionResponse(
        model.getCid().value(),
        model.getFecha(),
        model.getEstudiante().value(),
        model.getDocente().value(),
        model.getAsignatura().value(),
        model.getCarrera().value(),
        model.getUniversidad().value(),
        model.getPeriodo().value(),
        model.getActividadEvaluada().value(),
        model.getNota().value());
  }

  public static List<CalificacionResponse> toResponseList(final List<CalificacionModel> list) {
    return list.stream().map(CalificacionDesktopMapper::toResponse).toList();
  }
}
