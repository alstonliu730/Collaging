package model;

import java.util.Arrays;

/**
 * An interface that represents the methods of a Filter.
 */
public interface IFilter {
  /**
   * Apply this filter to the given pixels.
   *
   * @param pixels - the pixels the filter will need to apply the filter
   * @return - the pixel with the filter applied
   * @throws NullPointerException - When the given pixels were not given
   */
  IPixel apply(IPixel... pixels);

  /**
   * Returns this filter's option.
   *
   * @return - the option of the filter
   */
  String getOption();

  /**
   * Returns an array of all the options of an IFilter.
   *
   * @return - All options of an IFilter
   */
  static IFilter[] values(){
    IFilter[] options = Arrays.copyOf(Filter.values(), Filter.values().length);
    return options;
  }

  /**
   * Returns an array of all the options of an IFilter as a String.
   *
   * @return - All options of an IFilter as a String
   */
  static String[] options() {
    // Create array of options for
    String[] options = new String[IFilter.values().length];
    for(int i = 0; i < IFilter.values().length; i++) {
      options[i] = IFilter.values()[i].getOption();
    }

    return options;
  }
}
