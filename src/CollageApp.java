import controller.CollageController;
import controller.CollageGUI;
import model.CollageModel;
import model.CollagePPM;
import view.gui.CollageFrameView;
import view.gui.GUIView;

public class CollageApp {
  public static void main(String[] args) {
    CollageModel model = new CollagePPM();
    GUIView view = new CollageFrameView(model);
    CollageController control = new CollageGUI(model, view);
    control.runCollage();
  }
}
