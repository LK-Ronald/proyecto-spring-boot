package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteCalificacionPort;
import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
import com.jcaa.usersmanagement.application.service.mapper.CalificacionApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCalificacionService implements DeleteCalificacionUseCase {

  private final DeleteCalificacionPort deleteCalificacionPort;
  private final GetCalificacionByIdPort getCalificacionByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteCalificacionCommand command) {
    validateCommand(command);

    final CalificacionId id =
        CalificacionApplicationMapper.fromDeleteCommandToCalificacionId(command);
    ensureCalificacionExists(id);
    deleteCalificacionPort.deleteById(id);
  }

  private void validateCommand(final DeleteCalificacionCommand command) {
    final Set<ConstraintViolation<DeleteCalificacionCommand>> violations =
        validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureCalificacionExists(final CalificacionId id) {
    getCalificacionByIdPort
        .findById(id)
        .orElseThrow(() -> CalificacionNotFoundException.becauseIdWasNotFound(id.value()));
  }
}
