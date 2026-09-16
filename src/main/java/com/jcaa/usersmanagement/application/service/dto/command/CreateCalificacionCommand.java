package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateCalificacionCommand(
    @NotBlank(message = "estudiante must not be blank")
    @Size(max = 100, message = "estudiante must not exceed 100 characters")
    String estudiante,

    @NotBlank(message = "docente must not be blank")
    @Size(max = 100, message = "docente must not exceed 100 characters")
    String docente,

    @NotBlank(message = "asignatura must not be blank")
    @Size(max = 60, message = "asignatura must not exceed 60 characters")
    String asignatura,

    @NotBlank(message = "carrera must not be blank")
    @Size(max = 60, message = "carrera must not exceed 60 characters")
    String carrera,

    @NotBlank(message = "universidad must not be blank")
    @Size(max = 100, message = "universidad must not exceed 100 characters")
    String universidad,

    @NotBlank(message = "periodo must not be blank")
    @Size(max = 20, message = "periodo must not exceed 20 characters")
    String periodo,

    @NotBlank(message = "actividadEvaluada must not be blank")
    @Size(max = 100, message = "actividadEvaluada must not exceed 100 characters")
    String actividadEvaluada,

    @NotNull(message = "nota must not be null")
    @DecimalMin(value = "0.00", message = "nota must be at least 0.00")
    @DecimalMax(value = "99.99", message = "nota must not exceed 99.99")
    BigDecimal nota) {
}
