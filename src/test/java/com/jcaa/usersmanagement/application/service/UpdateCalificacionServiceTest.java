package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateCalificacionPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
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

@DisplayName("UpdateCalificacionService")
@ExtendWith(MockitoExtension.class)
class UpdateCalificacionServiceTest {

  @Mock private UpdateCalificacionPort updateCalificacionPort;
  @Mock private GetCalificacionByIdPort getCalificacionByIdPort;

  private UpdateCalificacionService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateCalificacionService(
              updateCalificacionPort, getCalificacionByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza la calificación cuando existe")
  void shouldUpdateCalificacionWhenExists() {
    // Arrange
    final UpdateCalificacionCommand command =
        new UpdateCalificacionCommand(
            1,
            "Juan Perez Actualizado",
            "Prof. Gomez",
            "Matematicas",
            "Ingenieria",
            "Universidad Nacional",
            "2026-1",
            "Parcial 1",
            new BigDecimal("4.80"));

    final LocalDateTime fecha = LocalDateTime.now();
    final CalificacionModel existingModel =
        new CalificacionModel(
            new CalificacionId(1),
            fecha,
            new CalificacionEstudiante("Juan Perez"),
            new CalificacionDocente("Prof. Gomez"),
            new CalificacionAsignatura("Matematicas"),
            new CalificacionCarrera("Ingenieria"),
            new CalificacionUniversidad("Universidad Nacional"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Parcial 1"),
            new CalificacionNota(new BigDecimal("4.50")));

    final CalificacionModel updatedModel =
        new CalificacionModel(
            new CalificacionId(1),
            fecha,
            new CalificacionEstudiante("Juan Perez Actualizado"),
            new CalificacionDocente("Prof. Gomez"),
            new CalificacionAsignatura("Matematicas"),
            new CalificacionCarrera("Ingenieria"),
            new CalificacionUniversidad("Universidad Nacional"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Parcial 1"),
            new CalificacionNota(new BigDecimal("4.80")));

    when(getCalificacionByIdPort.findById(new CalificacionId(1)))
        .thenReturn(Optional.of(existingModel));
    when(updateCalificacionPort.update(any(CalificacionModel.class))).thenReturn(updatedModel);

    // Act
    final CalificacionModel result = service.execute(command);

    // Assert
    assertAll(
        "flujo feliz UpdateCalificacionService",
        () -> assertNotNull(result, "resultado no debe ser null"),
        () -> assertEquals(1, result.getCid().value(), "cid debe ser 1"),
        () -> assertEquals("Juan Perez Actualizado", result.getEstudiante().value(), "estudiante"));

    verify(getCalificacionByIdPort).findById(new CalificacionId(1));
    verify(updateCalificacionPort).update(any(CalificacionModel.class));
  }

  @Test
  @DisplayName("execute() lanza CalificacionNotFoundException cuando la calificación no existe")
  void shouldThrowWhenNotFound() {
    // Arrange
    final UpdateCalificacionCommand command =
        new UpdateCalificacionCommand(
            99,
            "Juan Perez",
            "Prof. Gomez",
            "Matematicas",
            "Ingenieria",
            "Universidad Nacional",
            "2026-1",
            "Parcial 1",
            new BigDecimal("4.80"));

    when(getCalificacionByIdPort.findById(new CalificacionId(99))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CalificacionNotFoundException.class, () -> service.execute(command));
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el comando es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final UpdateCalificacionCommand command =
        new UpdateCalificacionCommand(
            0,
            "",
            "Prof. Gomez",
            "Matematicas",
            "Ingenieria",
            "Universidad Nacional",
            "2026-1",
            "Parcial 1",
            new BigDecimal("4.80"));

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(getCalificacionByIdPort, updateCalificacionPort);
  }
}
