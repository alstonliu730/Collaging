package util;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import model.CollageModel;
import model.CollagePPM;
import model.Filter;
import model.IListOfPixel;
import model.IPixel;
import model.Image;
import model.Layer;
import model.Pixel;
import model.Posn;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 */
public class ImageUtil {
  /**
   * Removes the comment lines from a file. Comments start with a '#'.
   * Returns the file without the comments.
   *
   * @param filename path of the file
   * @return a Readable object with the contents of the file without comments
   * @throws IllegalArgumentException - if a null arg is passed
   * @throws FileNotFoundException    - if the file is not found
   */
  public static Readable removeComments(String filename)
          throws IllegalArgumentException, FileNotFoundException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null argument.");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty() && !s.isBlank() && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //Set up a stream of input to export the readable
    InputStream stream = new ByteArrayInputStream(
            builder.toString().getBytes(StandardCharsets.UTF_8));
    return new InputStreamReader(stream);
  }

  /**
   * Read an image file in the PPM format and return the image.
   *
   * @param filename the path of the file.
   * @return - the image
   * @throws FileNotFoundException if the given file is not found
   */
  public static IListOfPixel readPPM(String filename) throws FileNotFoundException {
    Scanner sc;

    //now set up the scanner to read from the string we just built
    sc = new Scanner(ImageUtil.removeComments(filename));

    String token = sc.next();
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
        pixelList[i][j] = new Pixel(r, g, b, maxValue, new Posn(i, j));
      }
    }

    // Create this new layer
    IListOfPixel newImage = new Image(pixelList, new Posn(0, 0));

    // return the new model
    return newImage;
  }

  /**
   * Reads an image file that are supported by Java and returns the image.
   *
   * @param filename - the file location of the image
   * @return - the image
   * @throws IOException - when there's
   */
  public static IListOfPixel readImages(String filename) throws IOException {
    // get the file extension
    String extension = "";
    int index = filename.lastIndexOf(".");
    if(index > 0) {
      extension = filename.substring(index+1);
    }

    // check extension
    if(extension.equals("ppm")){
      return readPPM(filename);
    }

    // file is not a ppm
    BufferedImage input = ImageIO.read(new File(filename));
    int width = input.getWidth();
    int height = input.getHeight();

    // Create the 2d aray of pixel
    Pixel[][] pixelList = new Pixel[height][width];
    for(int i = input.getMinX(); i < width; i++) {
      for(int j = input.getMinY(); j < height; j++){
        Color c;
        if(extension.equals("png")) {
          c = new Color(input.getRGB(i,j), true);
        } else {
          c = new Color(input.getRGB(i,j));
        }
        pixelList[j][i] = new Pixel(c.getRed(), c.getGreen(),
                c.getBlue(), c.getAlpha(), new Posn(j,i));
      }
    }

    // create IListOfPixel Object
    IListOfPixel img = new Image(pixelList, new Posn(0,0));
    return img;
  }

  /**
   * Reads a Collage project file to be loaded in.
   *
   * @param filename - the location of the file
   * @return - the model from the given project file
   * @throws FileNotFoundException - could not find the file given the file path
   */
  public static CollageModel readProject(String filename) throws FileNotFoundException {
    Scanner sc;

    //now set up the scanner to read from the string we just built
    sc = new Scanner(ImageUtil.removeComments(filename));

    String token = sc.next();
    if (!token.equals("C1")) {
      System.out.println("Invalid Collage File: plain txt file should start with C1");
    }
    // Gather Collage information
    CollageModel model = new CollagePPM();
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    model.startModel(height, width, maxValue);

    // need to get rid of the default background layer for this collage's background
    model.removeLayer("background");

    // Gather layer information
    while (sc.hasNext()) {
      // Gather layer names and filter name
      Layer new_layer;
      String layer_name = sc.next();
      String filter_name = sc.next();

      // Create a new layer
      new_layer = new Layer(layer_name, height, width, maxValue);
      new_layer.setFilter(Filter.findByValue(filter_name));
      List<IPixel> pixelList = new ArrayList<IPixel>();

      // extract pixels to the list
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          int a = sc.nextInt();
          pixelList.add(new Pixel(r, g, b, a, new Posn(i, j)));
        }
      }
      // set the matrix of the layer
      new_layer.setMatrix(PixelArrayUtil.convertToMatrix(pixelList,
              new_layer.getHeight(), new_layer.getWidth()));
      model.addGivenLayer(new_layer);
    }

    return model;
  }

  /**
   * Writes a PPM image file given an IListOfPixel object and the file path.
   *
   * @param img      - the IListOfPixel object
   * @param filename - the saved location of the file
   */
  private static void writePPM(IListOfPixel img, String filename) {
    try (FileWriter fw = new FileWriter(filename)) {
      fw.write("P3\n");
      fw.write(img.getWidth() + " " + img.getHeight() + "\n");
      fw.write(img.getMax() + "\n");

      for (int i = 0; i < img.getHeight(); i++) {
        for (int j = 0; j < img.getWidth(); j++) {
          fw.write(img.getPixel(new Posn(i, j)).toString() + " ");
        }
        fw.write("\n");
      }

      fw.close();
    } catch (IOException e) {
      System.out.println("PPM file could not be written.");
    }
  }

  /**
   * Writes an image file given an IListOfPixel object and the file path.
   *
   * @param img - the compressed image object
   * @param filename - the saved location of the file
   * @return - returns if the image is saved properly
   * @throws IOException - Error in writing to an image file
   * @throws IllegalArgumentException - When the input is null or
   *                                    when the file extension is not supported
   */
  public static boolean writeImage(IListOfPixel img, String filename)
          throws IOException, IllegalArgumentException {
    if (Objects.isNull(img) || Objects.isNull(filename)) {
      throw new IllegalArgumentException("Given input is null! Try again!");
    }
    // check if extension is ppm
    if(filename.contains(".ppm")) {
      writePPM(img, filename);
      return true;
    }
    RenderedImage image = createImageObject(img);
    if (filename.contains(".jpg") || filename.contains(".jpeg")) {
      try {
        return ImageIO.write(image, "jpg", new File(filename));
      } catch (IOException e) {
        throw new IOException("Error in writing jpg image file.");
      }
    } else if (filename.contains(".png")) {
      try {
        return ImageIO.write(image, "png", new File(filename));
      } catch(IOException e) {
        throw new IOException("Error in writing png image file.");
      }
    } else {
      throw new IllegalArgumentException("File extension is not supported.");
    }
  }

  /**
   * Creates a rendered image object from the given IListOfPixel object.
   *
   * @param img - the IListOfPixel object
   * @return - the rendered image
   */
  public static BufferedImage createImageObject(IListOfPixel img) {
    BufferedImage image = new BufferedImage(img.getWidth(),
            img.getHeight(), BufferedImage.TYPE_INT_ARGB);

    // Iterating through the image array
    for (int i = 0; i < image.getHeight(); i++) {
      for(int j = 0; j < image.getWidth(); j++) {
        IPixel p = img.getPixel(new Posn(i,j));
        int[] rgba = p.getValues();
        Color c = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
        image.setRGB(j, i, c.getRGB());
      }
    }

    return image;
  }

  /**
   * Writes a project file given a model and the file path.
   *
   * @param model    - the given model
   * @param filename - the saved location of the file
   */
  public static void writeProject(CollageModel model, String filename) {
    try (FileWriter fw = new FileWriter(filename)) {
      // Write collage information
      fw.write("C1\n");
      fw.write(model.toString());

      // close the stream
      fw.close();
    } catch (IOException e) {
      System.out.println("Project file could not be written.");
    }
  }
}



