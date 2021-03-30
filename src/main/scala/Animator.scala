import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, BackgroundFill, ColumnConstraints, CornerRadii, GridPane, HBox, RowConstraints, VBox}
import scalafx.scene.paint.Color.{Black, Blue, Gray, Green, Red}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button

import scala.collection.mutable.ArrayBuffer

object Animator extends JFXApp {
  def viewerW = 720
  def viewerH = 480

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

  def getCurrIdx = this.frames.indexOf(this.currFrame)

  //set the current frame to a different one
  def setCurrFrame(newFrame: Frame): Unit = {
    this.currFrame = newFrame
  }

  def nextFrame = {
    if (this.currFrame == this.frames.last) {
      println("this is the last frame")
      false
    }
    else {
      this.setCurrFrame(this.frames(this.getCurrIdx + 1))
      true
    }
  }

  def previousFrame = {
    if (this.currFrame == this.frames.head) {
      println("this is the first frame")
      false
    }
    else {
      this.setCurrFrame(this.frames(this.getCurrIdx - 1))
      true
    }
  }

  //delete current frame
  def delCurrFrame(): Unit = {
    val currIdx = this.getCurrIdx
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
  def addFrame = {
    if (this.frames.size == 10) {
      println("You have reached the maximum number of key frames")
      false
    } else {
      val newFrame = this.currFrame.getCopy
      this.frames.insert(this.getCurrIdx + 1, newFrame)
      true
    }
  }

  def deleteFrames(): Unit = {
    this.frames.subtractAll(this.frames)
    this.frames += new Frame
    this.currFrame = this.frames(0)
  }

  def loadFile = true

  def saveFile = true

  def calculateFrames(): Unit = {
    println("frames calculated")
  }

  def playAnimation = {
    this.calculateFrames()
    true
  }

  def exit(): Unit = {
    stage.close()
  }

  //create a gridpane as the root of the scene. Then divide it into four main parts
  val root = new GridPane
  val scene = new Scene(root)
  stage.scene = scene

  val leftMenu = new VBox
  val rightMenu = new VBox
  val controller = new GridPane
  val viewer = new Canvas(this.viewerW, this.viewerH)

  root.add(leftMenu, 0, 0)
  root.add(rightMenu, 2, 0)
  root.add(controller, 0, 1, 3, 1)
  root.add(viewer, 1, 0)

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
  rightMenu.background = new Background(Array(new BackgroundFill((Black), CornerRadii.Empty, Insets.Empty)))
  controller.background = new Background(Array(new BackgroundFill((Blue), CornerRadii.Empty, Insets.Empty)))

  //buttons for the left side menu
  val saveButton = new Button("Save")
  val loadButton = new Button("Load")
  val newButton = new Button("New")
  val exitButton = new Button("Exit")

  //actions of the buttons
  saveButton.onAction = (event) => {
    if (this.saveFile) println("saved")
  }

  loadButton.onAction = (event) => {
    if (this.loadFile) println("loaded")
  }

  newButton.onAction = (event) => {
    this.deleteFrames()
    println("new animation initilized")
  }

  exitButton.onAction = (event) => {
    this.exit()
    println("Good Bye!")
  }

  leftMenu.children = Array [Button] (saveButton, loadButton, newButton, exitButton)

  //buttons for the right side menu
  val figureButton = new Button("Figure")
  val speechButton = new Button("Speech")
  val bgButton = new Button("BG")
  val frameButton = new Button("Frame")

  figureButton.onAction = (event) => {
    if (this.currFrame.addFigure) println("figure added")
  }

  speechButton.onAction = (event) => {
    if (this.currFrame.addSpeech) println("speech Bubble added")
  }

  bgButton.onAction = (event) => {
    this.currFrame.setBackground("basic")
    println("background changed")
  }

  frameButton.onAction = (event) => {
    if (this.addFrame) println("New frame added")
    else println("Couldn't add new frame")
    println("Now there are " + this.frames.size + " frames")
  }

  //add the buttons as the children of the rightMenu
  rightMenu.children = Array [Button] (figureButton, speechButton, bgButton, frameButton)

  //Elements of the controller
  val switchFrames = new HBox
  val playButton = new Button("Play")
  val timeline = new Canvas(10, 20)

  controller.add(switchFrames, 0, 0)
  controller.add(playButton, 0, 1)
  controller.add(timeline, 1, 0)

  //Define grid row and column size
  val column10 = new ColumnConstraints
  val column11 = new ColumnConstraints
  val row10 = new RowConstraints
  val row11 = new RowConstraints

  column10.percentWidth = 15
  column11.percentWidth = 85
  row10.percentHeight = 50
  row11.percentHeight = 50

  controller.columnConstraints = Array[ColumnConstraints](column10, column11)
  controller.rowConstraints = Array[RowConstraints](row10, row11)

  switchFrames.background = new Background(Array(new BackgroundFill((Red), CornerRadii.Empty, Insets.Empty)))
  playButton.background = new Background(Array(new BackgroundFill((Green), CornerRadii.Empty, Insets.Empty)))

  val nextButton = new Button("Next")
  val previousButton = new Button("Previous")

  //actions of the buttons
  previousButton.onAction = (event) => {
    if (this.previousFrame) println("Moved 1 frame backward")
    else println("Could not change frame")
  }

  nextButton.onAction = (event) => {
    if (this.nextFrame) println("Moved 1 frame forward")
    else println("Could not change frame")
  }

  switchFrames.children = Array [Button] (previousButton, nextButton)

  playButton.onAction = (event) => {
    if (this.playAnimation) println("Animation Played")
  }

  //canvas
  //graphics context
  val g = this.viewer.graphicsContext2D

  def draw(): Unit = {
    this.frames.foreach(_.draw())
  }

  val ticker = new Ticker(() => this.draw())
  ticker.start()
}