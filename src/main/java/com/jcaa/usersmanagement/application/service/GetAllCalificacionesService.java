package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllCalificacionesUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllCalificacionesPort;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllCalificacionesService implements GetAllCalificacionesUseCase {

  private final GetAllCalificacionesPort getAllCalificacionesPort;

  @Override
  public List<CalificacionModel> execute() {
    return getAllCalificacionesPort.findAll();
  }
}
