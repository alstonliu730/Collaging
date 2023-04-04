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
    List<IPixel> pixels = this.l1.render();

    assertEquals("255 255 255", pixels.get(0).toString());
  }
  @Test
  public void testGetName() {
    this.l1 = new Layer("first",1,1,255);
    this.l2 = new Layer("second", 1,1,255);

    assertEquals("first", this.l1.getName());
    assertEquals("second", this.l2.getName());
  }

  @Test
  public void testGetSetPixel() {
    this.l1 = new Layer("first", 2,2,255);

    // Set specific pixels
    this.l1.setPixel(new Posn(0,0), new Pixel(123,100,29, 255, new Posn(0,0)));
    this.l1.setPixel(new Posn(1,1), new Pixel(30,230,200, 255, new Posn(1,1)));

    // Get the pixel at the specific position
    assertEquals("123 100 29", this.l1.getPixel(new Posn(0,0)).toString());
    assertEquals("30 230 200", this.l1.getPixel(new Posn(1,1)).toString());
  }

  @Test
  public void testSetMatrix() {
    // Create the input matrix
    Pixel[][] input = new Pixel[2][2];
    input[0][0] = new Pixel(45, 200, 190, 255, new Posn(0,0));
    input[0][1] = new Pixel(0, 0, 0, 255, new Posn(0,1));
    input[1][0] = new Pixel(0, 0, 0, 255, new Posn(1,0));
    input[1][1] = new Pixel(38, 140, 230, 255, new Posn(1,1));

    // create layer object
    this.l1 = new Layer("first", 2,2,255);

    // set the matrix of this layer
    this.l1.setMatrix(input);

    // make sure the layer has the matrix set
    assertEquals("45 200 190", this.l1.getPixel(new Posn(0,0)));
    assertEquals("0 0 0", this.l1.getPixel(new Posn(0,1)));
    assertEquals("0 0 0", this.l1.getPixel(new Posn(1,0)));
    assertEquals("38 140 230", this.l1.getPixel(new Posn(1,1)));
  }

  @Test
  public void testGetHeight() {
    this.l1 = new Layer("first", 10,10,255);
    this.l2 = new Layer("second", 400,200, 255);
    this.l3 = new Layer("third", 1980,1080, 255);

    assertEquals(10, this.l1.getHeight());
    assertEquals(400, this.l1.getHeight());
    assertEquals(1980, this.l1.getHeight());
  }

  @Test
  public void testGetWidth() {
    this.l1 = new Layer("first", 10,10,255);
    this.l2 = new Layer("second", 400,200, 255);
    this.l3 = new Layer("third", 1980,1080, 255);

    assertEquals(10, this.l1.getWidth());
    assertEquals(400, this.l1.getWidth());
    assertEquals(1980, this.l1.getWidth());
  }

  @Test
  public void testApplyFilter() {
    this.l1 = new Layer("first", 2,2,255);
    this.l1.setFilter(Filter.RED);

    // prepare the previous layer
    this.l2 = new Layer("background", 2, 2, 255);
    this.l2.setFilter(Filter.NORMAL);
    for(int i = 0; i < 2; i++) {
      for(int j = 0; j < 2; j++) {
        this.l2.setPixel(new Posn(i,j), new Pixel(255,255,255,255, new Posn(i,j)));
      }
    }
    // The grid with specific pixels
    this.l1.setPixel(new Posn(0,0), new Pixel(150,50,89, 255, new Posn(0,0)));
    this.l1.setPixel(new Posn(1,1), new Pixel(230,40,100, 255, new Posn(1,1)));
    assertEquals("150 50 89", this.l1.getPixel(new Posn(0,0)).toString());
    assertEquals("230 40 100", this.l1.getPixel(new Posn(1,1)).toString());

    // Apply the filter: red-component
    this.l1.applyFilter(this.l2);

    // Make sure the filter changed the pixel to only red
    assertEquals("150 0 0", this.l1.getPixel(new Posn(0,0)).toString());
    assertEquals("230 0 0", this.l1.getPixel(new Posn(1,1)).toString());

    // test For Darken Filter
    this.l1 = new Layer("first", 2, 2, 255);
    this.l1.setFilter(Filter.DARKEN);

    // The grid with specific pixels
    this.l1.setPixel(new Posn(0,0), new Pixel(150,50,89, 255, new Posn(0,0)));
    this.l1.setPixel(new Posn(1,1), new Pixel(230,40,100, 255, new Posn(1,1)));
    assertEquals("150 50 89", this.l1.getPixel(new Posn(0,0)).toString());
    assertEquals("230 40 100", this.l1.getPixel(new Posn(1,1)).toString());

    // Apply the filter: darken
    this.l1.applyFilter(this.l2);

    // Make sure the filter is unchanged since the background is white
    assertEquals("150 50 89", this.l1.getPixel(new Posn(0,0)).toString());
    assertEquals("230 40 100", this.l1.getPixel(new Posn(1,1)).toString());
  }

  @Test
  public void testAddImage() {
    // Create the image matrix
    Pixel[][] input = new Pixel[2][2];
    input[0][0] = new Pixel(45, 200, 190, 255, new Posn(0,0));
    input[0][1] = new Pixel(0, 0, 0, 255, new Posn(0,1));
    input[1][0] = new Pixel(0, 0, 0, 255, new Posn(1,0));
    input[1][1] = new Pixel(38, 140, 230, 255, new Posn(1,1));

    Image img = new Image(input, new Posn(0,0));
    this.l1 = new Layer("first",3,3,255);

  }
}