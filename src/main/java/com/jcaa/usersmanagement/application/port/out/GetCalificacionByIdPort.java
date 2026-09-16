package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import java.util.Optional;

public interface GetCalificacionByIdPort {
  Optional<CalificacionModel> findById(CalificacionId id);
}
