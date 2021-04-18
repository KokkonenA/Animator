import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Animation {
    val figures = ArrayBuffer [Figure] ()
    private var background = ""

    private var frameCount = 30
    private var currIdx = 0

    def getCurrIdx = this.currIdx

    def getFrameCount = this.frameCount

    def addFrame(): Unit = {
        this.frameCount += 1
        this.figures.foreach(_.addFrame())
        Timeline.addFrame()
    }

    def deleteFrame() = {
        if (this.frameCount > 2) {
            this.frameCount -= 1
            if (this.currIdx == this.frameCount) this.currIdx -= 1
            this.figures.foreach(_.deleteFrame())
            Timeline.deleteFrame()
        }
    }

    def nextFrame(): Unit = {
        if (this.currIdx < this.frameCount - 1) {
            this.currIdx += 1
            this.figures.foreach(_.updateFrame())
            Timeline.updateCursor()
        }
    }

    def previousFrame(): Unit = {
        if (this.currIdx > 0) {
            this.currIdx -= 1
            this.figures.foreach(_.updateFrame())
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
            this.figures += new Figure(lines.slice(startIdx, endIdx + 1))
        } else {
            new Alert(AlertType.Error) {
                initOwner(Animator.stage)
                title = "Error"
                headerText = "Couldn't find a structure \"" + structure + "\""
            }.showAndWait()
        }
    }

    def deleteFigure(figure: Figure): Unit = {
        this.figures -= figure
        figure.remove()
    }

    def setBackground(newBG: String): Unit = {
        Viewer.setBackground(newBG)
        this.background = newBG
    }

    def play(): Unit = {

    }
    this.setBackground("file:basic.png")
}
