import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import controller.CollageController;
import controller.CollageControllerImpl;
import controller.CollageGUI;
import model.CollageModel;
import model.CollagePPM;
import view.PPMTextViewImpl;
import view.TextView;
import view.gui.CollageFrameView;
import view.gui.GUIView;

/**
 * Main function to run collage app.
 */
public class CollageApp {
  /**
   * Main method to execute the collaging application and launch the GUI.
   * @param args command line arguments as an array of Strings
   */
  public static void main(String[] args) {
    CollageModel model = new CollagePPM();
    if (args.length > 0) {
      if (args[0].equals("-text")) {
        TextView view = new PPMTextViewImpl(System.out, model);
        CollageController control = new CollageControllerImpl(model,
                new InputStreamReader(System.in), view);
        control.runCollage();
      } else if (args[0].equals("-file")) {
        TextView view = new PPMTextViewImpl(System.out, model);
        Readable input = null;
        try {
          input = ImageUtil.removeComments(args[1]);
          CollageController control = new CollageControllerImpl(model,
                  input, view);
          control.runCollage();
        } catch (FileNotFoundException e) {
          System.out.println(e.getMessage());
          e.printStackTrace();
          System.exit(-1);
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          e.printStackTrace();
          System.exit(-1);
        }
      } else {
        System.out.println("Invalid command line arguments!");
        System.exit(-1);
      }
    } else {
      GUIView view = new CollageFrameView(model);
      CollageController control = new CollageGUI(model, view);
      control.runCollage();
    }
  }
}
