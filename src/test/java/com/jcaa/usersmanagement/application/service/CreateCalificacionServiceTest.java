package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.SaveCalificacionPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateCalificacionService")
@ExtendWith(MockitoExtension.class)
class CreateCalificacionServiceTest {

  @Mock private SaveCalificacionPort saveCalificacionPort;

  private CreateCalificacionService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new CreateCalificacionService(saveCalificacionPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda la calificación correctamente")
  void shouldSaveCalificacionWhenValid() {
    // Arrange
    final CreateCalificacionCommand command =
        new CreateCalificacionCommand(
            "Juan Perez",
            "Prof. Gomez",
            "Matematicas",
            "Ingenieria",
            "Universidad Nacional",
            "2026-1",
            "Parcial 1",
            new BigDecimal("4.50"));

    final CalificacionModel savedModel =
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

    when(saveCalificacionPort.save(any(CalificacionModel.class))).thenReturn(savedModel);

    // Act
    final CalificacionModel result = service.execute(command);

    // Assert
    assertAll(
        "flujo feliz CreateCalificacionService",
        () -> assertNotNull(result, "resultado no debe ser null"),
        () -> assertEquals(1, result.getCid().value(), "cid debe ser 1"),
        () -> assertEquals("Juan Perez", result.getEstudiante().value(), "estudiante"));

    verify(saveCalificacionPort).save(any(CalificacionModel.class));
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el comando tiene datos invalidos")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final CreateCalificacionCommand invalidCommand =
        new CreateCalificacionCommand(
            "",
            "Prof. Gomez",
            "Matematicas",
            "Ingenieria",
            "Universidad Nacional",
            "2026-1",
            "Parcial 1",
            new BigDecimal("-1.00"));

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(invalidCommand));
    verifyNoInteractions(saveCalificacionPort);
  }
}
