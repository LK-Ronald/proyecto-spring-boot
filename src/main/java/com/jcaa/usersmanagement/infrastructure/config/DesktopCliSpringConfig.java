package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.application.port.in.CreateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUserUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllCalificacionesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUsersUseCase;
import com.jcaa.usersmanagement.application.port.in.GetCalificacionByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUserByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateCalificacionUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CalificacionController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DesktopCliSpringConfig {

  @Bean
  public UserController userController(
      final CreateUserUseCase createUserUseCase,
      final UpdateUserUseCase updateUserUseCase,
      final DeleteUserUseCase deleteUserUseCase,
      final GetUserByIdUseCase getUserByIdUseCase,
      final GetAllUsersUseCase getAllUsersUseCase,
      final LoginUseCase loginUseCase) {
    return new UserController(
        createUserUseCase,
        updateUserUseCase,
        deleteUserUseCase,
        getUserByIdUseCase,
        getAllUsersUseCase,
        loginUseCase);
  }

  @Bean
  public CalificacionController calificacionController(
      final CreateCalificacionUseCase createCalificacionUseCase,
      final UpdateCalificacionUseCase updateCalificacionUseCase,
      final DeleteCalificacionUseCase deleteCalificacionUseCase,
      final GetCalificacionByIdUseCase getCalificacionByIdUseCase,
      final GetAllCalificacionesUseCase getAllCalificacionesUseCase) {
    return new CalificacionController(
        createCalificacionUseCase,
        updateCalificacionUseCase,
        deleteCalificacionUseCase,
        getCalificacionByIdUseCase,
        getAllCalificacionesUseCase);
  }

  @Bean
  @ConditionalOnProperty(name = "cli.enabled", havingValue = "true", matchIfMissing = false)
  public CommandLineRunner cliCommandLineRunner(
      final UserController userController,
      final CalificacionController calificacionController,
      final ApplicationContext context) {
    return args -> {
      try (final Scanner scanner = new Scanner(System.in)) {
        new UserManagementCli(
                userController, calificacionController, new ConsoleIO(scanner, System.out))
            .start();
      }
      System.exit(SpringApplication.exit(context, () -> 0));
    };
  }
}
