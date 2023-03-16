package model;

import java.util.List;
import java.util.Objects;


public class Image implements IListOfPixel{
  int height, width;
  Pixel[][] image;
  Posn pos;

  /**
   *
   * @param height
   * @param width
   * @param pos
   * @throws IllegalArgumentException
   */
  public Image(int height, int width, Posn pos) throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || Objects.isNull(pos)) {
      throw new IllegalArgumentException("Invalid input!");
    }

    this.height = height;
    this.width = width;
    this.pos = pos;
    this.image = new Pixel[this.height][this.width];
  }

  public Image() {

  }

  /**
   *
   * @param img
   */
  @Override
  public void importImg(IListOfPixel img) {

  }

  /**
   *
   * @return
   */
  @Override
  public List<Pixel> render() {
    return null;
  }


}
