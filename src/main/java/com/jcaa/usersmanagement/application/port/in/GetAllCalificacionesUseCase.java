package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import java.util.List;

public interface GetAllCalificacionesUseCase {
  List<CalificacionModel> execute();
}
