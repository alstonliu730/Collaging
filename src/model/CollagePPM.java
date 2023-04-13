package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import util.PixelArrayUtil;

/**
 * A class that represents a CollageModel with a PPM format.
 */
public class CollagePPM implements CollageModel {
  private int height;
  private int width;
  private int maxValue;
  private List<ILayer> layers;

  /**
   * Creates a CollagePPM model and instantiates necessary items.
   */
  public CollagePPM() {
    this.layers = new ArrayList<ILayer>();
    this.maxValue = 255;
  }

  /**
   * Load model with given height, width, and max value.
   *
   * @param height   - the height of the model (rows)
   * @param width    - the width of the model (columns)
   * @param maxValue - the max value of the channel (max value of rgba values)
   * @throws IllegalArgumentException - when the given inputs are zero or negative.
   */
  @Override
  public void startModel(int height, int width, int maxValue) throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || maxValue <= 0) {
      throw new IllegalArgumentException("Invalid PPM dimension!");
    }
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;

    //create a white background layer.
    IPixel[][] background = new Pixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        background[i][j] = new Pixel(this.maxValue, this.maxValue,
                this.maxValue, this.maxValue, new Posn(i, j));
      }
    }

    // create the background layer object with a white background.
    Layer backgroundLayer = new Layer("background", this.height, this.width, this.maxValue);
    backgroundLayer.setMatrix(background);
    backgroundLayer.setFilter(Filter.NORMAL);

    // add the layer object to the list.
    this.layers.add(backgroundLayer);
  }

  /**
   * Adds given layer to the list of layers.
   *
   * @param layer - layer to be added
   */
  public void addGivenLayer(ILayer layer) {
    this.layers.add(layer);
  }

  /**
   * Removes the layer with the given name associated.
   * If it doesn't exist, it does nothing.
   *
   * @param name - the name of the layer
   */
  public void removeLayer(String name) {
    for (Iterator<ILayer> iter = this.layers.iterator(); iter.hasNext(); ) {
      ILayer l = iter.next();
      if (l.getName().equals(name)) {
        iter.remove();
      }
    }
  }

  /**
   * Returns the max value of this model.
   *
   * @return - the max value of the model.
   */
  public int getMax() {
    return this.maxValue;
  }

  /**
   * Returns the width of this model.
   *
   * @return - the width of the model
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height of this model.
   *
   * @return - the height of the model
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns the layer from the given name.
   *
   * @param name - the name of the layer
   * @return - the Layer object
   */
  public ILayer getLayer(String name) throws IllegalStateException {
    for (ILayer l : this.layers) {
      if (l.getName().equalsIgnoreCase(name)) {
        return l;
      }
    }

    throw new IllegalStateException("Invalid Layer name! Name: " + name);
  }

  /**
   * Adds a layer to this model given the name of the layer.
   *
   * @param layer - the name of the layer
   * @throws IllegalArgumentException - when the given layer name already exists in this model
   */
  public void addLayer(String layer) throws IllegalArgumentException {
    ILayer newLayer = new Layer(layer, this.height, this.width, this.maxValue);
    for (ILayer l : this.layers) {
      if (l.getName().equalsIgnoreCase(layer)) {
        throw new IllegalArgumentException("Layer name already exists!");
      }
    }
    this.layers.add(newLayer);
  }

  /**
   * Adds an image to the given layer given the image and the position.
   *
   * @param layer - the given layer
   * @param img   - the image to be added
   * @param row   - the x-coordinate of the image
   * @param col   - the y-coordinate of the image
   */
  public void addImageToLayer(ILayer layer, IListOfPixel img, int row, int col)
          throws IllegalArgumentException {
    layer.addImage(new Image(PixelArrayUtil.convertToMatrix(img.render(),
            img.getHeight(), img.getWidth()), new Posn(row, col)));
  }

  /**
   * Sets the Filter of the given layer.
   *
   * @param layer  - the name of the given layer
   * @param option - the filter type
   * @throws IllegalStateException - when the given layer is not found
   */
  public void setFilter(String layer, IFilter option) throws IllegalStateException {
    for (ILayer l: this.layers) {
      if (l.getName().equalsIgnoreCase(layer)) {
        l.setFilter(option);
        return;
      }
    }

    throw new IllegalStateException("Invalid Layer name! Name: " + layer);
  }

  /**
   * Save the layers as one layer.
   *
   * @return - the combined layers
   */
  public ILayer saveImage() {
    // set the background as the first since it's the lowest
    List<ILayer> filteredLayers = this.renderLayers();
    ILayer combined = filteredLayers.get(0);

    // go through the list of layers
    for (int i = 1; i < filteredLayers.size(); i++) {
      combined = filteredLayers.get(i).combine(combined);
    }

    // returns the rendered combined layer
    return combined;
  }

  /**
   * Return the current list of Layers with the applied filter.
   *
   * @return - the list of Layers
   */
  public List<ILayer> renderLayers() {
    // create a copy of the current layers
    List<ILayer> renderedLayers = new ArrayList<ILayer>();
    ILayer prev = this.layers.get(0); // get the background as the first layer
    renderedLayers.add(prev.applyFilter(null)); // add the background

    // apply the filter to each layer
    if (this.layers.size() > 1) {
      for (int i = 1; i < this.layers.size(); i++) {
        ILayer curr = this.layers.get(i);
        renderedLayers.add(curr.applyFilter(prev));
        prev = renderedLayers.get(i);
      }
    }
    return renderedLayers;
  }

  /**
   * Return the model in a certain format.
   *
   * @return - the format of the PPM model as a String
   */
  public String toString() {
    String project = "";

    // Adds project information
    project += (this.width + " " + this.height + "\n");
    project += (this.maxValue + "\n");

    // Adds layer information
    for (ILayer l : this.renderLayers()) {
      project += (l.getName() + " " + l.getFilter().getOption() + "\n");
      for (IPixel p : l.render()) {
        project += (p.rgbaString() + " ");
      }
      project += "\n";
    }

    // return the string of the project
    return project;
  }

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
  public IListOfPixel readPPM(String filename) throws FileNotFoundException {
    Scanner sc;

    //now set up the scanner to read from the string we just built
    sc = new Scanner(this.removeComments(filename));

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
  public IListOfPixel readImages(String filename) throws IOException {
    // get the file extension
    String extension = "";
    int index = filename.lastIndexOf(".");
    if(index > 0) {
      extension = filename.substring(index+1);
    }

    // check extension
    if(extension.equals("ppm")){
      return this.readPPM(filename);
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
  public CollageModel readProject(String filename) throws FileNotFoundException {
    Scanner sc;

    //now set up the scanner to read from the string we just built
    sc = new Scanner(this.removeComments(filename));

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
      ILayer new_layer;
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
  public boolean writeImage(IListOfPixel img, String filename)
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
  public BufferedImage createImageObject(IListOfPixel img) {
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
  public void writeProject(CollageModel model, String filename) {
    try (FileWriter fw = new FileWriter(filename)) {
      // Write collage information
      fw.write("C1\n");
      fw.write(model.getWidth() + " " + model.getHeight() + "\n");
      fw.write(model.getMax() + "\n");

      // write layers and layer information
      for (ILayer l : model.renderLayers()) {
        fw.write(l.getName() + " " + l.getFilter().getOption() + "\n");
        for (int i = 0; i < l.getHeight(); i++) {
          for (int j = 0; j < l.getWidth(); j++) {
            fw.write(l.getPixel(new Posn(i, j)).rgbaString() + " ");
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
