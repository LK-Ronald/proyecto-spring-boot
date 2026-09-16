package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllCalificacionesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetCalificacionByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateCalificacionUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCalificacionCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetCalificacionByIdQuery;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.CalificacionRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.CalificacionRestMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionRestController implements CalificacionRestControllerDocs {

  private final CreateCalificacionUseCase createCalificacionUseCase;
  private final UpdateCalificacionUseCase updateCalificacionUseCase;
  private final DeleteCalificacionUseCase deleteCalificacionUseCase;
  private final GetCalificacionByIdUseCase getCalificacionByIdUseCase;
  private final GetAllCalificacionesUseCase getAllCalificacionesUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public CalificacionRestResponse create(
      @Valid @RequestBody final CreateCalificacionRestRequest request) {
    final CreateCalificacionCommand command = CalificacionRestMapper.toCreateCommand(request);
    final CalificacionModel model = createCalificacionUseCase.execute(command);
    return CalificacionRestMapper.toResponse(model);
  }

  @GetMapping
  @Override
  public List<CalificacionRestResponse> getAll() {
    return CalificacionRestMapper.toResponseList(getAllCalificacionesUseCase.execute());
  }

  @GetMapping("/{id}")
  @Override
  public CalificacionRestResponse getById(@PathVariable final int id) {
    final GetCalificacionByIdQuery query = CalificacionRestMapper.toGetByIdQuery(id);
    final CalificacionModel model = getCalificacionByIdUseCase.execute(query);
    return CalificacionRestMapper.toResponse(model);
  }

  @PutMapping("/{id}")
  @Override
  public CalificacionRestResponse update(
      @PathVariable final int id,
      @Valid @RequestBody final UpdateCalificacionRestRequest request) {
    final UpdateCalificacionCommand command = CalificacionRestMapper.toUpdateCommand(id, request);
    final CalificacionModel model = updateCalificacionUseCase.execute(command);
    return CalificacionRestMapper.toResponse(model);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void delete(@PathVariable final int id) {
    final DeleteCalificacionCommand command = CalificacionRestMapper.toDeleteCommand(id);
    deleteCalificacionUseCase.execute(command);
  }
}
