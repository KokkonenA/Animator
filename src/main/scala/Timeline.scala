import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, Pane}
import scalafx.scene.paint.Color.Gray
import scalafx.scene.shape.Line
import scalafx.Includes._

object Timeline extends Pane {
    background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    val line = Line(0, 0.16 * 720 / 2, 0.7 * 1280, 0.16 * 720 / 2)
    children.add(line)
}