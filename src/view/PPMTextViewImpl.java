package view;

import java.io.IOException;
import java.util.Objects;

import model.CollageModel;

/**
 * A class that represents the PPM text view of the controller.
 */
public class PPMTextViewImpl implements TextView {
  private Appendable out;
  private CollageModel model;

  /**
   * Creates an object view in the PPM format.
   *
   * @param out   - the Appendable object to output
   * @param model - the model to base the view on
   * @throws IllegalArgumentException - when the given objects are null
   */
  public PPMTextViewImpl(Appendable out, CollageModel model) throws IllegalArgumentException {
    if (Objects.isNull(out) || Objects.isNull(model)) {
      throw new IllegalArgumentException("Invalid TextView input!");
    }

    this.out = out;
    this.model = model;
  }

  /**
   * Renders the given message into the data output of the implementation.
   *
   * @param message - the message to be printed
   * @throws IOException - if the transmission of the message to the data output fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }

  /**
   * Renders the project into the data output of the implementation
   *
   * @throws IOException - if the transmission of the project to the data output fails
   */
  @Override
  public void render() throws IOException {
    this.out.append(this.toString());
  }

  /**
   * Produces a textual view of the pixels in the project.
   * The first 3 rows of output will be the project information.
   * Then the view will output the Layers and the Layer Content.
   *
   * @return - representation of the current Project state
   */
  public String toString() {
    return this.model.toString();
  }
}
