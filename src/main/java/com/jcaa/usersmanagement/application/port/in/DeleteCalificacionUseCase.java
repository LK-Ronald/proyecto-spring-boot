package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteCalificacionUseCase {
  void execute(@NotNull @Valid DeleteCalificacionCommand command);
}
