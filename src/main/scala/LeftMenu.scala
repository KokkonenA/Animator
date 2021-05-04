import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, VBox}
import scalafx.scene.paint.Color.Black
import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Button

//Menu buttons on the left side of the viewer
object LeftMenu extends VBox {
    background = new Background(Array(new BackgroundFill((Black), CornerRadii.Empty, Insets.Empty)))
    spacing = 50
    alignment = Pos.Center


    //buttons for the left side menu
    val saveButton = new Button("Save") {
        maxWidth = 100
        prefHeight = 50
    }
    val loadButton = new Button("Load") {
        maxWidth = 100
        prefHeight = 50
    }
    val newButton = new Button("New") {
        maxWidth = 100
        prefHeight = 50
    }
    val exitButton = new Button("Exit") {
        maxWidth = 100
        prefHeight = 50
    }

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

    children = Array [Button] (saveButton, loadButton, newButton, exitButton)
}