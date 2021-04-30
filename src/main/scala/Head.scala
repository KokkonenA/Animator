import scalafx.scene.control.TextInputDialog
import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

import java.io.PrintWriter
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
        face.setExpression(frameData(currFrame))
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
        if (length > 0) {
            var frame = end.previous.get

            while (frame != start) {
                frameData(frame) = frameData(start)
                frame = frame.previous.get
            }
        }
    }

    onMouseClicked = (event) => {
        val dialog1 = new TextInputDialog(defaultValue = speech.text.value) {
            initOwner(Animator.stage)
            title = "Add speech"
            headerText = "Please type text for the stick figure to say"
            contentText = "Speech: "
        }

        val dialog2 = new TextInputDialog(defaultValue = expression) {
            initOwner(Animator.stage)
            title = "Change expression: "
            headerText = "Insert new expression"
            contentText = "Expression: "
        }

        val result1 = dialog1.showAndWait()
        val result2 = dialog2.showAndWait()

        if (result1.isDefined) {
            speech.text = result1.get

            if (!currFrame.isKeyFrame) {
                currFrame.toggleKeyFrame()
            }
        }

        if (result2.isDefined) {
            face.setExpression(result2.get)

            if (!currFrame.isKeyFrame) {
                currFrame.toggleKeyFrame()
            }
        }


    }

    override def remove(): Unit = {
        Viewer.children.remove(this)
        face.remove()
    }

    def read(lines: Array[String]): Array[String] = {
        var left = lines.tail

        Animator.keyFrames.foreach(n => {
            frameData(n) = left.head
            left = left.tail
        })
        left
    }

    def write(file: PrintWriter): Unit = {
        file.write("Head\n")

        Animator.keyFrames.foreach(n => {
            file.write(frameData(n))
            file.write("\n")
        })
    }

    def update(): Unit = {
        val angle = parentCP.angleToScene.toRadians

        centerX = parentCP.centerX.toDouble + radius.toDouble * cos(angle)
        centerY = parentCP.centerY.toDouble - radius.toDouble * sin(angle)

        face.update()
    }
}