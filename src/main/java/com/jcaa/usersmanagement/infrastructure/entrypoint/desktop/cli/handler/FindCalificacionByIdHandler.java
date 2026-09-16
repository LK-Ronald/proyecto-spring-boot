package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.CalificacionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CalificacionController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindCalificacionByIdHandler implements OperationHandler {

  private final CalificacionController calificacionController;
  private final ConsoleIO console;
  private final CalificacionResponsePrinter printer;

  @Override
  public void handle() {
    final int id = console.readInt("CID: ");
    try {
      final CalificacionResponse calificacion = calificacionController.findCalificacionById(id);
      printer.print(calificacion);
    } catch (final CalificacionNotFoundException exception) {
      console.println("  " + exception.getMessage());
    }
  }
}
