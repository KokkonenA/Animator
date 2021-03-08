import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, BackgroundFill, ColumnConstraints, CornerRadii, GridPane, RowConstraints, VBox}
import scalafx.scene.paint.Color.{Blue, Gray}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button

object Animator extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Stick Figure Animator"
    width = 1200
    height = 675
  }

  //create a gridpane as the root of the scene. Then divide it into four main parts
  val root = new GridPane
  val scene = new Scene(root)
  stage.scene = scene

  val leftMenu = new VBox
  val rightMenu = new VBox
  val timeline = new GridPane
  val viewer = new Canvas(720, 480)

  root.add(leftMenu, 0, 0)
  root.add(rightMenu, 2, 0)
  root.add(timeline, 1, 0)
  root.add(viewer, 0, 1)

  //Define grid row and column size
  val column0 = new ColumnConstraints
  val column1 = new ColumnConstraints
  val column2 = new ColumnConstraints
  val row0 = new RowConstraints
  val row1 = new RowConstraints

  column0.percentWidth = 15
  column1.percentWidth = 70
  column2.percentWidth = 15
  row0.percentHeight = 84
  row1.percentHeight = 16

  root.columnConstraints = Array[ColumnConstraints](column0, column1, column2)
  root.rowConstraints = Array[RowConstraints](row0, row1)

  leftMenu.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))
  rightMenu.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))
  timeline.background = new Background(Array(new BackgroundFill((Blue), CornerRadii.Empty, Insets.Empty)))

  //buttons for the left side menu
  val saveButton = new Button("Save")
  val loadButton = new Button("Load")
  val newButton = new Button("New")
  val exitButton = new Button("Exit")

  saveButton.onAction = (event) => {
    println("saved")
  }

  loadButton.onAction = (event) => {
    println("loaded")
  }

  newButton.onAction = (event) => {
    println("new")
  }

  exitButton.onAction = (event) => {
    println("Good Bye!")
    stage.close()
  }

  leftMenu.children = Array [Button] (saveButton, loadButton, newButton, exitButton)

  //buttons for the right side menu
  val figureButton = new Button("Figure")
  val speechButton = new Button("Speech")
  val bgButton = new Button("BG")
  val stepButton = new Button("Step")

  figureButton.onAction = (event) => {
    println("figure added")
  }

  speechButton.onAction = (event) => {
    println("speech Bubble added")
  }

  bgButton.onAction = (event) => {
    println("background changed")
  }

  stepButton.onAction = (event) => {
    println("new step added")
  }

  rightMenu.children = Array [Button] (figureButton, speechButton, bgButton, stepButton)

}
