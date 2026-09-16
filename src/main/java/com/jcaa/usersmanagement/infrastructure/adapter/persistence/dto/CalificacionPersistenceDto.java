package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CalificacionPersistenceDto(
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
