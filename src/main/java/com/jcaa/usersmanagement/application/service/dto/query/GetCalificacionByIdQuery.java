package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GetCalificacionByIdQuery(
    @NotNull(message = "cid must not be null")
    @Positive(message = "cid must be greater than zero")
    Integer cid) {
}
