import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, VBox}
import scalafx.scene.paint.Color.Black
import scalafx.Includes._
import scalafx.scene.control.Button

object RightMenu extends VBox {
    this.background = new Background(Array(new BackgroundFill((Black), CornerRadii.Empty, Insets.Empty)))

    //buttons for the right side menu
    val figureButton = new Button("Figure")
    val bgButton = new Button("BG")
    val frameButton = new Button("Frame")

    //Actions for the buttons
    figureButton.onAction = (event) => {
        Animator.addFigure()
    }

    bgButton.onAction = (event) => {
        Animator.setBackground("file:basic.png")
    }

    frameButton.onAction = (event) => {
        Animator.addFrame()
    }

    //add the buttons as the children of the rightMenu
    this.children = Array [Button] (figureButton, bgButton, frameButton)
}
