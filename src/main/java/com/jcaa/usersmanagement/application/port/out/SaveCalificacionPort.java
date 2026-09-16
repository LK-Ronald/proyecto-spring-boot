package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;

public interface SaveCalificacionPort {
  CalificacionModel save(CalificacionModel calificacion);
}
