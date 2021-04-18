import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, Pane}
import scalafx.scene.paint.Color.Gray
import scalafx.Includes._

object FrameController extends Pane {
    this.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    val addFrameButton = new Button ("+") {
        this.relocate(50, 20)
    }
    val delFrameButton = new Button("-") {
        this.relocate(100, 20)
    }

    addFrameButton.onMouseClicked = (event) => {
        Animator.addFrame()
    }
    delFrameButton.onMouseClicked = (event) => {
        Animator.deleteFrame()
    }

    this.children.addAll(addFrameButton, delFrameButton)
}
