import Animator.stage
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Animation {
    val figures = ArrayBuffer [Figure] ()
    private var background = ""

    private var frames = 1
    private var currFrame = 0
    val keyFrames = ArrayBuffer [Int] ()

    def addFrame(): Unit = {
        this.frames += 1
    }

    def deleteFrame() = {
        this.frames -= 1
    }

    def addFigure(structure: String): Unit = {
        val lines = ArrayBuffer [String] ()
        val bufferedSource = Source.fromFile("Structures")

        for (line <- bufferedSource.getLines()) {
            lines += line.trim
        }
        bufferedSource.close()

        val startIdx = lines.indexOf(structure)
        val endIdx = lines.indexOf("/" + structure)

        if (startIdx != -1 || endIdx != -1) {
            this.figures += new Figure(lines.slice(startIdx, endIdx + 1))
        } else {
            new Alert(AlertType.Error) {
                initOwner(stage)
                title = "Error"
                headerText = "Couldn't find a structure \"" + structure + "\""
            }.showAndWait()
        }
    }

    def deleteFigure(): Unit = {

    }

    def setBackground(newBG: String): Unit = {
        Viewer.setBackground(newBG)
        this.background = newBG
    }

    this.setBackground("file:basic.png")
}
