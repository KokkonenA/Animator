import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, BackgroundFill, ColumnConstraints, CornerRadii, GridPane, RowConstraints, VBox}
import scalafx.scene.paint.Color.{Blue, Gray}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scala.collection.mutable.ArrayBuffer

object Animator extends JFXApp {
  //create stage for scalaFX
  stage = new JFXApp.PrimaryStage {
    title.value = "Stick Figure Animator"
    width = 1280
    height = 720
  }

  val frames = ArrayBuffer [Frame] (new Frame)  //all frames in current animation
  private var currFrame = frames(0)   //frame that the app currently shows

  //return current frame
  def getCurrFrame = this.currFrame

  //set the current frame to a different one
  def setCurrFrame(newFrame: Frame): Unit = {
    this.currFrame = newFrame
  }

  //delete current frame
  def delCurrFrame(): Unit = {
    val currIdx =this.frames.indexOf(this.currFrame)
    this.frames -= this.currFrame

    if (this.frames.isEmpty) {
      this.frames += new Frame
      this.currFrame = this.frames(0)
    } else if(currIdx == this.frames.size) {
      this.currFrame = this.frames(currIdx - 1)
    } else {
      this.currFrame = this.frames(currIdx)
    }
  }


  //add new frame next to the current one. The new frame and the current one
  //are identical
  def addFrame() = {
    val currIdx = this.frames.indexOf(this.currFrame)
    val newFrame = this.currFrame.getCopy

    this.frames.insert(currIdx, newFrame)
  }

  def loadFile = ???

  def saveFile = ???

  def playAnimation = ???

  def exit(): Unit = {
    stage.close()
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

  //actions of the buttons
  saveButton.onAction = (event) => {
    println("saved")
  }

  loadButton.onAction = (event) => {
    println("loaded")
  }

  newButton.onAction = (event) => {
    this.frames.subtractAll(this.frames)
    this.frames += new Frame
    this.currFrame = this.frames(0)

    println("new animation created")
  }

  exitButton.onAction = (event) => {
    println("Good Bye!")
    this.exit()
  }

  leftMenu.children = Array [Button] (saveButton, loadButton, newButton, exitButton)

  //buttons for the right side menu
  val figureButton = new Button("Figure")
  val speechButton = new Button("Speech")
  val bgButton = new Button("BG")
  val frameButton = new Button("Step")

  figureButton.onAction = (event) => {
    println("figure added")
  }

  speechButton.onAction = (event) => {
    println("speech Bubble added")
  }

  bgButton.onAction = (event) => {
    println("background changed")
  }

  frameButton.onAction = (event) => {
    println("new frame added")
  }

  rightMenu.children = Array [Button] (figureButton, speechButton, bgButton, frameButton)
}