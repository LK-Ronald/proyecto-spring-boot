package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.CalificacionRestResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CalificacionRestMapper {

  public CreateCalificacionCommand toCreateCommand(final CreateCalificacionRestRequest request) {
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

  public UpdateCalificacionCommand toUpdateCommand(
      final int cid, final UpdateCalificacionRestRequest request) {
    return new UpdateCalificacionCommand(
        cid,
        request.estudiante(),
        request.docente(),
        request.asignatura(),
        request.carrera(),
        request.universidad(),
        request.periodo(),
        request.actividadEvaluada(),
        request.nota());
  }

  public GetCalificacionByIdQuery toGetByIdQuery(final int cid) {
    return new GetCalificacionByIdQuery(cid);
  }

  public DeleteCalificacionCommand toDeleteCommand(final int cid) {
    return new DeleteCalificacionCommand(cid);
  }

  public CalificacionRestResponse toResponse(final CalificacionModel model) {
    return new CalificacionRestResponse(
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

  public List<CalificacionRestResponse> toResponseList(final List<CalificacionModel> list) {
    return list.stream().map(CalificacionRestMapper::toResponse).toList();
  }
}
