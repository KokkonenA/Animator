import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

class SpeechBubble (val parentCP: ControlPoint) extends Circle with ChildFrameComponent {
    private var text = ""

    radius = 30
    stroke = Gray
    fill = White

    private var dxToParent = 0.0
    private var dyToParent = -200.0

    private val frameData =
        collection.mutable.Map((frames zip Array.fill(frameCount)((dxToParent, dyToParent, text))).toSeq: _*)

    def getText = text

    def setText(newText: String): Unit = {
        text = newText
    }

    onMouseDragged = (event) => {
        val x = centerX.toDouble
        val y = centerY.toDouble

        val mouseX = event.getX
        val mouseY = event.getY

        centerX = mouseX
        centerY = mouseY

        dxToParent += mouseX - x
        dyToParent += mouseY - y

        if (!currFrame.isKeyFrame) {
            currFrame.toggleKeyFrame()
        }
    }

    def addFrameToEnd(): Unit = {
        frameData += frameData.last
        children.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame(): Unit = {
        frameData -= frameData.last._1
        children.foreach(_.deleteLastFrame())
    }

    def update(): Unit = {
        centerX = parentCP.centerX.toDouble + dxToParent
        centerY = parentCP.centerY.toDouble + dyToParent
    }

    def loadFrameData(): Unit = {
        val frame = frameData(currFrame)

        dxToParent = frame._1
        dyToParent = frame._2
        setText(frame._3)
    }

    def saveFrameData(): Unit = {
        frameData(currFrame) = Tuple3(dxToParent, dyToParent, text)
    }
}