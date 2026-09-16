package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.CalificacionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CalificacionController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateCalificacionRequest;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateCalificacionHandler implements OperationHandler {

  private final CalificacionController calificacionController;
  private final ConsoleIO console;
  private final CalificacionResponsePrinter printer;

  @Override
  public void handle() {
    final int cid = console.readInt("CID to update     : ");
    final String estudiante = console.readRequired("Estudiante        : ");
    final String docente = console.readRequired("Docente           : ");
    final String asignatura = console.readRequired("Asignatura        : ");
    final String carrera = console.readRequired("Carrera           : ");
    final String universidad = console.readRequired("Universidad       : ");
    final String periodo = console.readRequired("Periodo           : ");
    final String actividadEvaluada = console.readRequired("Actividad Evaluada: ");
    final String notaStr = console.readRequired("Nota (ej. 4.50)   : ");

    final BigDecimal nota = new BigDecimal(notaStr.trim());
    try {
      final CalificacionResponse updated =
          calificacionController.updateCalificacion(
              new UpdateCalificacionRequest(
                  cid,
                  estudiante,
                  docente,
                  asignatura,
                  carrera,
                  universidad,
                  periodo,
                  actividadEvaluada,
                  nota));
      console.println("\n  Calificación updated successfully.");
      printer.print(updated);
    } catch (final CalificacionNotFoundException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
