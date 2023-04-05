package view.gui;

import controller.CollageFeatures;
import model.CollageModel;
import model.IListOfPixel;
import view.TextView;

public interface GUIView {
  /**
   * Refreshes the GUI and renders it again with the given model.
   *
   * @param model - the model to refresh with
   */
  void refresh(CollageModel model);

  /**
   * Displays a message on a dialog as a given type of message.
   * Types of messages:
   * <ol>
   *   <li><code>JOptionPane.PLAIN_MESSAGE</code></li>
   *   <li><code>JOptionPane.WARNING_MESSAGE</code></li>
   *   <li><code>JOptionPane.ERROR_MESSAGE</code></li>
   *   <li><code>JOptionPane.INFORMATION_MESSAGE</code></li>
   * </ol>
   *
   * @param message - the message to be displayed
   * @param title - the title of the dialog
   * @param type - the type of message
   */
  void echoMessage(String message, String title, int type);

  /**
   * Provides the view with all the callbacks
   * @param features -
   */
  void addFeatures(CollageFeatures features);

  /**
   * Quits the current running program.
   */
  void quit();
}
