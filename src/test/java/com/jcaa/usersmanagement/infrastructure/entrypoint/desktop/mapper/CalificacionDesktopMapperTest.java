package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateCalificacionRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateCalificacionRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CalificacionDesktopMapper")
class CalificacionDesktopMapperTest {

  @Test
  @DisplayName("toCreateCommand() mapea request a command correctamente")
  void shouldMapCreateRequestToCommand() {
    final CreateCalificacionRequest request =
        new CreateCalificacionRequest(
            "Estudiante",
            "Docente",
            "Asignatura",
            "Carrera",
            "Universidad",
            "2026-1",
            "Actividad",
            new BigDecimal("4.50"));

    final CreateCalificacionCommand command = CalificacionDesktopMapper.toCreateCommand(request);

    assertAll(
        "toCreateCommand",
        () -> assertEquals("Estudiante", command.estudiante()),
        () -> assertEquals("Docente", command.docente()),
        () -> assertEquals(new BigDecimal("4.50"), command.nota()));
  }

  @Test
  @DisplayName("toUpdateCommand() mapea request a command correctamente")
  void shouldMapUpdateRequestToCommand() {
    final UpdateCalificacionRequest request =
        new UpdateCalificacionRequest(
            1,
            "Estudiante",
            "Docente",
            "Asignatura",
            "Carrera",
            "Universidad",
            "2026-1",
            "Actividad",
            new BigDecimal("4.50"));

    final UpdateCalificacionCommand command = CalificacionDesktopMapper.toUpdateCommand(request);

    assertAll(
        "toUpdateCommand",
        () -> assertEquals(1, command.cid()),
        () -> assertEquals("Estudiante", command.estudiante()),
        () -> assertEquals(new BigDecimal("4.50"), command.nota()));
  }

  @Test
  @DisplayName("toGetByIdQuery() y toDeleteCommand() mapean correctamente")
  void shouldMapQueryAndCommand() {
    final GetCalificacionByIdQuery query = CalificacionDesktopMapper.toGetByIdQuery(1);
    final DeleteCalificacionCommand delete = CalificacionDesktopMapper.toDeleteCommand(1);

    assertEquals(1, query.cid());
    assertEquals(1, delete.cid());
  }

  @Test
  @DisplayName("toResponse() y toResponseList() mapean model a response")
  void shouldMapModelToResponse() {
    final CalificacionModel model =
        new CalificacionModel(
            new CalificacionId(1),
            LocalDateTime.now(),
            new CalificacionEstudiante("Estudiante"),
            new CalificacionDocente("Docente"),
            new CalificacionAsignatura("Asignatura"),
            new CalificacionCarrera("Carrera"),
            new CalificacionUniversidad("Universidad"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Actividad"),
            new CalificacionNota(new BigDecimal("4.50")));

    final CalificacionResponse response = CalificacionDesktopMapper.toResponse(model);
    final List<CalificacionResponse> list =
        CalificacionDesktopMapper.toResponseList(List.of(model));

    assertNotNull(response);
    assertEquals(1, response.cid());
    assertEquals(1, list.size());
  }
}
