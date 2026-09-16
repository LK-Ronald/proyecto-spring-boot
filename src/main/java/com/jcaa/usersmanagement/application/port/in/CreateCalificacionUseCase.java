package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateCalificacionUseCase {
  CalificacionModel execute(@NotNull @Valid CreateCalificacionCommand command);
}
