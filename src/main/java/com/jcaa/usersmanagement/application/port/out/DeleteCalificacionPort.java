package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;

public interface DeleteCalificacionPort {
  void deleteById(CalificacionId id);
}
