package view.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.CollageModel;
import model.IListOfPixel;
import model.IPixel;
import model.Posn;

/**
 * A JPanel solely displaying the image of the program.
 */
public class DisplayPanel extends JPanel {
  // Components of DisplayPanel
  private JLabel imageDisplay;
  private ImageIcon img;
  private CollageModel model;

  /**
   * Creates a JPanel that displays the image.
   *
   * @param model - the model to get the image from
   * @throws IllegalArgumentException - when the given model is null
   */
  public DisplayPanel(CollageModel model) throws IllegalArgumentException {
    super();
    if(Objects.isNull(model)) {
      throw new IllegalArgumentException("Given model is null");
    }
    this.model = model;

    // set this panels properties
    this.setLayout(new BorderLayout());
    this.setMaximumSize(new Dimension(this.model.getWidth(), this.model.getHeight()));
    this.setSize(new Dimension(this.model.getWidth(), this.model.getHeight()));

    // set up the image display
    this.imageDisplay = new JLabel();

    // set the Icon
    this.img = new ImageIcon(this.createImageFromScratch(this.model.saveImage()));
    this.imageDisplay.setIcon(this.img);
    this.add(imageDisplay);

    this.setVisible(true);
  }

  /**
   * Creates an image object for viewing such that each pixel's value is
   * represented is a single 32-bit number such that 8 bits of the
   * number correspond to a particular component of a 4-component
   * representation (R,G,B,A). The format of the number is below:
   *
   * AAAAAAAARRRRRRRRGGGGGGGGBBBBBBBB
   *   alpha    red    green   blue
   *
   * @return a new handcrafted image for display
   */
  private Image createImageFromScratch(IListOfPixel img) {
    BufferedImage image = new BufferedImage(img.getWidth(),
            img.getHeight(), BufferedImage.TYPE_INT_ARGB);

    //Iterating so x moves to the right and y moves down
    for(int x = 0; x < image.getHeight(); x++) {
      for(int y = 0; y < image.getWidth(); y++) {
        IPixel p = img.getPixel(new Posn(x,y));
        int r = p.getValues()[0];
        int g = p.getValues()[1];
        int b = p.getValues()[2];
        int a = p.getValues()[3];

        // Taking the 4 values and creating a single number

        // For Systems folks: this is a use of bit-packing
        // It's a compact representation of a pixel made fast!

        // For everyone: Notice how this representation loses ALL
        // meaning without any flexibility? Also how you need to know
        // something about manipulating bits to get anything out of
        // this? Maybe not the best to use...
        int argb = a << 24;
        argb |= r << 16;
        argb |= g << 8;
        argb |= b;
        image.setRGB(y, x, argb);
      }
    }
    return image;
  }

  /**
   * Refreshes the Image panel and renders it again.
   *
   * @param model - refreshes the image panel with the given model
   */
  public void refresh(CollageModel model) {
    this.model = model;
    this.img = new ImageIcon(this.createImageFromScratch(this.model.saveImage()));
    this.imageDisplay.setIcon(this.img);
    this.add(imageDisplay);

    this.invalidate();
    this.validate();
    this.repaint();
  }
}
