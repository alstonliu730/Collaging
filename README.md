# Collaging

## Overview
- Controller: `CollageController`
- Model: `CollageModel`
- View: `TextView`

### Controller
The controller CollageController has three behaviors for its interface. 
The implementation has a Readable in, TextView, CollageModel as its field and its constructor takes in these parameters. 
The purpose of this controller is to take in the user input and parse information from this controller to both the view and the model.

#### `runCollage()`
This function runs the overall controller and loops until the user quits. 
It takes in a line from the command text and executes the command. The scanner would separate the arguments
and parse it into the runCommand() with the arguments.

#### `runCommand(String command, String... args)`
This function is given a command and the arguments and executes the command. The options for the commands:
1. `quit`: Quits the controller.
2. `new-project`: Creates a new project with the given height and width.
3. `load-project`: Load the given project file name
4. `save-project`: Save the project with the given file path.
5. `add-layer`: Adds a layer with the given name to the project.
6. `add-image-to-layer`: Adds the given image file path to the given layer name with the given position.
7. `save-image`: Saves the image as a .ppm file with the given name.
8. `set-filter`: Set the filter of the given layer name with the given filter option.

#### `printMessage(String message)`
This function appends the given message to the view of the controller. 
If the view throws an "IOException", then the function will simply print in the System.out console "Failure in transmitting custom message!".

### Model
The model `CollagePPM` implements an interface called `CollageModel` and takes in the behaviors.
The model has four fields and its constructor takes in three values: `height`, `width`, and `maxValue`
The last field is a `List<Layer>` and this will represent the layers in a image processor.

The following behaviors are the methods for the `CollageModel`
#### `addImageToLayer(Layer layer, IListOfPixel img, int row, int col)`
The addImageToLayer takes in a layer, an IListOfPixel object (which can be a Layer or an Image), a row, and a column. 
The return type is void since it just modifies the given layer. It takes the matrix of the IListOfPixel object and adds it to the given layer with the coordinates.

#### `addLayer(String layer)`
This function just creates a new layer with the given Laye name. 
The function may throw an IllegalArgumentException when the layer name already exists in the model.

#### `saveImage()`
This function first combines all the Layers together and combines into one Layer. 
It returns the combined layer and this function will be fed into a FileWriter to be written as a PPM file.

#### `renderLayers()`
This function also combines all the layers together and adds it to a list of Layers. The layer's order would go from bottom-most layer to the top-most layer. 
This means the first layer in the list would be the bottom-most layer in the model.

#### `ppmFormat()`
This function returns a string format that resembles a PPM file. However, it also includes all the layer's information including name and filter name and the content.
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
The getters and setters are only for the fields. These are important for parsing information into other classes. This can help with the height, width, and max value of a layer so that there wouldn't be any errors on out of bounds errors.
