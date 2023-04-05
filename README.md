# Collaging

## Overview

- Controller: `CollageController`
- Model: `CollageModel`
- View: `TextView`

### Controller

The controller CollageController has three behaviors for its interface.
The implementation has a Readable in, TextView, CollageModel as its field and its constructor takes
in these parameters.
The purpose of this controller is to take in the user input and parse information from this
controller to both the view and the model.

#### `runCollage()`

This function runs the overall controller and loops until the user quits.
It takes in a line from the command text and executes the command. The scanner would separate the
arguments
and parse it into the runCommand() with the arguments.

#### `runCommand(String command, String... args)`

This function is given a command and the arguments and executes the command. The options for the
commands:

1. `quit`: Quits the controller.
2. `new-project`: Creates a new project with the given height and width.
3. `load-project`: Load the given project file name
4. `save-project`: Save the project with the given file path.
5. `add-layer`: Adds a layer with the given name to the project.
6. `add-image-to-layer`: Adds the given image file path to the given layer name with the given
   position.
7. `save-image`: Saves the image as a .ppm file with the given name.
8. `set-filter`: Set the filter of the given layer name with the given filter option.

#### `printMessage(String message)`

This function appends the given message to the view of the controller.
If the view throws an "IOException", then the function will simply print in the System.out console "
Failure in transmitting custom message!".

### Model

The model `CollagePPM` implements an interface called `CollageModel` and takes in the behaviors.
The model has four fields and its constructor takes in three values: `height`, `width`,
and `maxValue`
The last field is a `List<Layer>` and this will represent the layers in a image processor.

The following behaviors are the methods for the `CollageModel`

#### `addImageToLayer(Layer layer, IListOfPixel img, int row, int col)`

The addImageToLayer takes in a layer, an IListOfPixel object (which can be a Layer or an Image), a
row, and a column.
The return type is void since it just modifies the given layer. It takes the matrix of the
IListOfPixel object and adds it to the given layer with the coordinates.

#### `addLayer(String layer)`

This function just creates a new layer with the given Laye name.
The function may throw an IllegalArgumentException when the layer name already exists in the model.

#### `saveImage()`

This function first combines all the Layers together and combines into one Layer.
It returns the combined layer and this function will be fed into a FileWriter to be written as a PPM
file.

#### `renderLayers()`

This function also combines all the layers together and adds it to a list of Layers. The layer's
order would go from bottom-most layer to the top-most layer.
This means the first layer in the list would be the bottom-most layer in the model.

#### `ppmFormat()`

This function returns a string format that resembles a PPM file. However, it also includes all the
layer's information including name and filter name and the content.
The format should look like this:

    C1
    width height
    max-value
    layer-name filter-name
    LAYER-CONTENT-FORMAT
    ...
    layer-name filter-name
    LAYER-CONTENT-FORMAT
    ...

#### Getters

The getters and setters are only for the fields. These are important for parsing information into
other classes. This can help with the height, width, and max value of a layer so that there wouldn't
be any errors on out-of-bounds errors.

## Design Changes and New Additions

PLEASE REFER TO THE NEW CLASS DIAGRAM PROVIDED.

### Controller Package

- `CollageControllerImpl` class: added a new method `printCommands()` that prints the command line
options/arguments a user can use when running the controller.

- `CollageFeatures` Interface: a new interface with a command callback design pattern
that establishes communication between the Controller and the View.

- `CollageGUI` class: a new class that implements the `CollageController` and `CollageFeatures` interfaces
that implements a controller for the GUI.

### Model Package

- `CollageModel` Interface: added a `startModel(int height, int width, int maxValue)` method
that launches a new project with the given parameters, a `removeLayer(String name)` method
that removes the given Layer (its name) from the working project; overloaded the `toString()` 
method to have a string representation of the project.

- `CollagePPM` class: implemented the new methods from the interface above, fixed a
`NullPointerException` bug in the `addLayer(String layer)` method, fixed object reference copy
issue in the `setFilter((String layer, Filter option)` method (getter returned a copy of layer and 
modification were not being made to the actual layer in the project).

- `Filter` Class : redesigned class to be an enum as it was a better data representation since
we have a limited set of filters to work with.

- `IPixel` interface: an interface that represents the general behavior of pixels, extendable for
future revisions or design additions. It includes all public methods previously declared in the
`Pixel` class in addition to a new `invert(IPixel prev)` method that inverts the colors of a pixel.

- `IListPixel` interface: Removed `convertToMatrix()` method, we thought it fit better in
a utility class.

- `Pixel` class: modified the `brighten(IPixel prev)` and `darken(Ipixel prev)` methods to work
with the HSL pixel representation, implemented the `invert(IPixel prev)` method.

- `Posn` class: added some missing Javadoc.

### View Package

- `GUIVIEW` interface: a new interface that defines the behavior of our GUI for collaging.

- `CommandListPanel` class: a new class that extends `JPanel` and implements the command buttons
for our GUI.

- `DisplayPanel` class: a new class that also extends `JPanel` and implements the displaying 
of the image in the GUI throughout the collaging process.

- `WelcomeDialog` class: a new class that extends `JDialog` and displays the welcome message for
our GUI, allowing users to initially either create a new project or to load an existing one.