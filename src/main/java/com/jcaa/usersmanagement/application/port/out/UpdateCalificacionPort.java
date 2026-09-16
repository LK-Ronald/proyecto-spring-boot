package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;

public interface UpdateCalificacionPort {
  CalificacionModel update(CalificacionModel calificacion);
}
