import scalafx.geometry.Insets
import scalafx.scene.layout._
import scalafx.scene.paint.Color.Gray
import scalafx.Includes._

object Controller extends GridPane {
    this.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    this.add(PlayController, 0, 0)
    this.add(Timeline, 1, 0)
    this.add(FrameController, 2, 0)

    //Define grid row and column sizes
    val column0 = new ColumnConstraints
    val column1 = new ColumnConstraints
    val column2 = new ColumnConstraints

    column0.percentWidth = 15
    column1.percentWidth = 70
    column2.percentWidth = 15

    //implement constraints to root Gridpane
    this.columnConstraints = Array[ColumnConstraints](column0, column1, column2)
}