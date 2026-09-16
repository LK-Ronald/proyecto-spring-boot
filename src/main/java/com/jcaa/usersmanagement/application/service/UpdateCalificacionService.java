package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateCalificacionPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.mapper.CalificacionApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCalificacionService implements UpdateCalificacionUseCase {

  private final UpdateCalificacionPort updateCalificacionPort;
  private final GetCalificacionByIdPort getCalificacionByIdPort;
  private final Validator validator;

  @Override
  public CalificacionModel execute(final UpdateCalificacionCommand command) {
    validateCommand(command);

    final CalificacionId id = new CalificacionId(command.cid());
    final CalificacionModel current = findExistingCalificacionOrFail(id);

    final CalificacionModel calificacionToUpdate =
        CalificacionApplicationMapper.fromUpdateCommandToModel(command, current.getFecha());
    return updateCalificacionPort.update(calificacionToUpdate);
  }

  private void validateCommand(final UpdateCalificacionCommand command) {
    final Set<ConstraintViolation<UpdateCalificacionCommand>> violations =
        validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private CalificacionModel findExistingCalificacionOrFail(final CalificacionId id) {
    return getCalificacionByIdPort
        .findById(id)
        .orElseThrow(() -> CalificacionNotFoundException.becauseIdWasNotFound(id.value()));
  }
}
