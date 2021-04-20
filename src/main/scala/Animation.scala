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

    def firstFrame = frames.head

    def lastFrame = frames.last

    def keyFrames = frames.filter(_.isKeyFrame)

    def keyFramesWithIndex = frames.zipWithIndex.filter(_._1.isKeyFrame)

    def frameCount = frames.length

    def addFrameToEnd(): Unit = {
        frames += new Frame(Some(frames.last))
        figures.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame() = {
        if (frameCount > 2) {
            if (currFrame == frames.last) currFrame = frames.last.previous.get
            lastFrame.remove()
            frames -= lastFrame
            figures.foreach(_.deleteLastFrame())
        }
    }

    def nextFrame(): Unit = {
        calculateFrameData()

        if (currFrame != frames.last) {
            currFrame = frames.find(_.previous == Some(currFrame)).get
            figures.foreach(_.loadFrameData())
        }
    }

    def previousFrame(): Unit = {
        calculateFrameData()

        if (currFrame != frames.head) {
            currFrame = currFrame.previous.get
            figures.foreach(_.loadFrameData())
        }
    }

    def saveFrameData(): Unit = {
        figures.foreach(_.saveFrameData())
    }

    def framesBetween(start: Frame, end: Frame): Int = {
        frames.indexOf(end) - frames.indexOf(start)
    }

    def calculateFrameData(): Unit = {
        val frame = currFrame

        val hasKey = frame.isKeyFrame

        val idx = frames.indexOf(frame)

        val findPrevious = keyFramesWithIndex.findLast(_._2 < idx)
        val previous = if(findPrevious.isDefined) findPrevious.get._1 else firstFrame

        val findNext = keyFramesWithIndex.find(_._2 > idx)
        val next = if(findNext.isDefined) findNext.get._1 else lastFrame

        val previousHasKey = previous.isKeyFrame
        val nextHasKey = next.isKeyFrame

        if (hasKey) {
            saveFrameData()
            if (previousHasKey) figures.foreach(_.interpolate(previous, frame, framesBetween(previous,frame)))

            if (nextHasKey) figures.foreach(_.interpolate(frame, next, framesBetween(frame, next)))
            else figures.foreach(_.setDataEqual(frame, next))
        } else {
            if (previousHasKey) figures.foreach(_.interpolate(previous, next, framesBetween(previous, next)))
            else {
                if (nextHasKey) figures.foreach(_.setDataEqual(previous, next))
                else figures.foreach(_.setDataEqual(previous, next.previous.get))
            }
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

    def close(): Unit = {
        this.figures.foreach(_.remove())
        this.frames.foreach(_.remove())
    }

    setBackground("file:basic.png")
}
