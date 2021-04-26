import scalafx.scene.control.TextInputDialog
import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

import scala.math.{cos, sin}

class Head (val parentCP: ControlPoint, private var expression: String) extends Circle with ChildFrameComponent {
    radius = 20
    stroke = Gray
    fill = White

    private val face = new Face(this, expression)

    private val frameData =
        collection.mutable.Map((Animator.frames zip Array.fill(frameCount)(expression)).toSeq: _*)

    private val speech = new Speech(parentCP)
    this.parentCP.children += speech

    def getExpression = expression

    def setExpression(newExpression: String): Unit = {
        expression = newExpression
    }

    def addFrameToEnd(): Unit = {
        frameData += (frames.last -> frameData.last._2)
        children.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame(): Unit = {
        frameData += frameData.last
        children.foreach(_.deleteLastFrame())
    }

    def loadFrameData(): Unit = {
        expression = frameData(currFrame)
    }

    def saveFrameData(): Unit = {
        frameData(currFrame) = expression
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
            frameData(frame) = frameData(start)
            idx += 1
            frame = frame.previous.get
        }
    }

    onMouseClicked = (event) => {
        val dialog = new TextInputDialog(defaultValue = speech.text.value) {
            initOwner(Animator.stage)
            title = "Add speech"
            headerText = "Please type text for the stick figure to say"
            contentText = "Speech: "
        }

        val result = dialog.showAndWait()

        if (result.isDefined) {
            speech.text = result.get

            if (!currFrame.isKeyFrame) {
                currFrame.toggleKeyFrame()
            }
        }
    }

    def update(): Unit = {
        val angle = parentCP.angleToScene.toRadians

        centerX = parentCP.centerX.toDouble + radius.toDouble * cos(angle)
        centerY = parentCP.centerY.toDouble - radius.toDouble * sin(angle)

        face.update()
    }
}
