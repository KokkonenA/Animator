import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Animation {
    val figures = ArrayBuffer [Figure] ()
    private var background = ""

    private var frameCount = 30
    private var currIdx = 0

    def getCurrIdx = currIdx

    def getFrameCount = frameCount

    def addFrame(): Unit = {
        frameCount += 1
        figures.foreach(_.addFrame())
        Timeline.addFrame()
    }

    def deleteFrame() = {
        if (frameCount > 2) {
            frameCount -= 1
            if (currIdx == frameCount) currIdx -= 1
            figures.foreach(_.deleteFrame())
            Timeline.deleteFrame()
        }
    }

    def nextFrame(): Unit = {
        if (currIdx < frameCount - 1) {
            currIdx += 1
            figures.foreach(_.loadFrameData())
            Timeline.updateCursor()
        }
    }

    def previousFrame(): Unit = {
        if (currIdx > 0) {
            currIdx -= 1
            figures.foreach(_.loadFrameData())
            Timeline.updateCursor()
        }
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
            figures += new Figure(lines.slice(startIdx, endIdx + 1))
        } else {
            new Alert(AlertType.Error) {
                initOwner(Animator.stage)
                title = "Error"
                headerText = "Couldn't find a structure \"" + structure + "\""
            }.showAndWait()
        }
    }

    def deleteFigure(figure: Figure): Unit = {
        figures -= figure
        figure.remove()
    }

    def setBackground(newBG: String): Unit = {
        Viewer.setBackground(newBG)
        background = newBG
    }

    def play(): Unit = {

    }

    def update(): Unit = {
        figures.foreach(_.update())
    }

    setBackground("file:basic.png")
}
