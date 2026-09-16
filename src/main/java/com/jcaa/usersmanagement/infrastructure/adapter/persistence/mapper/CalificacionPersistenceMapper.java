package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionActividadEvaluada;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionAsignatura;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionCarrera;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionDocente;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionEstudiante;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionNota;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionPeriodo;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionUniversidad;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.CalificacionPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.CalificacionEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CalificacionPersistenceMapper {

  public CalificacionPersistenceDto fromModelToDto(final CalificacionModel calificacion) {
    return new CalificacionPersistenceDto(
        calificacion.getCid() != null ? calificacion.getCid().value() : 0,
        calificacion.getFecha(),
        calificacion.getEstudiante().value(),
        calificacion.getDocente().value(),
        calificacion.getAsignatura().value(),
        calificacion.getCarrera().value(),
        calificacion.getUniversidad().value(),
        calificacion.getPeriodo().value(),
        calificacion.getActividadEvaluada().value(),
        calificacion.getNota().value());
  }

  public CalificacionEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    final Timestamp timestamp = resultSet.getTimestamp("fecha");
    final LocalDateTime fecha = Objects.nonNull(timestamp) ? timestamp.toLocalDateTime() : null;

    return new CalificacionEntity(
        resultSet.getInt("cid"),
        fecha,
        resultSet.getString("estudiante"),
        resultSet.getString("docente"),
        resultSet.getString("asignatura"),
        resultSet.getString("carrera"),
        resultSet.getString("universidad"),
        resultSet.getString("periodo"),
        resultSet.getString("actividadEvaluada"),
        resultSet.getBigDecimal("nota"));
  }

  public CalificacionModel fromEntityToModel(final CalificacionEntity entity) {
    return new CalificacionModel(
        new CalificacionId(entity.cid()),
        entity.fecha(),
        new CalificacionEstudiante(entity.estudiante()),
        new CalificacionDocente(entity.docente()),
        new CalificacionAsignatura(entity.asignatura()),
        new CalificacionCarrera(entity.carrera()),
        new CalificacionUniversidad(entity.universidad()),
        new CalificacionPeriodo(entity.periodo()),
        new CalificacionActividadEvaluada(entity.actividadEvaluada()),
        new CalificacionNota(entity.nota()));
  }

  public CalificacionModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<CalificacionModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<CalificacionModel> calificaciones = new ArrayList<>();
    while (resultSet.next()) {
      calificaciones.add(fromResultSetToModel(resultSet));
    }
    return calificaciones;
  }
}
