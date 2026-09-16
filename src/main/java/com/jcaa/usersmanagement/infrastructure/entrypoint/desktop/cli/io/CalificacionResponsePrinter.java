package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CalificacionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CalificacionResponsePrinter {

  private final ConsoleIO console;

  public void print(final CalificacionResponse c) {
    console.println();
    console.println("  ------------------------------------------");
    console.printf("  CID               : %d%n", c.cid());
    console.printf("  Fecha             : %s%n", c.fecha() != null ? c.fecha().toString() : "N/A");
    console.printf("  Estudiante        : %s%n", c.estudiante());
    console.printf("  Docente           : %s%n", c.docente());
    console.printf("  Asignatura        : %s%n", c.asignatura());
    console.printf("  Carrera           : %s%n", c.carrera());
    console.printf("  Universidad       : %s%n", c.universidad());
    console.printf("  Periodo           : %s%n", c.periodo());
    console.printf("  Actividad Evaluada: %s%n", c.actividadEvaluada());
    console.printf("  Nota              : %s%n", c.nota() != null ? c.nota().toPlainString() : "N/A");
    console.println("  ------------------------------------------");
  }

  public void printList(final List<CalificacionResponse> list) {
    if (list.isEmpty()) {
      console.println("\n  No calificaciones registered.");
      return;
    }
    console.printf("\n  Total: %d calificación(es)%n", list.size());
    list.forEach(this::print);
  }
}
