package view;

import java.io.IOException;

/**
 * An interface that represents the view in text.
 */
public interface TextView {
  /**
   * Produces a textual view of the pixels in the project.
   * The first 3 rows of output will be the project information.
   * Then the view will output the Layers and the Layer Content.
   *
   * @return - representation of the current Project state
   */
  String toString();

  /**
   * Renders the given message into the data output of the implementation.
   *
   * @param message - the message to be printed
   * @throws IOException - if the transmission of the message to the data output fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Renders the current state of the program onto the view.
   *
   * @throws IOException - if the transmission of the project to the data output fails
   */
  void render() throws IOException;

}
