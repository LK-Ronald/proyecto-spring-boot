package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.in.CreateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllCalificacionesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetCalificacionByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateCalificacionUseCase;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CalificacionController")
@ExtendWith(MockitoExtension.class)
class CalificacionControllerTest {

  @Mock private CreateCalificacionUseCase createCalificacionUseCase;
  @Mock private UpdateCalificacionUseCase updateCalificacionUseCase;
  @Mock private DeleteCalificacionUseCase deleteCalificacionUseCase;
  @Mock private GetCalificacionByIdUseCase getCalificacionByIdUseCase;
  @Mock private GetAllCalificacionesUseCase getAllCalificacionesUseCase;

  private CalificacionController controller;
  private CalificacionModel model;
  private LocalDateTime now;

  @BeforeEach
  void setUp() {
    controller =
        new CalificacionController(
            createCalificacionUseCase,
            updateCalificacionUseCase,
            deleteCalificacionUseCase,
            getCalificacionByIdUseCase,
            getAllCalificacionesUseCase);

    now = LocalDateTime.now();
    model =
        new CalificacionModel(
            new CalificacionId(1),
            now,
            new CalificacionEstudiante("Estudiante"),
            new CalificacionDocente("Docente"),
            new CalificacionAsignatura("Asignatura"),
            new CalificacionCarrera("Carrera"),
            new CalificacionUniversidad("Universidad"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Actividad"),
            new CalificacionNota(new BigDecimal("4.50")));
  }

  @Test
  @DisplayName("listAllCalificaciones() retorna lista de respuestas")
  void shouldListAll() {
    when(getAllCalificacionesUseCase.execute()).thenReturn(List.of(model));

    final List<CalificacionResponse> result = controller.listAllCalificaciones();

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("findCalificacionById() retorna respuesta cuando existe")
  void shouldFindById() {
    when(getCalificacionByIdUseCase.execute(new GetCalificacionByIdQuery(1))).thenReturn(model);

    final CalificacionResponse result = controller.findCalificacionById(1);

    assertNotNull(result);
    assertEquals(1, result.cid());
  }

  @Test
  @DisplayName("createCalificacion() delega a caso de uso y retorna respuesta")
  void shouldCreateCalificacion() {
    when(createCalificacionUseCase.execute(any(CreateCalificacionCommand.class))).thenReturn(model);

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

    final CalificacionResponse result = controller.createCalificacion(request);

    assertNotNull(result);
    assertEquals(1, result.cid());
  }

  @Test
  @DisplayName("updateCalificacion() delega a caso de uso y retorna respuesta")
  void shouldUpdateCalificacion() {
    when(updateCalificacionUseCase.execute(any(UpdateCalificacionCommand.class))).thenReturn(model);

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

    final CalificacionResponse result = controller.updateCalificacion(request);

    assertNotNull(result);
    assertEquals(1, result.cid());
  }

  @Test
  @DisplayName("deleteCalificacion() delega a caso de uso")
  void shouldDeleteCalificacion() {
    assertDoesNotThrow(() -> controller.deleteCalificacion(1));
    verify(deleteCalificacionUseCase).execute(new DeleteCalificacionCommand(1));
  }
}
