package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import java.util.List;

public interface GetAllCalificacionesPort {
  List<CalificacionModel> findAll();
}
