package model;

/**
 * An interface that represents additional methods to Image.
 */
public interface IImage extends IListOfPixel {
  /**
   * Returns the position of this image.
   */
  IPosn getPos();

  /**
   * Change the current position of this image.
   */
  void changePos(IPosn p);
}
