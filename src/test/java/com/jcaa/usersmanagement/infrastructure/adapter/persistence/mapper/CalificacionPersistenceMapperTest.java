package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CalificacionPersistenceMapper")
@ExtendWith(MockitoExtension.class)
class CalificacionPersistenceMapperTest {

  @Mock private ResultSet resultSet;

  private CalificacionModel model;
  private CalificacionEntity entity;
  private LocalDateTime now;

  @BeforeEach
  void setUp() {
    now = LocalDateTime.now();
    model =
        new CalificacionModel(
            new CalificacionId(1),
            now,
            new CalificacionEstudiante("Estudiante"),
            new CalificacionDocente("Docente"),
            new CalificacionAsignatura("Asignatura"),
            new CalificacionCarrera("Carrera"),
            new CalificacionUniversidad("Universidad"),
            new CalificacionPeriodo("2026-1"),
            new CalificacionActividadEvaluada("Actividad"),
            new CalificacionNota(new BigDecimal("4.50")));

    entity =
        new CalificacionEntity(
            1,
            now,
            "Estudiante",
            "Docente",
            "Asignatura",
            "Carrera",
            "Universidad",
            "2026-1",
            "Actividad",
            new BigDecimal("4.50"));
  }

  @Test
  @DisplayName("fromModelToDto() mapea correctamente")
  void shouldMapModelToDto() {
    final CalificacionPersistenceDto dto = CalificacionPersistenceMapper.fromModelToDto(model);

    assertAll(
        "dto mapping",
        () -> assertEquals(1, dto.cid()),
        () -> assertEquals("Estudiante", dto.estudiante()),
        () -> assertEquals("Docente", dto.docente()),
        () -> assertEquals(new BigDecimal("4.50"), dto.nota()));
  }

  @Test
  @DisplayName("fromEntityToModel() mapea correctamente")
  void shouldMapEntityToModel() {
    final CalificacionModel mapped = CalificacionPersistenceMapper.fromEntityToModel(entity);

    assertAll(
        "model mapping",
        () -> assertEquals(1, mapped.getCid().value()),
        () -> assertEquals("Estudiante", mapped.getEstudiante().value()),
        () -> assertEquals(new BigDecimal("4.50"), mapped.getNota().value()));
  }

  @Test
  @DisplayName("fromResultSetToEntity() mapea correctamente desde ResultSet")
  void shouldMapResultSetToEntity() throws SQLException {
    when(resultSet.getInt("cid")).thenReturn(1);
    when(resultSet.getTimestamp("fecha")).thenReturn(Timestamp.valueOf(now));
    when(resultSet.getString("estudiante")).thenReturn("Estudiante");
    when(resultSet.getString("docente")).thenReturn("Docente");
    when(resultSet.getString("asignatura")).thenReturn("Asignatura");
    when(resultSet.getString("carrera")).thenReturn("Carrera");
    when(resultSet.getString("universidad")).thenReturn("Universidad");
    when(resultSet.getString("periodo")).thenReturn("2026-1");
    when(resultSet.getString("actividadEvaluada")).thenReturn("Actividad");
    when(resultSet.getBigDecimal("nota")).thenReturn(new BigDecimal("4.50"));

    final CalificacionEntity result = CalificacionPersistenceMapper.fromResultSetToEntity(resultSet);

    assertNotNull(result);
    assertEquals(1, result.cid());
    assertEquals("Estudiante", result.estudiante());
  }

  @Test
  @DisplayName("fromResultSetToModelList() mapea lista correctamente")
  void shouldMapResultSetToList() throws SQLException {
    when(resultSet.next()).thenReturn(true, false);
    when(resultSet.getInt("cid")).thenReturn(1);
    when(resultSet.getTimestamp("fecha")).thenReturn(Timestamp.valueOf(now));
    when(resultSet.getString("estudiante")).thenReturn("Estudiante");
    when(resultSet.getString("docente")).thenReturn("Docente");
    when(resultSet.getString("asignatura")).thenReturn("Asignatura");
    when(resultSet.getString("carrera")).thenReturn("Carrera");
    when(resultSet.getString("universidad")).thenReturn("Universidad");
    when(resultSet.getString("periodo")).thenReturn("2026-1");
    when(resultSet.getString("actividadEvaluada")).thenReturn("Actividad");
    when(resultSet.getBigDecimal("nota")).thenReturn(new BigDecimal("4.50"));

    final List<CalificacionModel> list = CalificacionPersistenceMapper.fromResultSetToModelList(resultSet);

    assertEquals(1, list.size());
  }
}
