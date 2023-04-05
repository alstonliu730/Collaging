package view.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.CollageController;
import controller.CollageFeatures;
import model.CollageModel;
import model.IListOfPixel;
import model.IPixel;
import model.Layer;
import model.Pixel;
import model.Posn;
import util.ImageUtil;
import view.TextView;

public class CollageFrameView extends JFrame implements GUIView {
  // Components as fields
  private DisplayPanel imagePanel;
  private JPanel layerList;
  private JPanel infoLists;
  private JList<String> listOfLayers;
  private DefaultListModel<String> dataLayerList;
  private CommandListPanel commandList;
  private CollageModel model;
  /**
   * Creates the frame components of the CollageView.
   */
  public CollageFrameView(CollageModel model) {
    // design the skeleton of all layouts here
    super();
    this.model = model;

    // Configure frame properties
    this.setTitle("Collaging App");
    this.setSize(1280, 720); //720p
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocation(300,300); // sets the location of where it appears on the screen.
    this.setResizable(true);
    this.setLayout(new BorderLayout());
    this.setBackground(Color.DARK_GRAY);

    // Creates a usable model
    this.model = new WelcomeDialog(this.model, this).getModel();

    // Create a section to display the image
    this.imagePanel = new DisplayPanel(this.model);
    this.imagePanel.setPreferredSize(new Dimension(this.model.getWidth(), this.model.getHeight()));
    this.imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    this.add(this.imagePanel, BorderLayout.CENTER);

    // A section that displays list
    this.infoLists = new JPanel();
    this.infoLists.setLayout(new BoxLayout(this.infoLists, BoxLayout.Y_AXIS));

    // Create a section that displays a list of layers
    this.layerList = new JPanel();
    this.layerList.setBorder(BorderFactory.createTitledBorder("Layer List"));
    this.layerList.setLayout(new BoxLayout(this.layerList, BoxLayout.X_AXIS));
    this.layerList.setMinimumSize(new Dimension(200,100));
    this.layerList.setPreferredSize(new Dimension(300,200));
    this.infoLists.add(this.layerList);

    // fill list with the layer list names
    this.dataLayerList = new DefaultListModel<>();
    ArrayList<String> names = new ArrayList<String>();
    this.model.renderLayers().forEach((l) -> names.add(l.getName()));
    this.dataLayerList.addAll(names);

    // create the list of layers object and add it to the panel
    this.listOfLayers = new JList<>(this.dataLayerList);
    this.layerList.add(this.listOfLayers);

    // Create a command lists
    this.commandList = new CommandListPanel(this.model);
    this.commandList.setBorder(BorderFactory.createTitledBorder("Command List"));
    this.commandList.setMinimumSize(new Dimension(200,200));
    this.commandList.setPreferredSize(new Dimension(300,300));
    this.infoLists.add(this.commandList);

    this.add(this.infoLists, BorderLayout.EAST);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Refreshes the GUI and renders it again.
   */
  public void refresh(CollageModel model) {
    this.model = model;
    this.imagePanel.refresh(this.model);

    this.invalidate();
    this.validate();
    this.repaint();
  }

  /**
   * Displays a message on a dialog as a given type of message.
   * Types of messages:
   * <ol>
   *   <li><code>JOptionPane.PLAIN_MESSAGE</code></li>
   *   <li><code>JOptionPane.WARNING_MESSAGE</code></li>
   *   <li><code>JOptionPane.ERROR_MESSAGE</code></li>
   *   <li><code>JOptionPane.INFORMATION_MESSAGE</code></li>
   * </ol>
   *
   * @param message - the message to be displayed
   * @param title - the title of the dialog
   * @param type - the type of message
   */
  @Override
  public void echoMessage(String message, String title, int type) {
    JOptionPane.showMessageDialog(this, message, "", type);
  }

  /**
   * Provides the view with all the callbacks to the controller.
   *
   * @param features - the controller used to callback functions
   */
  @Override
  public void addFeatures(CollageFeatures features) {
    for(JButton b: this.commandList.getCommandButtons()) {
      b.addActionListener((evt) -> this.execute(features, evt.getActionCommand()));
    }
    this.listOfLayers.addListSelectionListener(e -> System.out.println(e.getSource()));
    features.updateModel(this.model);
  }

  /**
   * Quits the current running program.
   */
  public void quit() {
    this.dispose();
  }

  /**
   * A helper method that helps set the action listener of the buttons.
   *
   * @param features - the gui controller
   * @param command - the command to be executed
   */
  private void execute(CollageFeatures features, String command){
    switch(command) {
      case "Save Image":
      case "Save Project": {
        String filePath = this.showFileChooser(features);
        if(Objects.isNull(filePath)) {
          features.warn("Saving cancelled.", "Save Cancel");
        }
        features.saveFile(filePath);
      }

      break;
      case "Load Image":{
        String filePath;
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PPM Images", "ppm");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          filePath = f.getAbsolutePath();

          String layer = JOptionPane.showInputDialog(this, "Enter the name of the layer:",
                  "Load Image", JOptionPane.INFORMATION_MESSAGE);
          String col = JOptionPane.showInputDialog(this, "Enter the x coordinate of the image:",
                  "Load Image", JOptionPane.INFORMATION_MESSAGE);
          String row = JOptionPane.showInputDialog(this, "Enter the x coordinate of the image:",
                  "Load Image", JOptionPane.INFORMATION_MESSAGE);

          features.addImageToLayer(layer, filePath, Integer.parseInt(row), Integer.parseInt(col));
        }
      }
      break;
      case "Load Project":{
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Collage Project", "collage");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          features.loadProject(f.getAbsolutePath());
        }
      }
      break;
      case "New Project": {
        String width = JOptionPane.showInputDialog(this, "Enter the width of the new project:",
                "New Project", JOptionPane.INFORMATION_MESSAGE);
        String height = JOptionPane.showInputDialog(this, "Enter the height of the new project:",
                "New Project", JOptionPane.INFORMATION_MESSAGE);

        features.newProject(Integer.parseInt(height), Integer.parseInt(width));
      }
      break;
      case "Add Layer": {
        String layer = JOptionPane.showInputDialog(this, "Enter a name for the new layer:",
                "Add Layer", JOptionPane.INFORMATION_MESSAGE);
        features.addLayer(layer);
        this.dataLayerList.addElement(layer);
      }
      break;
      case "Set Filter":{
        String layer = JOptionPane.showInputDialog(this, "Enter the name of the layer:",
                "Set Filter", JOptionPane.INFORMATION_MESSAGE);
        String filter = JOptionPane.showInputDialog(this, "Enter the filter name:",
                "Set Filter", JOptionPane.INFORMATION_MESSAGE);
        features.setFilter(layer, filter);
      }
      break;
      case "Quit":
        features.exitProgram();
        break;
      default:
        features.warn("Unknown Command!", "Warning!");
    }
  }

  /**
   * A helper function that displays the file chooser dialog.
   *
   * @param features - the gui controller
   */
  private String showFileChooser(CollageFeatures features) {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }
}