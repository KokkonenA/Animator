import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, Pane}
import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Polygon
import scalafx.Includes._

object PlayController extends Pane {
    background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    val previousButton = Polygon (50, 30, 70.0, 20.0, 70.0, 40.0)
    previousButton.fill = White
    previousButton.stroke = Gray

    val nextButton = Polygon (80, 20.0, 80.0, 40.0, 100.0, 30.0)
    nextButton.fill = White
    nextButton.stroke = Gray

    val playButton = Polygon (60, 60, 60, 90, 90, 75)
    playButton.fill = White
    playButton.stroke = Gray

    //actions of the buttons
    previousButton.onMouseClicked = (event) => {
        Animator.previousFrame()
    }
    nextButton.onMouseClicked = (event) => {
        Animator.nextFrame()
    }
    playButton.onMouseClicked = (event) => {
        Animator.playAnimation()
    }

    children.addAll(previousButton, nextButton, playButton)
}