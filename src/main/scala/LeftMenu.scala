import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, VBox}
import scalafx.scene.paint.Color.Gray
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.control.Button

object LeftMenu extends VBox {
    this.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))

    //buttons for the left side menu
    val saveButton = new Button("Save")
    val loadButton = new Button("Load")
    val newButton = new Button("New")
    val exitButton = new Button("Exit")

    //actions of the buttons
    saveButton.onAction = (event) => {
        Animator.saveAnimation()
    }

    loadButton.onAction = (event) => {
        Animator.loadAnimation()
    }

    newButton.onAction = (event) => {
        Animator.newAnimation()
    }

    exitButton.onAction = (event) => {
        Animator.exit()
    }

    this.children = Array [Button] (saveButton, loadButton, newButton, exitButton)
}