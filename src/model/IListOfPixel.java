package model;

import java.util.List;

public interface IListOfPixel {
  /**
   *
   * @param img
   */
  public void importImg(IListOfPixel img);

  /**
   *
   * @return
   */
  public List<Pixel> render();

}
