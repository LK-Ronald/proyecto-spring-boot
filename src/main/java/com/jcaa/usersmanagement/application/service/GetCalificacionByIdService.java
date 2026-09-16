package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetCalificacionByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
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
public class GetCalificacionByIdService implements GetCalificacionByIdUseCase {

  private final GetCalificacionByIdPort getCalificacionByIdPort;
  private final Validator validator;

  @Override
  public CalificacionModel execute(final GetCalificacionByIdQuery query) {
    validateQuery(query);

    final CalificacionId id =
        CalificacionApplicationMapper.fromGetCalificacionByIdQueryToCalificacionId(query);
    return getCalificacionByIdPort
        .findById(id)
        .orElseThrow(() -> CalificacionNotFoundException.becauseIdWasNotFound(id.value()));
  }

  private void validateQuery(final GetCalificacionByIdQuery query) {
    final Set<ConstraintViolation<GetCalificacionByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
