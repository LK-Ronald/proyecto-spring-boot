package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteCalificacionPort;
import com.jcaa.usersmanagement.application.port.out.GetAllCalificacionesPort;
import com.jcaa.usersmanagement.application.port.out.GetCalificacionByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveCalificacionPort;
import com.jcaa.usersmanagement.application.port.out.UpdateCalificacionPort;
import com.jcaa.usersmanagement.domain.exception.CalificacionNotFoundException;
import com.jcaa.usersmanagement.domain.model.CalificacionModel;
import com.jcaa.usersmanagement.domain.valueobject.CalificacionId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.CalificacionPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.CalificacionPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CalificacionRepositoryMySQL
    implements SaveCalificacionPort,
        UpdateCalificacionPort,
        GetCalificacionByIdPort,
        GetAllCalificacionesPort,
        DeleteCalificacionPort {

  private static final String SQL_INSERT =
      "INSERT INTO tb_calificaciones "
          + "(estudiante, docente, asignatura, carrera, universidad, periodo, actividadEvaluada, nota, fecha) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

  private static final String SQL_UPDATE =
      "UPDATE tb_calificaciones SET estudiante = ?, docente = ?, asignatura = ?, carrera = ?, universidad = ?, periodo = ?, actividadEvaluada = ?, nota = ? "
          + "WHERE cid = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT cid, fecha, estudiante, docente, asignatura, carrera, universidad, periodo, actividadEvaluada, nota "
          + "FROM tb_calificaciones "
          + "WHERE cid = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT cid, fecha, estudiante, docente, asignatura, carrera, universidad, periodo, actividadEvaluada, nota "
          + "FROM tb_calificaciones "
          + "ORDER BY cid ASC";

  private static final String SQL_DELETE =
      "DELETE FROM tb_calificaciones "
          + "WHERE cid = ?";

  private final DataSource dataSource;

  @Override
  public CalificacionModel save(final CalificacionModel calificacion) {
    final CalificacionPersistenceDto dto =
        CalificacionPersistenceMapper.fromModelToDto(calificacion);
    final int generatedId = executeSave(dto);
    return findByIdOrFail(new CalificacionId(generatedId));
  }

  @Override
  public CalificacionModel update(final CalificacionModel calificacion) {
    final CalificacionPersistenceDto dto =
        CalificacionPersistenceMapper.fromModelToDto(calificacion);
    executeUpdate(dto);
    return findByIdOrFail(calificacion.getCid());
  }

  @Override
  public Optional<CalificacionModel> findById(final CalificacionId id) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setInt(1, id.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(CalificacionPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(String.valueOf(id.value()), exception);
    }
  }

  @Override
  public List<CalificacionModel> findAll() {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return CalificacionPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllFailed(exception);
    }
  }

  @Override
  public void deleteById(final CalificacionId id) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setInt(1, id.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteFailed(String.valueOf(id.value()), exception);
    }
  }

  private int executeSave(final CalificacionPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement =
            connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, dto.estudiante());
      statement.setString(2, dto.docente());
      statement.setString(3, dto.asignatura());
      statement.setString(4, dto.carrera());
      statement.setString(5, dto.universidad());
      statement.setString(6, dto.periodo());
      statement.setString(7, dto.actividadEvaluada());
      statement.setBigDecimal(8, dto.nota());
      statement.executeUpdate();

      try (final ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        }
        throw PersistenceException.becauseSaveFailed(
            String.valueOf(dto.cid()), new SQLException("No generated ID obtained"));
      }
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveFailed(String.valueOf(dto.cid()), exception);
    }
  }

  private void executeUpdate(final CalificacionPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.estudiante());
      statement.setString(2, dto.docente());
      statement.setString(3, dto.asignatura());
      statement.setString(4, dto.carrera());
      statement.setString(5, dto.universidad());
      statement.setString(6, dto.periodo());
      statement.setString(7, dto.actividadEvaluada());
      statement.setBigDecimal(8, dto.nota());
      statement.setInt(9, dto.cid());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateFailed(String.valueOf(dto.cid()), exception);
    }
  }

  private CalificacionModel findByIdOrFail(final CalificacionId id) {
    return findById(id)
        .orElseThrow(() -> CalificacionNotFoundException.becauseIdWasNotFound(id.value()));
  }
}
