package controller;

public interface CollageController {
  /**
   * Runs the controller to take input and output using the model.
   */
  void runCollage();

  /**
   * Runs the given command based on the option
   * @param command - the command to execute
   */
  void runCommands(String command, String... args);
}
