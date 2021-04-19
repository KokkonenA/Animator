import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Rectangle

class Key(frame: Frame) extends Rectangle with TimelineComponent {
    fill = White
    stroke = Gray
    width = 15
    height = 15
    rotate = 45

    layoutY = frame.endY.toDouble - frame.startY.toDouble + height.toDouble / 2

    def update(): Unit = {
        layoutX = frame.endX.toDouble - width.toDouble / 2
    }
}
