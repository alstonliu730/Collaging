package view.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.CollageModel;

/**
 * A class that represents the panel of command buttons.
 */
public class CommandListPanel extends JPanel {
  private ArrayList<JButton> commandButtons;

  /**
   * Creates a JPanel object with the given model.
   *
   * @param model - the model to base off of
   */
  public CommandListPanel(CollageModel model) {
    super();

    GridLayout layout = new GridLayout(4, 2);
    this.setLayout(layout);

    // Populate the list of Buttons
    this.populateButtons();
    this.setActionForButtons();
    this.setSizeForButtons();

    // add the buttons to the grid of commands
    this.addToGrid();
    this.setVisible(true);
  }

  /**
   * Helper method that populates the list of Buttons.
   */
  private void populateButtons() {
    this.commandButtons = new ArrayList<JButton>();
    this.commandButtons.add(new JButton("Save Image"));
    this.commandButtons.add(new JButton("Save Project"));
    this.commandButtons.add(new JButton("Load Image"));
    this.commandButtons.add(new JButton("Load Project"));
    this.commandButtons.add(new JButton("Add Layer"));
    this.commandButtons.add(new JButton("Set Filter"));
    this.commandButtons.add(new JButton("Quit"));
  }

  /**
   * Helper method that sets the action commands of each button.
   */
  private void setActionForButtons() {
    for (JButton b: this.commandButtons) {
      b.setActionCommand(b.getName());
    }
  }

  /**
   * Helper method that sets the size of each button.
   */
  private void setSizeForButtons() {
    this.commandButtons.forEach((button) -> button.setPreferredSize(
            new Dimension(this.getWidth() / 2, this.getHeight()
                    / (this.commandButtons.size() / 2))));
  }

  /**
   * Helper method that adds the buttons to the command grid.
   */
  private void addToGrid() {
    for (JButton b: this.commandButtons) {
      this.add(b);
    }
  }

  /**
   * Returns the list of the command buttons.
   *
   * @return - array of buttons
   */
  public ArrayList<JButton> getCommandButtons() {
    return this.commandButtons;
  }

}
