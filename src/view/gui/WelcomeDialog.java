package view.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.CollageModel;
import model.CollagePPM;
import util.ImageUtil;

/**
 * A class that represents the welcoming window.
 */
public class WelcomeDialog extends JDialog {
  private CollageModel model;
  private JFrame prevFrame;
  private JButton newButton;
  private JButton loadButton;
  private JLabel welcomeLabel;

  /**
   * Creates the structure of the window.
   *
   * @param model - the model to be based on
   * @param frame - the main view
   */
  public WelcomeDialog(CollageModel model, JFrame frame) {
    super(frame, true);
    this.model = model;
    this.prevFrame = frame;

    this.setSize(new Dimension(400,200));
    this.setLocation(500,500);
    this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    this.welcomeLabel = new JLabel("Welcome to Collage");
    this.welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.welcomeLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
    this.add(this.welcomeLabel);

    this.newButton = new JButton("New Project");
    this.newButton.setActionCommand("New Project");
    this.newButton.setMargin(new Insets(5,5,5,5));
    this.newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.newButton.addActionListener((evt) -> this.updateNewModel());
    this.add(this.newButton);

    this.loadButton = new JButton("Load Project");
    this.loadButton.setActionCommand("Load Project");
    this.loadButton.setMargin(new Insets(5,5,5,5));
    this.loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.loadButton.addActionListener((evt) -> this.chooseFile());
    this.add(this.loadButton);
    this.setVisible(true);
  }

  /**
   * A helper function to gather information about the new project.
   */
  private void updateNewModel() {
    int height = Integer.parseInt(this.getModelHeight());
    int width = Integer.parseInt(this.getModelWidth());
    int max = Integer.parseInt(this.getModelMax());

    this.model = new CollagePPM();
    this.model.startModel(height, width, max);
    this.dispose();
  }

  /**
   * Returns the model of this window.
   *
   * @return - the model
   */
  public CollageModel getModel() {
    return this.model;
  }

  /**
   * Gets the height of the new project.
   *
   * @return - String input of the height
   */
  private String getModelHeight() {
    return JOptionPane.showInputDialog(this, "Enter a valid height:",
            "New Project", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Gets the width of the new project.
   *
   * @return - String input of the width
   */
  private String getModelWidth() {
    return JOptionPane.showInputDialog(this, "Enter a valid width:",
            "New Project", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Gets the max of the new project.
   *
   * @return - String input of the max
   */
  private String getModelMax() {
    return JOptionPane.showInputDialog(this, "Enter a max value:",
            "New Project", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Creates a file chooser for the user to find their existing project.
   */
  private void chooseFile() {
    String filePath = null;
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Collage Project", "collage");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this.prevFrame);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      filePath = f.getAbsolutePath();
    }
    if(!Objects.isNull(filePath)) {
      try {
        this.model = ImageUtil.readProject(filePath);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      this.dispose();
      this.prevFrame.setVisible(true);
    }
  }
}
