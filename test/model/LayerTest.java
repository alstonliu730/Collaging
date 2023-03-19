package model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LayerTest {
  Layer l1;
  Layer l2;
  Layer l3;

  @Test
  public void testValidConstruction() {
    this.l1 = new Layer("first", 4,4,255);
    assertEquals("first", this.l1.getName());

    this.l2 = new Layer("second", 4, 4, 255);
    assertEquals("second", this.l2.getName());
  }

  @Test
  public void testInvalidConstruction() {
    try {
      this.l1 = new Layer("first", -4, 4,255);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Layer inputs!", e.getMessage());
    }

    try {
      this.l2 = new Layer(null, 4, 4, 255);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Layer inputs!", e.getMessage());
    }

    try {
      this.l3 = new Layer("third", 4,4, -255);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Layer inputs!", e.getMessage());
    }
  }

  @Test
  public void testRender() {
    this.l1 = new Layer("first", 1,1,255);
    List<Pixel> pixels = this.l1.render();

    assertEquals("255 255 255", pixels.get(0).ppmFormat());
  }


}