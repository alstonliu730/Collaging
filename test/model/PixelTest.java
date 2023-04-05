package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PixelTest {
  Pixel p1;
  Pixel p2;
  Pixel p3;

  @Test
  public void testValidConstruction() {
    this.p1 = new Pixel(255, 255, 255, 255, new Posn(0, 0));
    assertEquals(new Posn(0, 0), this.p1.getPos());
    assertEquals("255 255 255", this.p1.toString());
  }

  @Test
  public void testInvalidConstruction() {
    try {
      this.p1 = new Pixel(255, 255, -255, 255, new Posn(0, 0));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Pixel input!", e.getMessage());
    }

    try {
      this.p2 = new Pixel(23, 200, 142, -255, new Posn(0, 0));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Pixel input!", e.getMessage());
    }

    try {
      this.p3 = new Pixel(23, 200, 142, 255, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Pixel input!", e.getMessage());
    }
  }

  @Test
  public void testCombine() {
    this.p1 = new Pixel(200, 200, 200, 255, 255, new Posn(0, 0));
    this.p2 = new Pixel(50, 200, 150, 255, 255, new Posn(0, 0));

    assertEquals("200 200 200", this.p1.combine(this.p2).toString());
  }

  /* OLD tests
  @Test
  public void testBrighten() {
    this.p1 = new Pixel(100, 100, 100, 255, new Posn(0, 0));
    this.p1.brighten();
    assertEquals("204 204 204", this.p1.ppmFormat());
  }

  @Test
  public void testDarken() {
    this.p1 = new Pixel(100, 100, 100, 255, new Posn(0, 0));
    this.p1.darken();
    assertEquals("0 0 0", this.p1.ppmFormat());
  }*/

  @Test
  public void testApplyFilter() {
    this.p1 = new Pixel(100, 100, 100, 255, 255, new Posn(0, 0));
    this.p1.applyFilter("r");
    assertEquals("100 0 0", this.p1.toString());

    this.p2 = new Pixel(100, 100, 100, 255, 255, new Posn(0, 0));
    this.p2.applyFilter("g");
    assertEquals("0 100 0", this.p2.toString());

    this.p3 = new Pixel(100, 100, 100, 255, 255, new Posn(0, 0));
    this.p3.applyFilter("b");
    assertEquals("0 0 100", this.p3.toString());
  }

  @Test
  public void testChangePos() {
    this.p1 = new Pixel(10, 10, 20, 255, 255, new Posn(0, 0));
    this.p1.changePos(new Posn(1, 1));
    assertEquals(new Posn(1, 1), this.p1.getPos());
  }

  @Test
  public void testGetPos() {
    this.p1 = new Pixel(10, 10, 20, 255, 255, new Posn(1, 1));
    assertEquals(new Posn(1, 1), this.p1.getPos());
  }

  @Test
  public void testPPMFormat() {
    this.p1 = new Pixel(100, 100, 100, 255, 255, new Posn(1, 1));
    assertEquals("100 100 100", this.p1.toString());
  }

  @Test
  public void testGetMax() {
    this.p1 = new Pixel(100, 100, 100, 255, 255, new Posn(1, 1));
    assertEquals(255, this.p1.getMax());
  }
}