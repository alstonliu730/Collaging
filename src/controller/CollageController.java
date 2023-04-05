package controller;

/**
 * Interface that defines the behavior of a controller for collaging.
 * Runs the controller.
 * Executes given commands as strings.
 */
public interface CollageController {
  /**
   * Runs the controller to take input and output using the model.
   */
  void runCollage();

  /**
   * Runs the given command based on the option.
   *
   * @param command - the command to execute.
   */
  void runCommands(String command, String... args);
}
