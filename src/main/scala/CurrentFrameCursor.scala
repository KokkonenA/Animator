import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Polygon

object CurrentFrameCursor extends Polygon with TimelineComponent {
    points.addAll(Timeline.line.startX.toDouble - 20, Timeline.line.startY.toDouble - 20,
        Timeline.line.startX.toDouble + 20, Timeline.line.startY.toDouble - 20,
        Timeline.line.startX.toDouble, Timeline.line.startY.toDouble)
    fill = White
    stroke = Gray
}
