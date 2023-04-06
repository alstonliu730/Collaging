# Collaging
Collaging is a image processor that lets the user edit and apply filters onto images.
The executable jar file is called Collaging.jar.

## Overview
The executable file will first show up as a `JDialog` window prompting users to either create
a new project or load an existing projects. Once users, have executed either operations, the main 
view of the GUI will appear with the image loaded on the left-hand side. The image is loaded into
an `ImageIcon` to render into the `DisplayPanel` section of the GUI. The Layer list and 
Command List are both on the right-hand side. The Layer list is a `JList` that list all the 
layers the project contains. The Command List contains a `Grid Layout` that contains all the 
operations the user can do.

### Operations
The operations for the GUI are provided in the right-hand side of the GUI labelled **Command List**.
In the starting screen, there are only two options: `New Project` and `Load Project`.
<ul>
    <li> 
        <code>Save Image</code>
        <p>
            This operation saves the current layers and compresses it into a <code>.ppm</code> image. 
            It requires the file path the user wishes to save to as another argument.
            The prompt will show up with a <code>FileChooser</code> and show a <code>JDialog</code> 
            window with the users directories and files.
        </p>
        <br>
    </li>
    <li>
        <code>Save Project</code>
        <p>
            This operation saves the entire project as a <code>.collage</code> file with the layer 
            information and RGBA values. It requires the file path the user wishes to save to as
            an argument. However, a <code>FileChooser</code> will also pop up as a 
            <code>JDialog</code> window with the users directories and files.
        </p>
        <br>
    </li>
    <li>
        <code>Load Image</code>
        <p>
            This operation loads an image from the user's directory by using a 
            <code>FileChooser</code> to find the user's image. The image must be in a 
            <code>.ppm</code> image format to be compatible. Then it prompts the user which layer
            to add this image to. Then it will also prompt the user for the coordinate of where
            the image should be inserted on to the grid.
        </p>
        <br>
    </li>
    <li>
        <code>Load Project</code>
        <p>
            This operation loads an entire <code>.collage</code> file from the user's directory by 
            using a <code>FileChooser</code> to find the project file. The project file contains 
            the user's saved file with the layer information and individual RGBA values of each 
            <code>IPixel</code> in each <code>Layer</code>. The user will be prompt with the
            <code>FileChooser</code> window to find and load the file.
        </p>
        <br>
    </li>
    <li>
        <code>New Project</code>
        <p>
            This operation creates a new project from scratch. It takes in three arguments: 
            <code>width</code>,<code>height</code>, & <code>maxValue</code>. The user is prompted
            to input these arguments. Then the main frame is visible with the main view of the image
            is a blank canvas with a white background.
        </p>
        <br>
    </li>
    <li>
        <code>Add Layer</code>
        <p>
            This operation creates a new <code>Layer</code> and adds it to the model. In the layer
            list, users can see the updated list with the new <code>Layer</code>. The operation 
            requires one argument: the name of the <code>Layer</code>. The user is prompted to
            input the name of the Layer.
        </p>
        <br>
    </li>
    <li>
        <code>Set Filter</code>
        <p>
            This operation sets the <code>Filter</code> of the given <code>Layer</code>. This 
            operation requires two arguments: The <code>Filter</code> name & the <code>Layer</code>
            name. The user is prompted to input the name of the <code>Layer</code> first then the
            <code>Filter</code> name. The <code>DisplayPanel</code> will be updated with the filter
            applied to it.
        </p>
        <br>
    </li>
    <li>
        <code>Quit</code>
        <p>
            This operation quits the entire program. If the project has not be saved, this 
            operation will not save any changes after the most recent save of the 
            <b>project</b> or <b>image</b>. This will exit with the code 0 for normal exit.
        </p>
        <br>
    </li>
</ul>
