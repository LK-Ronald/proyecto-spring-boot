package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
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
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetCalificacionByIdService")
@ExtendWith(MockitoExtension.class)
class GetCalificacionByIdServiceTest {

  @Mock private GetCalificacionByIdPort getCalificacionByIdPort;

  private GetCalificacionByIdService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new GetCalificacionByIdService(
              getCalificacionByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() retorna la calificación cuando existe")
  void shouldReturnCalificacionWhenExists() {
    // Arrange
    final GetCalificacionByIdQuery query = new GetCalificacionByIdQuery(1);
    final CalificacionModel model =
        new CalificacionModel(
            new CalificacionId(1),
            LocalDateTime.now(),
            new CalificacionEstudiante("Juan"),
            new CalificacionDocente("Docente"),
            new CalificacionAsignatura("Asignatura"),
            new CalificacionCarrera("Carrera"),
            new CalificacionUniversidad("Uni"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Actividad"),
            new CalificacionNota(new BigDecimal("5.00")));

    when(getCalificacionByIdPort.findById(new CalificacionId(1))).thenReturn(Optional.of(model));

    // Act
    final CalificacionModel result = service.execute(query);

    // Assert
    assertAll(
        "flujo feliz GetCalificacionByIdService",
        () -> assertNotNull(result, "no nulo"),
        () -> assertEquals(1, result.getCid().value(), "id correcto"));

    verify(getCalificacionByIdPort).findById(new CalificacionId(1));
  }

  @Test
  @DisplayName("execute() lanza CalificacionNotFoundException cuando no existe")
  void shouldThrowWhenNotFound() {
    // Arrange
    final GetCalificacionByIdQuery query = new GetCalificacionByIdQuery(99);
    when(getCalificacionByIdPort.findById(new CalificacionId(99))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CalificacionNotFoundException.class, () -> service.execute(query));
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando query es inválida")
  void shouldThrowWhenQueryInvalid() {
    // Arrange
    final GetCalificacionByIdQuery query = new GetCalificacionByIdQuery(-5);

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(query));
    verifyNoInteractions(getCalificacionByIdPort);
  }
}
