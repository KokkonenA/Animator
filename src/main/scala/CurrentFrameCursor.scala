import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Polygon

object CurrentFrameCursor extends Polygon with TimelineComponent {
    private var line = Timeline.line
    points.addAll(line.startX.toDouble - 20, line.startY.toDouble - 40,
        line.startX.toDouble + 20, line.startY.toDouble - 40,
        line.startX.toDouble, line.startY.toDouble - 20)
    fill = White
    stroke = Gray

    def update(): Unit = {
        this.layoutX = Animator.currFrame.startX.toDouble
    }
}
