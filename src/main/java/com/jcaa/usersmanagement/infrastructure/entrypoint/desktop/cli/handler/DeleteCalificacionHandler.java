package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CalificacionController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteCalificacionHandler implements OperationHandler {

  private final CalificacionController calificacionController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final int id = console.readInt("CID: ");
    try {
      calificacionController.deleteCalificacion(id);
      console.printf("  Calificación '%d' deleted successfully.%n", id);
    } catch (final CalificacionNotFoundException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
