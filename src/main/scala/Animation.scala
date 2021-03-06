import java.io.PrintWriter
import java.util.{Timer, TimerTask}
import scala.collection.mutable.ArrayBuffer

class Animation(initFrameCount: Int) {
    val frames = ArrayBuffer.iterate(new Frame(None), initFrameCount)(n => new Frame(Some(n)))
    val figures = ArrayBuffer [Figure] ()

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
            figures.foreach(_.deleteLastFrame())
            lastFrame.remove()
            frames -= lastFrame
        }
    }

    def setCurrFrame(frame: Frame) {
        currFrame = frame
        figures.foreach(_.loadFrameData())
    }

    def nextFrame(): Unit = {
        if (currFrame != frames.last) {
            setCurrFrame(frames.find(_.previous == Some(currFrame)).get)
        }
    }

    def previousFrame(): Unit = {
        if (currFrame != frames.head) {
            setCurrFrame(currFrame.previous.get)
        }
    }

    def saveFrameData(): Unit = {
        figures.foreach(_.saveFrameData())
    }

    //count of frames from start _until_ end
    def framesBetween(start: Frame, end: Frame): Int = {
        frames.indexOf(end) - frames.indexOf(start)
    }

    //update frame data regarding the changes in key frames
    def updateFrameData(): Unit = {
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

            if (nextHasKey) figures.foreach(_.interpolate(frame, next, framesBetween(frame, next)))
            else figures.foreach(_.setDataEqual(frame, next))

            if (previousHasKey) figures.foreach(_.interpolate(previous, frame, framesBetween(previous, frame)))
            else figures.foreach(_.setDataEqual(previous, frame.previous.get))
        } else {
            if (previousHasKey && nextHasKey) figures.foreach(_.interpolate(previous, next, framesBetween(previous, next)))
            else if (nextHasKey) figures.foreach(_.setDataEqual(previous, next.previous.get))
            else figures.foreach(_.setDataEqual(previous, next))
        }
    }

    def addFigure(structure: String): Unit = {
        figures += new Figure(structure)
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
        setCurrFrame(firstFrame)

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

    //loading animation
    def read(lines: Array[String]): Unit = {
        val head = lines.head

        val keyframeIdxs = if (head.isEmpty) Array [Int] () else head.split(",").map(_.toInt)

        (frames.zipWithIndex).foreach( n => {
            if (keyframeIdxs.contains(n._2)) {
                n._1.toggleKeyFrame()
            }
        })
        setBackground(lines(1))

        var reading = true
        var left = lines.drop(2)

        while (reading) {
            if (left.nonEmpty && left.head == "Figure") {
                addFigure(left(1))
                left = figures.last.read(left.tail)
            } else {
                reading = false
            }
        }

        figures.foreach(_.loadFrameData())
    }

    //saving animation
    def write(file: PrintWriter): Unit = {
        file.write(frameCount + "\n")
        file.write(keyFramesWithIndex.map(_._2).mkString(",") + "\n")
        file.write(background + "\n")
        figures.foreach(_.write(file))
    }

    def update(): Unit = {
        figures.foreach(_.update())
        frames.foreach(_.update())

        updateFrameData()

        CurrentFrameCursor.update()
    }

    def close(): Unit = {
        this.figures.foreach(_.remove())
        this.frames.foreach(_.remove())
    }
    setBackground("basic.png")
}