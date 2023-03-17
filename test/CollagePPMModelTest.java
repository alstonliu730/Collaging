import org.junit.Test;

import model.CollageModel;
import model.CollagePPM;
import model.Layer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class to test the PPM Model Implementation.
 */
public class CollagePPMModelTest {

  CollageModel model1;
  CollageModel model2;

  @Test
  public void testInvalidConstruction() {
    try {
      this.model1 = new CollagePPM(-2, 8, 3);
      fail("Should not be able to construct with a non-positive height.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM dimension!", e.getMessage());
    }

    try {
      this.model1 = new CollagePPM(2, 0, 3);
      fail("Should not be able to construct with a non-positive width.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM dimension!", e.getMessage());
    }

    try {
      this.model1 = new CollagePPM(3, 4, -4);
      fail("Should not be able to construct with a non-positive MAX_VALUE.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM dimension!", e.getMessage());
    }
  }

  @Test
  public void testParamInitialization() {
    this.model1 = new CollagePPM(4, 5, 7);
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
    this.model1 = new CollagePPM(3, 4, 6);
    assertEquals(3, this.model1.getHeight());

    this.model2 = new CollagePPM(5, 2, 8);
    assertEquals(5, this.model2.getHeight());
  }

  @Test
  public void testGetWidth() {
    this.model1 = new CollagePPM(2, 5, 9);
    assertEquals(5, this.model1.getWidth());

    this.model2 = new CollagePPM(3, 7, 10);
    assertEquals(7, this.model2.getWidth());
  }

  @Test
  public void testGetMax() {
    this.model1 = new CollagePPM(3, 4, 6);
    assertEquals(6, this.model1.getMax());

    this.model2 = new CollagePPM(5, 2, 8);
    assertEquals(8, this.model2.getMax());
  }

  @Test
  public void testGetLayer() {
    this.model1 = new CollagePPM(5, 5, 20);

    try {
      this.model1.getLayer("table-layer");
      fail("Should not be able to get a layer that isn't in the collage.");
    } catch (IllegalStateException e) {
      assertEquals("Invalid Layer name!", e.getMessage());
    }

    this.model1.addLayer("table-layer");
    assertEquals("table-layer", this.model1.getLayer("first-layer").getName());
  }

  @Test
  public void addLayer() {
    this.model1 = new CollagePPM(3, 4, 6);

    try {
      this.model1.getLayer("first-layer");
      fail("Should not be able to get a layer that isn't in the collage.");
    } catch (IllegalStateException e) {
      assertEquals("Invalid Layer name!", e.getMessage());
    }

    this.model1.addLayer("first-layer");
    assertEquals("first-layer", this.model1.getLayer("first-layer").getName());
  }
}
