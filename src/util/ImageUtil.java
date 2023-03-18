package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import model.CollageModel;
import model.CollagePPM;
import model.Filter;
import model.IListOfPixel;
import model.Image;
import model.Layer;
import model.Pixel;
import model.Posn;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method 
 *  as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and return the layer.
   *
   * @param filename the path of the file.
   * @return - the Layer
   * @throws FileNotFoundException
   */
  public static IListOfPixel readPPM(String filename) throws FileNotFoundException {
    Scanner sc;

    try {
        sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
        throw new FileNotFoundException("File "+filename+ " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0)!='#') {
            builder.append(s+System.lineSeparator());
        }
    }
    
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token; 

    token = sc.next();
    if (!token.equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    Pixel[][] pixelList = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();
            pixelList[i][j] = new Pixel(r,g,b,maxValue,new Posn(i,j));
        }
    }

    // Create this new layer
    IListOfPixel newImage = new Image(pixelList, new Posn(0,0));

    // return the new model
    return newImage;
  }

  /**
   * Reads a Collage project file to be loaded in.
   *
   * @param filename - the location of the file
   * @return - the model from the given project file.
   * @throws FileNotFoundException - could not find the file given the file path
   */
  public static CollageModel readProject(String filename) throws FileNotFoundException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token = sc.next();
    if (!token.equals("C1")) {
      System.out.println("Invalid Collage File: plain txt file should start with C1");
    }
    // Gather Collage information
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    CollageModel model = new CollagePPM(height, width, maxValue);

    // Gather layer information
    while (sc.hasNext()) {
      // Gather layer names and filter name
      Layer new_layer;
      String layer_name = sc.next();
      String filter_name = sc.next();

      // Create a new layer
      new_layer = new Layer(layer_name, height, width, maxValue);
      new_layer.setFilter(new Filter(filter_name));
      List<Pixel> pixelList = new ArrayList<Pixel>();

      // extract pixels to the list
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          pixelList.add(new Pixel(r, g, b, maxValue, new Posn(i, j)));
        }
      }
      // set the matrix of the layer
      new_layer.setMatrix(new_layer.convertToMatrix(pixelList));
      model.addGivenLayer(new_layer);
    }

    return model;
  }

  /**
   * Writes a PPM image file given an IListOfPixel object and the file path.
   *
   * @param img - the IListOfPixel object
   * @param filename - the saved location of the file
   */
  public static void writePPM(IListOfPixel img, String filename) {
    try(FileWriter fw = new FileWriter(filename)) {
      fw.write("P3\n");
      fw.write(img.getWidth() + " " + img.getHeight() + "\n");
      fw.write(img.getMax() + "\n");
      for(int i = 0; i < img.getHeight(); i++) {
        for(int j = 0; j < img.getWidth(); j++) {
          fw.write(img.getPixel(new Posn(i,j)).ppmFormat() + " ");
        }
        fw.write("\n");
      }

      fw.close();
    } catch (IOException e) {
      System.out.println("PPM file could not be written.");
    }
  }

  /**
   * Writes a project file given a model and the file path.
   *
   * @param model - the given model
   * @param filename - the saved location of the file
   */
  public static void writeProject(CollageModel model, String filename) {
    try(FileWriter fw = new FileWriter(filename)) {
      // Write collage information
      fw.write("C1\n");
      fw.write(model.getWidth() + " " + model.getHeight() + "\n");
      fw.write(model.getMax() + "\n");

      // write layers and layer information
      for(Layer l : model.renderLayers()) {
        fw.write(l.getName() + " " + l.getFilter().getOption() + "\n");
        for (int i = 0; i < l.getHeight(); i++) {
          for (int j = 0; j < l.getWidth(); j++) {
            fw.write(l.getPixel(new Posn(i,j)).ppmFormat() + " ");
          }
          fw.write("\n");
        }
      }

      // close the stream
      fw.close();
    } catch (IOException e) {
      System.out.println("Project file could not be written.");
    }
  }
}

