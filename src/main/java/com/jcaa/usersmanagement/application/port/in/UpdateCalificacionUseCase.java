package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateCalificacionUseCase {
  CalificacionModel execute(@NotNull @Valid UpdateCalificacionCommand command);
}
