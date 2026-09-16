package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetCalificacionByIdUseCase {
  CalificacionModel execute(@NotNull @Valid GetCalificacionByIdQuery query);
}
