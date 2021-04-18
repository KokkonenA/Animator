import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, Pane}
import scalafx.scene.paint.Color.Gray
import scalafx.scene.shape.Line
import scalafx.Includes._

import scala.collection.mutable.ArrayBuffer

object Timeline extends Pane {
    this.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    val line = Line(0, 0.16 * 720 / 2, 0.7 * 1280, 0.16 * 720 / 2)

    this.children.add(line)

    private var frames = ArrayBuffer.fill(this.frameCount)(new Frame)

    def currIdx = Animator.currIdx

    def currFrame = this.frames(this.currIdx)

    def frameCount = Animator.frameCount

    def addFrame(): Unit = {
        this.frames += new Frame
    }

    def deleteFrame(): Unit = {
        this.children -= this.frames.last
        this.frames -= this.frames.last
    }

    def updateCursor(): Unit = {
        CurrentFrameCursor.relocate(currFrame.startX.toDouble - 20, currFrame.startY.toDouble - 20)
    }

    def updateFrames(): Unit = {
        val length = this.frames.length

        for (i <- 0 until length) {
            val currFrame = this.frames(i)
            currFrame.startX =
                this.line.startX.toDouble + i * (this.line.endX.toDouble - this.line.startX.toDouble) / length
            currFrame.endX = currFrame.startX.toDouble
        }
    }

    def newAnimation(): Unit = {
        frames.foreach(children.remove(_))
        frames.clear()
        frames = ArrayBuffer.fill(this.frameCount)(new Frame)
    }

    def update(): Unit = {
        this.updateFrames()
        this.updateCursor()
    }
}