package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateCalificacionRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.CalificacionRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Calificaciones", description = "Gestión de calificaciones: crear, consultar, actualizar y eliminar.")
public interface CalificacionRestControllerDocs {

  @Operation(summary = "Crear calificación", description = "Registra una nueva calificación en el sistema.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Calificación creada exitosamente.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalificacionRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  CalificacionRestResponse create(@Valid @RequestBody CreateCalificacionRestRequest request);

  @Operation(summary = "Listar todas las calificaciones", description = "Retorna la lista completa de calificaciones.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de calificaciones obtenida exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = CalificacionRestResponse.class)))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  List<CalificacionRestResponse> getAll();

  @Operation(summary = "Obtener calificación por ID", description = "Retorna los datos de una calificación específica.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Calificación encontrada.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalificacionRestResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe una calificación con el ID especificado.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  CalificacionRestResponse getById(
      @Parameter(description = "ID de la calificación a consultar", example = "1")
      @PathVariable final int id);

  @Operation(summary = "Actualizar calificación", description = "Actualiza los datos de una calificación existente.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Calificación actualizada exitosamente.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalificacionRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe una calificación con el ID especificado.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  CalificacionRestResponse update(
      @Parameter(description = "ID de la calificación a actualizar", example = "1")
      @PathVariable final int id,
      @Valid @RequestBody UpdateCalificacionRestRequest request);

  @Operation(summary = "Eliminar calificación", description = "Elimina permanentemente una calificación por su ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Calificación eliminada exitosamente."),
    @ApiResponse(
        responseCode = "404",
        description = "No existe una calificación con el ID especificado.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  void delete(
      @Parameter(description = "ID de la calificación a eliminar", example = "1")
      @PathVariable final int id);
}
