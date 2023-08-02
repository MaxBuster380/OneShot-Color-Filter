package model.applicationfunctions

import model.classes.BissectedCube
import java.lang.Exception

/**
 *
 */
class TerminalMain {
    companion object {

        var notDone = true

        var tvThickness = 2

        fun main(args: Array<String>) {
            println("\tWORLD MACHINE COLOR FILTER")

            var command: String?

            while (notDone) {
                print("> ")
                command = readlnOrNull()
                if (command != null) {
                    val arguments = command.split(" ".toRegex()).toTypedArray()

                    if (arguments.isNotEmpty()) {
                        treatCommand(arguments)
                    } else {
                        println("Invalid command")
                    }
                }
            }
        }

        fun treatCommand(args: Array<String>) {
            val commandHead = args[0]

            when (commandHead) {
                "help" -> helpCommand()
                //"description" -> println("TODO")
                "apply" -> {
                    val inputPath = args[1]
                    val outputPath = if (args.size >= 3) {
                        args[2]
                    } else {
                        inputPath.substring(0, inputPath.length - 4) + "_OneShot-Color-Filter.png"
                    }
                    applyCommand(inputPath, outputPath)
                }

                "stt" -> {
                    try {
                        val newThickness = (args[1]).toInt()
                        if (newThickness >= 0) {
                            tvThickness = newThickness
                            println("TV thickness updated to $tvThickness")
                        } else {
                            println("TV thickness must be positive.")
                        }
                    } catch (e: Exception) {
                        println("An error has occurred : $e")
                    }
                }

                "credits" -> creditsCommand()
                "quit" -> quitCommand()
                else -> println("Invalid command")
            }
        }

        fun applyCommand(pathIn: String, pathOut: String) {
            try {
                var rGBCube: BissectedCube = RGBCubeBuilder.getRGBCube("dataColors.txt")
                val recoloredImage = ColorFilterApplier.applyOnImage(pathIn, pathOut, rGBCube)
                TVEffectApplier.apply(recoloredImage, tvThickness)
                recoloredImage.save(pathOut)
                println("Image successfully created")
            } catch (e: Exception) {
                println("An error has occurred : $e")
            }
        }

        fun helpCommand() {
            println("help\n\tShows this list.\n")
            //println("description\n\tShows a description of the application\n")
            println("apply [input image path] [output image path]\n\tApply the World Machine color filter on an image\n\tIf no output path is given, the file will be created in the same directory as the input.\n")
            println("stt [integer] \n\tSets the thickness of the TV effect, in number of pixels.\n\tFor no effect, input 0.\n")
            println("credits\n\tShows the app's credits.\n")
            println("quit\n\tClose the app.")
        }

        fun creditsCommand() {
            println("\tWORLD MACHINE COLOR FILTER")
            println("Creator\t\t\tMaxBuster")
            println("Designer\t\tMaxBuster")
            println("Developer\t\tMaxBuster")
            println("OneShot\t\t\tFutureCat")
            println("Source images\tFutureCat")
            println("\nProject repository : https://github.com/MaxBuster380/WorldMachine-Color-Filter")
        }

        fun quitCommand() {
            notDone = false
        }
    }
}