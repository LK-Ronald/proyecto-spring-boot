package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveCalificacionPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.mapper.CalificacionApplicationMapper;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCalificacionService implements CreateCalificacionUseCase {

  private final SaveCalificacionPort saveCalificacionPort;
  private final Validator validator;

  @Override
  public CalificacionModel execute(final CreateCalificacionCommand command) {
    validateCommand(command);

    final CalificacionModel calificacionToSave =
        CalificacionApplicationMapper.fromCreateCommandToModel(command);
    return saveCalificacionPort.save(calificacionToSave);
  }

  private void validateCommand(final CreateCalificacionCommand command) {
    final Set<ConstraintViolation<CreateCalificacionCommand>> violations =
        validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
