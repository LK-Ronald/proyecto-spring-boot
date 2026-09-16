package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CalificacionResponse(
    int cid,
    LocalDateTime fecha,
    String estudiante,
    String docente,
    String asignatura,
    String carrera,
    String universidad,
    String periodo,
    String actividadEvaluada,
    BigDecimal nota) {}
