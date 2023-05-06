import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.FileDialog
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class UserInterface {
    var inputPath by mutableStateOf("")
    var outputPath by mutableStateOf("")

    var tvEffectSize by mutableStateOf("2")

    var autoOutputPath by mutableStateOf(true)
    val notIntegerRegex = Regex("[^\\d]")

    var working by mutableStateOf(false)

    var image: BufferedImage? by mutableStateOf(null)

    var imageTreated by mutableStateOf(false)

    var progressImageRender by mutableStateOf(0f)

    @Composable
    @Preview
    fun App() {
        MaterialTheme {
            Column(
                Modifier
                    .fillMaxWidth()
                    .width(800.dp)
                    .height(800.dp)
                    .background(Color(64, 64, 64))
            ) {
                TitleSection()
                Row(
                    Modifier
                        .fillMaxHeight()
                        .background(Color(64, 64, 96))
                ) {
                    ImagePreviewer()
                    ControlPanel()
                }
            }
        }
    }

    @Composable
    fun TitleSection() {
        Text(
            text = "TITLE" /*TODO*/,
            Modifier
                .height(50.dp)
        )
    }

    @Composable
    fun ImagePreviewer() {
        Box(
            Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(Color(0, 0, 0))
        ) {
            if (image != null) {
                Image(
                    image!!.toComposeImageBitmap(), "",
                    Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }

    @Composable
    fun ControlPanel() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            InputFileSelection()

            ImageGeneration()

            OutputPathSelection()
        }
    }

    @Composable
    fun InputFileSelection() {
        Column {
            TextField(
                label = {Text("Input file path")},
                value = inputPath,
                onValueChange = {},
                enabled = false
            )
            Button(
                onClick = {
                    selectFile()
                },
                enabled = !working
            ) {
                Text("Choose File")
            }
        }
    }

    @Composable
    fun ImageGeneration() {
        Column {
            TextField(
                label = { Text("TV effect size") },
                value = tvEffectSize,
                onValueChange = { updateTvEffectSize(it) },
                enabled = !working
            )
            LinearProgressIndicator(
                progress = progressImageRender,
                Modifier
                    .fillMaxWidth()
            )
            Button(
                onClick = { createImage() },
                enabled = !working && !filePicked()
            ) {
                Text("Apply Color Filter")
            }
        }
    }

    @Composable
    fun OutputPathSelection() {
        Column {
            TextField(
                label = { Text("Output file path") },
                value = outputPath,
                onValueChange = { outputPath = it },
                enabled = !autoOutputPath
            )

            Row {
                Text(
                    "Auto output path :",
                    Modifier
                        .align(Alignment.CenterVertically)
                )
                Checkbox(
                    checked = autoOutputPath,
                    onCheckedChange = { autoOutputPath = !autoOutputPath }
                )
            }

            Button(
                onClick = { saveImage() },
                enabled = !working && imageTreated
            ) {
                Text("Save")
            }
        }
    }

    private fun setImageToInput() {
        image = ImageIO.read(File(inputPath))
        imageTreated = false
    }

    private fun updateTvEffectSize(newValue: String) {
        tvEffectSize = newValue
        tvEffectSize = tvEffectSize.replace(notIntegerRegex, "")
    }

    private fun selectFile() {
        var newPath = ""
        try {
            newPath = openFileDialog(ComposeWindow())[0].path
        }catch (_:Exception) {}

        if (newPath != "") {
            inputPath = newPath
            updateAutoOutputPath()
            setImageToInput()
        }
    }

    private fun openFileDialog(window: ComposeWindow): List<File> {
        // https://www.reddit.com/r/Kotlin/comments/n16u8z/desktop_compose_file_picker/
        val title = "Choose an image to work on"
        val allowedExtensions = listOf(".png")
        val allowMultiSelection = false
        return FileDialog(window, title, FileDialog.LOAD).apply {
            isMultipleMode = allowMultiSelection

            // windows
            file = allowedExtensions.joinToString(";") { "*$it" } // e.g. '*.jpg'

            // linux
            setFilenameFilter { _, name ->
                allowedExtensions.any {
                    name.endsWith(it)
                }
            }

            isVisible = true
        }.files.toList()
    }

    private fun filePicked(): Boolean {
        return inputPath == ""
    }

    // MODEL CALLING

    fun updateAutoOutputPath() {
        outputPath = inputPath.substring(0, inputPath.length - 4) + "_OneShotColorFilter.png"
    }

    fun createImage() {
        if (inputPath != "") {
            working = true
            progressImageRender = 0f
            Thread {
                image = getFilteredImage(inputPath, rGBCube, tvEffectSize.toInt(), this)
                working = false
                imageTreated = true
                progressImageRender = 1f
            }.start()
        }
    }

    fun setProgress(newValue: Float) {
        progressImageRender = newValue
    }

    fun saveImage() {
        saveImage(image!!, outputPath)
    }

    fun main() = application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}

fun main() = UserInterface().main()