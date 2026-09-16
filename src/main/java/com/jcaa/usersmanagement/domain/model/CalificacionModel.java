package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.CalificacionActividadEvaluada;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionAsignatura;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionCarrera;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionDocente;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionEstudiante;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionNota;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionPeriodo;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionUniversidad;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class CalificacionModel {

  CalificacionId cid;
  LocalDateTime fecha;
  CalificacionEstudiante estudiante;
  CalificacionDocente docente;
  CalificacionAsignatura asignatura;
  CalificacionCarrera carrera;
  CalificacionUniversidad universidad;
  CalificacionPeriodo periodo;
  CalificacionActividadEvaluada actividadEvaluada;
  CalificacionNota nota;

  public static CalificacionModel create(
      final CalificacionEstudiante estudiante,
      final CalificacionDocente docente,
      final CalificacionAsignatura asignatura,
      final CalificacionCarrera carrera,
      final CalificacionUniversidad universidad,
      final CalificacionPeriodo periodo,
      final CalificacionActividadEvaluada actividadEvaluada,
      final CalificacionNota nota) {
    return new CalificacionModel(
        new CalificacionId(0),
        LocalDateTime.now(),
        estudiante,
        docente,
        asignatura,
        carrera,
        universidad,
        periodo,
        actividadEvaluada,
        nota);
  }

  public CalificacionModel update(
      final CalificacionEstudiante estudiante,
      final CalificacionDocente docente,
      final CalificacionAsignatura asignatura,
      final CalificacionCarrera carrera,
      final CalificacionUniversidad universidad,
      final CalificacionPeriodo periodo,
      final CalificacionActividadEvaluada actividadEvaluada,
      final CalificacionNota nota) {
    return new CalificacionModel(
        this.cid,
        this.fecha,
        estudiante,
        docente,
        asignatura,
        carrera,
        universidad,
        periodo,
        actividadEvaluada,
        nota);
  }
}
