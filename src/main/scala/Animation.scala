import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import java.util.{Timer, TimerTask}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Animation {
    val figures = ArrayBuffer [Figure] ()
    val frames = ArrayBuffer.iterate(new Frame(None), 30)(n => new Frame(Some(n)))

    private var background = ""

    private var currFrame = frames(0)

    def getCurrFrame = currFrame

    def frameCount = frames.length

    def addFrameToEnd(): Unit = {
        frames += new Frame(Some(frames.last))
        figures.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame() = {
        if (frameCount > 2) {
            if (currFrame == frames.last) currFrame = frames.last.previous.get
            frames.last.remove()
            frames -= frames.last
            figures.foreach(_.deleteLastFrame())
        }
    }

    def nextFrame(): Unit = {
        if (currFrame != frames.last) {
            currFrame = frames.find(_.previous == Some(currFrame)).get
            figures.foreach(_.loadFrameData())
        }
    }

    def previousFrame(): Unit = {
        if (currFrame != frames.head) {
            currFrame = currFrame.previous.get
            figures.foreach(_.loadFrameData())
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
        currFrame = frames(0)

        val timer = new Timer
        timer.scheduleAtFixedRate(new TimerTask {
            var count = 0

            override def run(): Unit = {
                nextFrame()
                count += 1
                if (count == frameCount) timer.cancel()
            }
        }, 1000, 42)
    }

    def update(): Unit = {
        figures.foreach(_.update())
        frames.foreach(_.update())
        CurrentFrameCursor.update()
    }

    setBackground("file:basic.png")
}
