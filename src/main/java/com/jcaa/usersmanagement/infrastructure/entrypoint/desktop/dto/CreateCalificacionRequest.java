package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

import java.math.BigDecimal;

public record CreateCalificacionRequest(
    String estudiante,
    String docente,
    String asignatura,
    String carrera,
    String universidad,
    String periodo,
    String actividadEvaluada,
    BigDecimal nota) {}
