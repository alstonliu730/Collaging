package model;

import java.util.Objects;

/**
 * A class that represents a Filter that changes pixels.
 */
public enum Filter {
  NORMAL("normal"),
  RED("red-component"),
  GREEN("green-component"),
  BLUE("blue-component"),
  DARKEN("darken"),
  BRIGHTEN("brighten"),
  INVERT("invert");
  String option;

  /**
   * Creates a filter object that is given a filter option.
   *
   * @param option - the type of filter
   * @throws IllegalArgumentException - When given no option
   */
  Filter(String option) throws IllegalArgumentException {
    if (Objects.isNull(option)) {
      throw new IllegalArgumentException("Invalid Filter option!");
    }

    this.option = option;
  }

  /**
   * Apply this filter to the given pixels.
   *
   * @param pixels - the pixels the filter will need to apply the filter
   * @return - the pixel with the filter applied
   * @throws NullPointerException - When the given pixels were not given
   */
  public IPixel apply(IPixel... pixels) throws NullPointerException {
    Objects.requireNonNull(pixels);

    switch (this.option) {
      case "red-component":
        return pixels[0].applyFilter("r");
      case "green-component":
        return pixels[0].applyFilter("g");
      case "blue-component":
        return pixels[0].applyFilter("b");
      case "darken":
        return pixels[0].darken(pixels[1]);
      case "brighten":
        return pixels[0].brighten(pixels[1]);
      case "invert":
        return pixels[0].invert(pixels[1]);
      default:
        return pixels[0];
    }
  }

  /**
   * Find the filter object with the given option.
   *
   * @param option - the given option
   * @return - the Filter object associated with that option
   * @throws IllegalArgumentException - when the given option is null
   *                                  OR when the given option is not associated with a value
   */
  public static Filter findByValue(String option)
          throws IllegalArgumentException {
    if (Objects.isNull(option)) {
      throw new IllegalArgumentException("Given option is null!");
    }
    for (Filter f : values()) {
      if (f.getOption().equalsIgnoreCase(option)) {
        return f;
      }
    }
    throw new IllegalArgumentException("No Filter associated with this option!");
  }

  /**
   * Returns this filter's option.
   *
   * @return - the option of the filter
   */
  public String getOption() {
    return this.option;
  }
}
