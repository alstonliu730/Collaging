package model;

import static org.junit.Assert.*;

public class ImageTest  {
  Image img1;
  Image img2;

  public void testValidConstruction() {
    // test valid inputs
    this.img1 = new Image(10,10,new Posn(0,0));
    assertEquals(10, this.img1.getHeight());
    assertEquals(new Posn(0,0), this.img1.getPos());

    // test max value input
    this.img2 = new Image(10,10,255, new Posn(0,0));
    assertEquals(255, this.img1.getMax());
    assertEquals(new Posn(0,0), this.img1.getPos());

    // test array input construction
    Pixel[][] matrix = new Pixel[4][4];
    for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 4; j++) {
        matrix[i][j] = new Pixel(255, 0, 0, 255, new Posn(i,j));
      }
    }
    this.img1 = new Image(matrix, new Posn(0,0));
  }
}
