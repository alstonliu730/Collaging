package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ImageTest  {
  Image img1;
  Image img2;

  @Test
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
    assertEquals(255, this.img1.getMax());
  }

  @Test
  public void testInvalidConstruction() {
    try {
      this.img1 = new Image(-10, 10, new Posn(0,0));
      fail("Should not be able to input a negative number for the width or height!");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Image input!", e.getMessage());
    }

    try {
      this.img1 = new Image(10,10, null);
      fail("Should not be able to input a null in the position!");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Image input!", e.getMessage());
    }

    try {
      this.img1 = new Image(10,10,-23, new Posn(0,0));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid max value input!", e.getMessage());
    }

    try {
      this.img1 = new Image(null, new Posn(0,0));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid input!", e.getMessage());
    }
  }
}
