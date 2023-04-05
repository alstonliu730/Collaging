package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A test class to tets the Posn class.
 */
public class PosnTest {
  Posn p1;
  Posn p2;

  @Test
  public void testGetRow() {
    this.p1 = new Posn(3, 5);
    this.p2 = new Posn(4, 9);
    assertEquals(3, this.p1.getRow());
    assertEquals(4, this.p2.getRow());
  }

  @Test
  public void testGetCol() {
    this.p1 = new Posn(3, 5);
    this.p2 = new Posn(4, 9);
    assertEquals(5, this.p1.getCol());
    assertEquals(9, this.p2.getCol());
  }

  @Test
  public void testToString() {
    this.p1 = new Posn(3, 5);
    this.p2 = new Posn(4, 9);
    assertEquals("3 5", this.p1.toString());
    assertEquals("4 9", this.p2.toString());
  }
}