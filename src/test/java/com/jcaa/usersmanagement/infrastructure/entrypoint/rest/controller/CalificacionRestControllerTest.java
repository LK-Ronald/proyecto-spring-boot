package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.jcaa.usersmanagement.infrastructure.security.JwtAuthenticationFilter;
import com.jcaa.usersmanagement.infrastructure.security.JwtTokenService;
import com.jcaa.usersmanagement.infrastructure.security.RestAccessDeniedHandler;
import com.jcaa.usersmanagement.infrastructure.security.RestAuthenticationEntryPoint;
import com.jcaa.usersmanagement.infrastructure.security.SecurityConfig;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = CalificacionRestController.class,
    properties = "spring.main.web-application-type=servlet")
@Import({
  SecurityConfig.class,
  JwtAuthenticationFilter.class,
  RestAuthenticationEntryPoint.class,
  RestAccessDeniedHandler.class
})
@DisplayName("CalificacionRestController")
class CalificacionRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private CreateCalificacionUseCase createCalificacionUseCase;
  @MockitoBean private UpdateCalificacionUseCase updateCalificacionUseCase;
  @MockitoBean private DeleteCalificacionUseCase deleteCalificacionUseCase;
  @MockitoBean private GetCalificacionByIdUseCase getCalificacionByIdUseCase;
  @MockitoBean private GetAllCalificacionesUseCase getAllCalificacionesUseCase;
  @MockitoBean private JwtTokenService jwtTokenService;

  @Test
  @WithMockUser
  @DisplayName("POST /api/calificaciones crea una calificación")
  void shouldCreateCalificacion() throws Exception {
    final String request =
        """
        {
          "estudiante": "Juan Perez",
          "docente": "Prof. Gomez",
          "asignatura": "Matematicas",
          "carrera": "Ingenieria",
          "universidad": "Universidad Nacional",
          "periodo": "2026-1",
          "actividadEvaluada": "Parcial 1",
          "nota": 4.50
        }
        """;

    final CalificacionModel model =
        new CalificacionModel(
            new CalificacionId(1),
            LocalDateTime.now(),
            new CalificacionEstudiante("Juan Perez"),
            new CalificacionDocente("Prof. Gomez"),
            new CalificacionAsignatura("Matematicas"),
            new CalificacionCarrera("Ingenieria"),
            new CalificacionUniversidad("Universidad Nacional"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Parcial 1"),
            new CalificacionNota(new BigDecimal("4.50")));

    when(createCalificacionUseCase.execute(any(CreateCalificacionCommand.class))).thenReturn(model);

    mockMvc
        .perform(post("/api/calificaciones").contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.cid").value(1))
        .andExpect(jsonPath("$.estudiante").value("Juan Perez"));
  }

  @Test
  @WithMockUser
  @DisplayName("GET /api/calificaciones retorna todas las calificaciones")
  void shouldGetAllCalificaciones() throws Exception {
    final CalificacionModel model =
        new CalificacionModel(
            new CalificacionId(1),
            LocalDateTime.now(),
            new CalificacionEstudiante("Juan Perez"),
            new CalificacionDocente("Prof. Gomez"),
            new CalificacionAsignatura("Matematicas"),
            new CalificacionCarrera("Ingenieria"),
            new CalificacionUniversidad("Universidad Nacional"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Parcial 1"),
            new CalificacionNota(new BigDecimal("4.50")));

    when(getAllCalificacionesUseCase.execute()).thenReturn(List.of(model));

    mockMvc
        .perform(get("/api/calificaciones"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].cid").value(1))
        .andExpect(jsonPath("$[0].estudiante").value("Juan Perez"));
  }

  @Test
  @WithMockUser
  @DisplayName("GET /api/calificaciones/{id} retorna la calificación por ID")
  void shouldGetCalificacionById() throws Exception {
    final CalificacionModel model =
        new CalificacionModel(
            new CalificacionId(1),
            LocalDateTime.now(),
            new CalificacionEstudiante("Juan Perez"),
            new CalificacionDocente("Prof. Gomez"),
            new CalificacionAsignatura("Matematicas"),
            new CalificacionCarrera("Ingenieria"),
            new CalificacionUniversidad("Universidad Nacional"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Parcial 1"),
            new CalificacionNota(new BigDecimal("4.50")));

    when(getCalificacionByIdUseCase.execute(new GetCalificacionByIdQuery(1))).thenReturn(model);

    mockMvc
        .perform(get("/api/calificaciones/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cid").value(1))
        .andExpect(jsonPath("$.estudiante").value("Juan Perez"));
  }

  @Test
  @WithMockUser
  @DisplayName("PUT /api/calificaciones/{id} actualiza la calificación")
  void shouldUpdateCalificacion() throws Exception {
    final String request =
        """
        {
          "estudiante": "Juan Perez",
          "docente": "Prof. Gomez",
          "asignatura": "Matematicas",
          "carrera": "Ingenieria",
          "universidad": "Universidad Nacional",
          "periodo": "2026-1",
          "actividadEvaluada": "Parcial 1",
          "nota": 4.80
        }
        """;

    final CalificacionModel model =
        new CalificacionModel(
            new CalificacionId(1),
            LocalDateTime.now(),
            new CalificacionEstudiante("Juan Perez"),
            new CalificacionDocente("Prof. Gomez"),
            new CalificacionAsignatura("Matematicas"),
            new CalificacionCarrera("Ingenieria"),
            new CalificacionUniversidad("Universidad Nacional"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Parcial 1"),
            new CalificacionNota(new BigDecimal("4.80")));

    when(updateCalificacionUseCase.execute(any(UpdateCalificacionCommand.class))).thenReturn(model);

    mockMvc
        .perform(
            put("/api/calificaciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cid").value(1))
        .andExpect(jsonPath("$.nota").value(4.80));
  }

  @Test
  @WithMockUser
  @DisplayName("DELETE /api/calificaciones/{id} elimina la calificación")
  void shouldDeleteCalificacion() throws Exception {
    mockMvc.perform(delete("/api/calificaciones/1")).andExpect(status().isNoContent());

    verify(deleteCalificacionUseCase).execute(new DeleteCalificacionCommand(1));
  }
}
