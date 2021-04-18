import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

import scala.collection.mutable.ArrayBuffer

class SpeechBubble (val parentCP: ControlPoint) extends Circle with ChildFrameComponent {
    private var text = ""

    this.radius = 30
    this.stroke = Gray
    this.fill = White

    private var dxToParent = 0.0
    private var dyToParent = -200.0

    private val frameData = ArrayBuffer.fill(this.frameCount)((this.dxToParent, this.dyToParent, this.text))

    def getText = this.text

    def setText(newText: String): Unit = {
        this.text = newText
    }

    this.onMouseDragged = (event) => {
        val x = this.centerX.toDouble
        val y = this.centerY.toDouble

        val mouseX = event.getX
        val mouseY = event.getY

        this.centerX = mouseX
        this.centerY = mouseY

        this.dxToParent += mouseX - x
        this.dyToParent += mouseY - y

    }

    def addFrame(): Unit = {
        this.frameData += this.frameData.last
        this.children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        this.frameData -= this.frameData.last
        this.children.foreach(_.deleteFrame())
    }

    def updatePos(): Unit = {
        this.centerX = this.parentCP.centerX.toDouble + dxToParent
        this.centerY = this.parentCP.centerY.toDouble + dyToParent
    }

    def updateFrame(): Unit = {
        val frame = this.frameData(this.currIdx)

        this.dxToParent = frame._1
        this.dyToParent = frame._2
        this.setText(frame._3)
    }

    def updateFrameData(): Unit = {
        this.frameData(this.currIdx) = Tuple3(this.dxToParent, this.dyToParent, this.text)
    }
}