package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.CollageModel;
import model.IListOfPixel;
import util.ImageUtil;

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
    if (Objects.isNull(model)) {
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

  /**
   * Creates a Java AWT Image object given an IListOfPixel image object.
   *
   * @param img - the given IListOfPixel image object
   * @return - the image object
   */
  private Image createImageFromScratch(IListOfPixel img) {
    Image image = ImageUtil.createImageObject(img);
    return image;
  }

}
