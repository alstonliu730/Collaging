package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import util.PixelArrayUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class to test the PPM Model Implementation.
 */
public class CollagePPMModelTest {

  CollageModel model1 = new CollagePPM();
  CollageModel model2;

  @Test
  public void testInvalidConstruction() {
    try {
      this.model1.startModel(-2, 8, 3);
      fail("Should not be able to construct with a non-positive height.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM dimension!", e.getMessage());
    }

    try {
      this.model1.startModel(2, 0, 3);
      fail("Should not be able to construct with a non-positive width.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM dimension!", e.getMessage());
    }

    try {
      this.model1.startModel(3, 4, -4);
      fail("Should not be able to construct with a non-positive MAX_VALUE.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM dimension!", e.getMessage());
    }
  }

  @Test
  public void testParamInitialization() {
    this.model1.startModel(4, 5, 7);
    assertEquals(4, this.model1.getHeight());
    assertEquals(5, this.model1.getWidth());
    assertEquals(7, this.model1.getMax());

    try {
      Layer layer = this.model1.getLayer("layer-name");
      fail("Should not be able to get a layer when list of array has only the background.");
    } catch (IllegalStateException e) {
      assertEquals("Invalid Layer name!", e.getMessage());
    }

    Layer layer = this.model1.getLayer("background");
    assertEquals("background", layer.getName());
    assertEquals("normal", layer.getFilter().getOption());
  }

  @Test
  public void testGetHeight() {
    this.model1.startModel(3, 4, 6);
    assertEquals(3, this.model1.getHeight());

    this.model2.startModel(5, 2, 8);
    assertEquals(5, this.model2.getHeight());
  }

  @Test
  public void testGetWidth() {
    this.model1.startModel(2, 5, 9);
    assertEquals(5, this.model1.getWidth());

    this.model2.startModel(3, 7, 10);
    assertEquals(7, this.model2.getWidth());
  }

  @Test
  public void testGetMax() {
    this.model1.startModel(3, 4, 6);
    assertEquals(6, this.model1.getMax());

    this.model2.startModel(5, 2, 8);
    assertEquals(8, this.model2.getMax());
  }

  @Test
  public void testGetLayer() {
    this.model1.startModel(5, 5, 20);

    try {
      this.model1.getLayer("table-layer");
      fail("Should not be able to get a layer that isn't in the collage.");
    } catch (IllegalStateException e) {
      assertEquals("Invalid Layer name!", e.getMessage());
    }

    this.model1.addLayer("table-layer");
    assertEquals("table-layer", this.model1.getLayer("table-layer").getName());
  }

  @Test
  public void addLayer() {
    this.model1.startModel(3, 4, 6);

    try {
      this.model1.getLayer("first-layer");
      fail("Should not be able to get a layer that isn't in the collage.");
    } catch (IllegalStateException e) {
      assertEquals("Invalid Layer name!", e.getMessage());
    }

    this.model1.addLayer("first-layer");
    assertEquals("first-layer", this.model1.getLayer("first-layer").getName());
  }

  @Test
  public void testSetFilter() {
    this.model1.startModel(3,4,6);
    this.model1.addLayer("first-layer");
    this.model1.setFilter("first-layer", Filter.RED);
    assertEquals("red-component",
            this.model1.getLayer("first-layer").getFilter().getOption());
  }

  @Test
  public void testAddImageToLayer() {
    this.model1.startModel(4,4,255);
    List<IPixel> img = new ArrayList<IPixel>();
    // red square
    for(int i = 0; i < 2; i++) {
      for(int j = 0; j < 2; j++) {
        img.add(new Pixel(255,0,0,255,255, new Posn(i,j)));
      }
    }
    Image redSquare = new Image(2,2,new Posn(0,0));
    IPixel[][] pixels = PixelArrayUtil.convertToMatrix(img,
            redSquare.getHeight(), redSquare.getWidth());
    redSquare = new Image(pixels, new Posn(0,0));

    this.model1.addLayer("first-layer");
    this.model1.addImageToLayer(this.model1.getLayer("first-layer"),
            redSquare, 0,0);
    assertEquals("255 0 0",
            this.model1.getLayer("first-layer").getPixel(new Posn(0,0)).toString());
  }

  @Test
  public void testPPMFormat() {
    this.model1.startModel(2,2,255);
    assertEquals("2 2\n255\nbackground normal\n255 255 255 255 255 255 255 "
                    + "255 255 255 255 255 \n", this.model1.toString());
  }

  @Test
  public void testSaveImage() {
    this.model1.startModel(1,1,255);
    Layer l1 = this.model1.saveImage();

    assertEquals("background", l1.getName());
  }
}
