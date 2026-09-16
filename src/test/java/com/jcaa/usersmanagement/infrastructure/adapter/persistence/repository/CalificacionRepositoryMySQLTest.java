package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
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
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CalificacionRepositoryMySQL")
@ExtendWith(MockitoExtension.class)
class CalificacionRepositoryMySQLTest {

  @Mock private DataSource dataSource;
  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private PreparedStatement selectStatement;
  @Mock private ResultSet resultSet;
  @Mock private ResultSet generatedKeys;

  private CalificacionRepositoryMySQL repository;
  private CalificacionModel model;
  private LocalDateTime now;

  @BeforeEach
  void setUp() {
    repository = new CalificacionRepositoryMySQL(dataSource);
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
  }

  private void mockResultSetFields(final ResultSet rs) throws SQLException {
    when(rs.getInt("cid")).thenReturn(1);
    when(rs.getTimestamp("fecha")).thenReturn(Timestamp.valueOf(now));
    when(rs.getString("estudiante")).thenReturn("Estudiante");
    when(rs.getString("docente")).thenReturn("Docente");
    when(rs.getString("asignatura")).thenReturn("Asignatura");
    when(rs.getString("carrera")).thenReturn("Carrera");
    when(rs.getString("universidad")).thenReturn("Universidad");
    when(rs.getString("periodo")).thenReturn("2026-1");
    when(rs.getString("actividadEvaluada")).thenReturn("Actividad");
    when(rs.getBigDecimal("nota")).thenReturn(new BigDecimal("4.50"));
  }

  @Test
  @DisplayName("save() inserta y retorna la entidad guardada")
  void shouldSaveCalificacion() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
    when(connection.prepareStatement(anyString())).thenReturn(selectStatement);
    when(statement.getGeneratedKeys()).thenReturn(generatedKeys);
    when(generatedKeys.next()).thenReturn(true);
    when(generatedKeys.getInt(1)).thenReturn(1);
    when(selectStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    mockResultSetFields(resultSet);

    final CalificacionModel saved = repository.save(model);

    assertNotNull(saved);
    assertEquals(1, saved.getCid().value());
  }

  @Test
  @DisplayName("update() actualiza y retorna la entidad")
  void shouldUpdateCalificacion() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement, selectStatement);
    when(selectStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    mockResultSetFields(resultSet);

    final CalificacionModel updated = repository.update(model);

    assertNotNull(updated);
    assertEquals(1, updated.getCid().value());
  }

  @Test
  @DisplayName("findById() retorna optional con la entidad cuando existe")
  void shouldFindById() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    mockResultSetFields(resultSet);

    final Optional<CalificacionModel> result = repository.findById(new CalificacionId(1));

    assertTrue(result.isPresent());
    assertEquals(1, result.get().getCid().value());
  }

  @Test
  @DisplayName("findById() retorna Optional.empty() cuando no existe")
  void shouldReturnEmptyWhenNotFound() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);

    final Optional<CalificacionModel> result = repository.findById(new CalificacionId(99));

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("findAll() retorna lista de calificaciones")
  void shouldFindAll() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, false);
    mockResultSetFields(resultSet);

    final List<CalificacionModel> result = repository.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("deleteById() ejecuta el borrado")
  void shouldDeleteById() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);

    assertDoesNotThrow(() -> repository.deleteById(new CalificacionId(1)));
    verify(statement).executeUpdate();
  }
}
