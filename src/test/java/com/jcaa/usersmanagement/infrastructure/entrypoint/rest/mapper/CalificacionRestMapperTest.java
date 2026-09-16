package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

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
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.CalificacionRestResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CalificacionRestMapper")
class CalificacionRestMapperTest {

  @Test
  @DisplayName("toCreateCommand() mapea request a command correctamente")
  void shouldMapCreateRequestToCommand() {
    final CreateCalificacionRestRequest request =
        new CreateCalificacionRestRequest(
            "Estudiante",
            "Docente",
            "Asignatura",
            "Carrera",
            "Universidad",
            "2026-1",
            "Actividad",
            new BigDecimal("4.50"));

    final CreateCalificacionCommand command = CalificacionRestMapper.toCreateCommand(request);

    assertAll(
        "toCreateCommand",
        () -> assertEquals("Estudiante", command.estudiante()),
        () -> assertEquals("Docente", command.docente()),
        () -> assertEquals(new BigDecimal("4.50"), command.nota()));
  }

  @Test
  @DisplayName("toUpdateCommand() mapea request a command correctamente")
  void shouldMapUpdateRequestToCommand() {
    final UpdateCalificacionRestRequest request =
        new UpdateCalificacionRestRequest(
            "Estudiante",
            "Docente",
            "Asignatura",
            "Carrera",
            "Universidad",
            "2026-1",
            "Actividad",
            new BigDecimal("4.50"));

    final UpdateCalificacionCommand command = CalificacionRestMapper.toUpdateCommand(1, request);

    assertAll(
        "toUpdateCommand",
        () -> assertEquals(1, command.cid()),
        () -> assertEquals("Estudiante", command.estudiante()),
        () -> assertEquals(new BigDecimal("4.50"), command.nota()));
  }

  @Test
  @DisplayName("toGetByIdQuery() y toDeleteCommand() mapean correctamente")
  void shouldMapQueryAndCommand() {
    final GetCalificacionByIdQuery query = CalificacionRestMapper.toGetByIdQuery(1);
    final DeleteCalificacionCommand delete = CalificacionRestMapper.toDeleteCommand(1);

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

    final CalificacionRestResponse response = CalificacionRestMapper.toResponse(model);
    final List<CalificacionRestResponse> list = CalificacionRestMapper.toResponseList(List.of(model));

    assertNotNull(response);
    assertEquals(1, response.cid());
    assertEquals(1, list.size());
  }
}
