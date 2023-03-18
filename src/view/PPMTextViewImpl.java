package view;

import java.io.IOException;
import java.util.Objects;
import model.CollageModel;
import model.Layer;
import model.Pixel;

public class TextViewImpl implements TextView {
  Appendable out;
  CollageModel model;

  public TextViewImpl(Appendable out, CollageModel model) throws IllegalArgumentException {
    if(Objects.isNull(out) || Objects.isNull(model)) {
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
  public void renderProject() throws IOException {
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
    String project = "";

    // Adds project information
    project += (this.model.getWidth() + " " + this.model.getHeight() + "\n");
    project += (this.model.getMax() + "\n");

    // Adds layer information
    for(Layer l : this.model.renderLayers()) {
      project += (l.getName() + " " + l.getFilter().getOption() + "\n");
      for(Pixel p: l.render()) {
        project += (p.ppmFormat() + " ");
      }
      project += "\n";
    }

    // return the string of the project
    return project;
  }
}