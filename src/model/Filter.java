package model;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A class that represents a Filter that changes pixels
 */
public class Filter implements Consumer<Pixel> {

  String option;

  /**
   * Creates a filter object that is given a filter option
   * @param option - the type of filter
   * @throws IllegalArgumentException - When given no option
   */
  public Filter(String option) throws IllegalArgumentException{
    if(Objects.isNull(option)) {
      throw new IllegalArgumentException("Invalid option!");
    }

    this.option = option;
  }
  /**
   * Performs this operation on the given argument.
   *
   * @param pixel the input argument
   */
  @Override
  public void accept(Pixel pixel) {
    switch(option) {
      case "brighten":
        pixel.brighten();
        break;
      case "darken":
        pixel.darken();
        break;
      case "red-component":
        pixel.applyFilter("r");
        break;
      case "green-component":
        pixel.applyFilter("g");
        break;
      case "blue-component":
        pixel.applyFilter("b");
        break;
      default:
        // decide how to handle this
        // does nothing
        break;
    }
  }
}
