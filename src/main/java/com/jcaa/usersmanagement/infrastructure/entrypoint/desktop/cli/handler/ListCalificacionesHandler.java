package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.CalificacionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CalificacionController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListCalificacionesHandler implements OperationHandler {

  private final CalificacionController calificacionController;
  private final CalificacionResponsePrinter printer;

  @Override
  public void handle() {
    printer.printList(calificacionController.listAllCalificaciones());
  }
}
