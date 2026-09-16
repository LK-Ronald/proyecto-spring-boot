package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.CalificacionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CalificacionController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateCalificacionRequest;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateCalificacionHandler implements OperationHandler {

  private final CalificacionController calificacionController;
  private final ConsoleIO console;
  private final CalificacionResponsePrinter printer;

  @Override
  public void handle() {
    final String estudiante = console.readRequired("Estudiante        : ");
    final String docente = console.readRequired("Docente           : ");
    final String asignatura = console.readRequired("Asignatura        : ");
    final String carrera = console.readRequired("Carrera           : ");
    final String universidad = console.readRequired("Universidad       : ");
    final String periodo = console.readRequired("Periodo           : ");
    final String actividadEvaluada = console.readRequired("Actividad Evaluada: ");
    final String notaStr = console.readRequired("Nota (ej. 4.50)   : ");

    final BigDecimal nota = new BigDecimal(notaStr.trim());
    final CalificacionResponse created =
        calificacionController.createCalificacion(
            new CreateCalificacionRequest(
                estudiante,
                docente,
                asignatura,
                carrera,
                universidad,
                periodo,
                actividadEvaluada,
                nota));

    console.println("\n  Calificación created successfully.");
    printer.print(created);
  }
}
