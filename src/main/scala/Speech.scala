import scalafx.scene.paint.Color.Black
import scalafx.scene.text.{Font, Text}

class Speech(val parentCP: ControlPoint) extends Text with ChildFrameComponent {
    fill = Black
    font = new Font(50)
    text = ""

    private var dxToParent = 0.0
    private var dyToParent = -100.0

    private val frameData =
        collection.mutable.Map((frames zip Array.fill(frameCount)((dxToParent, dyToParent, text.value))).toSeq: _*)

    onMouseDragged = (event) => {
        val x = parentCP.centerX.toDouble
        val y = parentCP.centerY.toDouble

        val mouseX = event.getX
        val mouseY = event.getY

        dxToParent = mouseX - x
        dyToParent = mouseY - y

        if (!currFrame.isKeyFrame) {
            currFrame.toggleKeyFrame()
        }
    }

    def addFrameToEnd(): Unit = {
        frameData += (frames.last -> frameData.last._2)
        children.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame(): Unit = {
        frameData -= frameData.last._1
        children.foreach(_.deleteLastFrame())
    }

    def update(): Unit = {
        x = parentCP.centerX.toDouble + dxToParent
        y = parentCP.centerY.toDouble + dyToParent
    }

    def loadFrameData(): Unit = {
        val frame = frameData(currFrame)

        dxToParent = frame._1
        dyToParent = frame._2
        println(frame._3)
        text = frame._3
    }

    def saveFrameData(): Unit = {
        frameData(currFrame) = Tuple3(dxToParent, dyToParent, text.value)
    }

    def setDataEqual(start: Frame, end: Frame): Unit = {
        var frame = end

        while (frame != start) {
            frameData(frame) = frameData(start)
            frame = frame.previous.get
        }
    }

    def interpolate(start: Frame, end: Frame, length: Int): Unit = {
        var idx = 0

        var frame = end

        while(frame != start) {
            frameData(frame) = (
                frameData(end)._1 + (frameData(start)._1 - frameData(end)._1) * idx / length,
                frameData(end)._2 + (frameData(start)._2 - frameData(end)._2) * idx / length,
                    if (frame != end) frameData(start)._3 else frameData(end)._3
                )
            idx += 1
            frame = frame.previous.get
        }
    }
}