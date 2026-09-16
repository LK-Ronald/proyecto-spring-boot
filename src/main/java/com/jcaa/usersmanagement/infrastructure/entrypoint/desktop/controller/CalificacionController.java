package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllCalificacionesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetCalificacionByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateCalificacionUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateCalificacionRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateCalificacionRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.CalificacionDesktopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CalificacionController {

  private final CreateCalificacionUseCase createCalificacionUseCase;
  private final UpdateCalificacionUseCase updateCalificacionUseCase;
  private final DeleteCalificacionUseCase deleteCalificacionUseCase;
  private final GetCalificacionByIdUseCase getCalificacionByIdUseCase;
  private final GetAllCalificacionesUseCase getAllCalificacionesUseCase;

  public List<CalificacionResponse> listAllCalificaciones() {
    final var list = getAllCalificacionesUseCase.execute();
    return CalificacionDesktopMapper.toResponseList(list);
  }

  public CalificacionResponse findCalificacionById(final int cid) {
    final var query = CalificacionDesktopMapper.toGetByIdQuery(cid);
    final var calificacion = getCalificacionByIdUseCase.execute(query);
    return CalificacionDesktopMapper.toResponse(calificacion);
  }

  public CalificacionResponse createCalificacion(final CreateCalificacionRequest request) {
    final var command = CalificacionDesktopMapper.toCreateCommand(request);
    final var calificacion = createCalificacionUseCase.execute(command);
    return CalificacionDesktopMapper.toResponse(calificacion);
  }

  public CalificacionResponse updateCalificacion(final UpdateCalificacionRequest request) {
    final var command = CalificacionDesktopMapper.toUpdateCommand(request);
    final var calificacion = updateCalificacionUseCase.execute(command);
    return CalificacionDesktopMapper.toResponse(calificacion);
  }

  public void deleteCalificacion(final int cid) {
    final var command = CalificacionDesktopMapper.toDeleteCommand(cid);
    deleteCalificacionUseCase.execute(command);
  }
}
