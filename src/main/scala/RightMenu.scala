import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, VBox}
import scalafx.scene.paint.Color.Black
import scalafx.Includes._
import scalafx.scene.control.Button

//buttons on the right side of the timeline
object RightMenu extends VBox {
    background = new Background(Array(new BackgroundFill((Black), CornerRadii.Empty, Insets.Empty)))
    spacing = 50
    alignment = Pos.Center

    //buttons for the right side menu
    val figureButton = new Button("Figure") {
        maxWidth = 100
        prefHeight = 50
    }
    val bgButton = new Button("Background") {
        maxWidth = 100
        prefHeight = 50
    }

    //Actions for the buttons
    figureButton.onAction = (event) => {
        Animator.addFigure()
    }

    bgButton.onAction = (event) => {
        Animator.setBackground()
    }

    //add the buttons as the children of the rightMenu
    children = Array [Button] (figureButton, bgButton)
}
