import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scalafx.scene.layout._
import scalafx.scene.paint.Color.{Blue, Green, Red}
import scalafx.Includes._

object Controller extends GridPane {
    this.background = new Background(Array(new BackgroundFill((Blue), CornerRadii.Empty, Insets.Empty)))

    //Elements of the controller
    val switchFrames = new HBox
    val playButton = new Button("Play")
    val timeline = new Canvas(10, 20)

    this.add(switchFrames, 0, 0)
    this.add(playButton, 0, 1)
    this.add(timeline, 1, 0)

    //Define grid row and column size
    val column10 = new ColumnConstraints
    val column11 = new ColumnConstraints
    val row10 = new RowConstraints
    val row11 = new RowConstraints

    column10.percentWidth = 15
    column11.percentWidth = 85
    row10.percentHeight = 50
    row11.percentHeight = 50

    this.columnConstraints = Array[ColumnConstraints](column10, column11)
    this.rowConstraints = Array[RowConstraints](row10, row11)

    switchFrames.background = new Background(Array(new BackgroundFill((Red), CornerRadii.Empty, Insets.Empty)))
    playButton.background = new Background(Array(new BackgroundFill((Green), CornerRadii.Empty, Insets.Empty)))

    val nextButton = new Button("Next")
    val previousButton = new Button("Previous")

    //actions of the buttons
    previousButton.onAction = (event) => {
        Animator.previousFrame()
    }

    nextButton.onAction = (event) => {
        Animator.nextFrame()
    }

    switchFrames.children = Array [Button] (previousButton, nextButton)

    playButton.onAction = (event) => {
        Animator.playAnimation()
    }
}
