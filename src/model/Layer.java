package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Layer implements IListOfPixel{
  String name;
  int height, width;

  List<Image> images;

  /**
   * Creates a layer of pixels with the given height and width.
   * @param name
   * @param height
   * @param width
   * @throws IllegalArgumentException
   */
  public Layer(String name, int height, int width) throws IllegalArgumentException {
    if(Objects.isNull(name) || height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Invalid input!");
    }
    
    this.name = name;
    this.height = height;
    this.width = width;
    this.images = new ArrayList<Image>();

    //set background as translucent layer
    this.images.add(new Image(this.height, this.width, new Posn(0,0)));
  }

  /**
   *
   * @param img
   */
  @Override
  public void importImg(IListOfPixel img) {

  }

  /**
   * @return
   */
  @Override
  public ArrayList<Pixel> render() {
    return null;
  }
}
