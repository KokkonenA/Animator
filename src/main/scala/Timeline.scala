import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, Pane}
import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.{Line, Polygon}
import scalafx.Includes._
import scala.collection.mutable.ArrayBuffer

object Timeline extends Pane {
    this.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    val frames = ArrayBuffer.fill(this.frameCount)(frame)

    val hLine = Line(0, 0.16 * 720 / 2, 0.7 * 1280, 0.16 * 720 / 2)

    val timelineCursor = Polygon (
        this.hLine.startX.toDouble - 20, this.hLine.startY.toDouble - 20,
        this.hLine.startX.toDouble + 20, this.hLine.startY.toDouble - 20,
        this.hLine.startX.toDouble, this.hLine.startY.toDouble)
    timelineCursor.fill = White
    timelineCursor.stroke = Gray

    this.children.addAll(this.timelineCursor, hLine)

    def currIdx = Animator.currIdx

    def currFrame = this.frames(this.currIdx)

    def frameCount = Animator.frameCount

    def frame = Line(hLine.startX.toDouble,
        hLine.startY.toDouble - 20,
        hLine.startX.toDouble,
        hLine.startY.toDouble + 20)

    def updateCursor(): Unit = {
        println(currIdx)
        timelineCursor.relocate(currFrame.startX.toDouble - 20, currFrame.startY.toDouble - 20)
    }

    def updateFrames(): Unit = {
        val length = this.frames.length

        for (i <- 0 until length) {
            val currFrame = this.frames(i)
            currFrame.startX =
                this.hLine.startX.toDouble + i * (this.hLine.endX.toDouble - this.hLine.startX.toDouble) / length
            currFrame.endX = currFrame.startX.toDouble
        }
        this.updateCursor()
    }

    def addFrame(): Unit = {
        val newFrame = this.frame
        this.frames += newFrame
        this.children += newFrame
        this.updateFrames()
    }

    def deleteFrame(): Unit = {
        this.children -= this.frames.last
        this.frames -= this.frames.last
        this.updateFrames()
    }
}