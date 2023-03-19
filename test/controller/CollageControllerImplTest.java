package controller;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Scanner;

import model.CollageModel;
import model.CollagePPM;
import util.ImageUtil;
import view.PPMTextViewImpl;
import view.TextView;

import static org.junit.Assert.*;

public class CollageControllerImplTest {
  CollageModel model;
  TextView view;
  Readable in;
  Appendable out;
  CollageController controller;
  public void init(int height, int width) {
    this.out = System.out;
    this.model = new CollagePPM(height,width,255);
    this.view = new PPMTextViewImpl(this.out, this.model);
  }
  @Test
  public void runCollage() {
    this.in = ImageUtil.removeComments("res/redLogo.txt");
    init(400, 400);
    this.controller = new CollageControllerImpl(this.model, this.in, this.view);
    this.controller.runCollage();

    this.in = ImageUtil.removeComments("res/greenLogo.txt");
    init(400, 400);
    this.controller = new CollageControllerImpl(this.model, this.in, this.view);
    this.controller.runCollage();

    this.in = ImageUtil.removeComments("res/blueLogo.txt");
    init(400, 400);
    this.controller = new CollageControllerImpl(this.model, this.in, this.view);
    this.controller.runCollage();
  }

  @Test
  public void runCommands() {

  }

  @Test
  public void printMessage() {
    
  }
}