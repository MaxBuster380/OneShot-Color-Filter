# WorldMachine-Color-Filter

OneShot is a game created by Future Cat.
In it, there is a character called "The World Machine".
That character's sprites are derived from another character's, with a certain color filter applied to it.
This project aims to make it possible to add that exact filter to any image.

While this project is under MIT license, credit given is appreciated.

To use this project, you may use any IDE that can run Kotlin and can take keyboard inputs. IntelliJ IDEA Community Version works and is free (https://www.jetbrains.com/idea/).

A command invite will show. You have these options :

help
	- Shows this list.

apply [input image path] [output image path]
	- Apply the World Machine color filter on an image
	If no output path is given, the file will be created in the same directory as the input.

stt [integer] 
	- Sets the thickness of the TV effect, in number of pixels.
	For no effect, input 0.

credits
	- Shows the app's credits.

quit
	- Close the app.
	

Example of use :

> stt 2

> apply C:\Users\User\Pictures\Niko.png
	
