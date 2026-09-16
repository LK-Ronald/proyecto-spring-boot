package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.DeleteCalificacionPort;
import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
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

@DisplayName("DeleteCalificacionService")
@ExtendWith(MockitoExtension.class)
class DeleteCalificacionServiceTest {

  @Mock private DeleteCalificacionPort deleteCalificacionPort;
  @Mock private GetCalificacionByIdPort getCalificacionByIdPort;

  private DeleteCalificacionService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteCalificacionService(
              deleteCalificacionPort, getCalificacionByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() elimina la calificación cuando existe")
  void shouldDeleteCalificacionWhenExists() {
    // Arrange
    final DeleteCalificacionCommand command = new DeleteCalificacionCommand(1);
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

    // Act & Assert
    assertDoesNotThrow(() -> service.execute(command));
    verify(getCalificacionByIdPort).findById(new CalificacionId(1));
    verify(deleteCalificacionPort).deleteById(new CalificacionId(1));
  }

  @Test
  @DisplayName("execute() lanza CalificacionNotFoundException cuando la calificación no existe")
  void shouldThrowWhenNotFound() {
    // Arrange
    final DeleteCalificacionCommand command = new DeleteCalificacionCommand(99);
    when(getCalificacionByIdPort.findById(new CalificacionId(99))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CalificacionNotFoundException.class, () -> service.execute(command));
    verifyNoInteractions(deleteCalificacionPort);
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el comando es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final DeleteCalificacionCommand command = new DeleteCalificacionCommand(-1);

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(getCalificacionByIdPort, deleteCalificacionPort);
  }
}
