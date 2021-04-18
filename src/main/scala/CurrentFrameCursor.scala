import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Polygon

object CurrentFrameCursor extends Polygon with TimelineComponent {
    this.points.addAll(Timeline.line.startX.toDouble - 20, Timeline.line.startY.toDouble - 20,
        Timeline.line.startX.toDouble + 20, Timeline.line.startY.toDouble - 20,
        Timeline.line.startX.toDouble, Timeline.line.startY.toDouble)
    this.fill = White
    this.stroke = Gray
}
