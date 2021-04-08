import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints}
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

  //return current Index
  def getCurrIdx = this.frames.indexOf(this.currFrame)

  //set the current frame to a different one
  def setCurrFrame(newFrame: Frame): Unit = {
    Viewer.children.clear()
    this.currFrame = newFrame
    this.currFrame.addAll()
  }

  //set the next frame as the current one
  def nextFrame = {
    if (this.currFrame == this.frames.last) {
      println("this is the last frame")
      false
    } else {
      this.setCurrFrame(this.frames(this.getCurrIdx + 1))
      true
    }
  }

  //set the frame earlier as the current one
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
      val idx = this.getCurrIdx + 1
      this.setCurrFrame(this.currFrame.getCopy)
      this.frames.insert(idx, this.currFrame)
      true
    }
  }

  //delete all frames
  def deleteFrames(): Unit = {
    this.frames.subtractAll(this.frames)
    this.frames += new Frame
    this.setCurrFrame(this.frames(0))
  }

  //load an animation
  def loadFile = true

  //save current animation
  def saveFile = true

  //calculate frames between the key frames
  def calculateFrames(): Unit = {
    println("frames calculated")
  }

  //play the animation once from start to finnish
  def playAnimation = {
    this.calculateFrames()
    true
  }

  //exit the app
  def exit(): Unit = {
    stage.close()
  }

  //layout
  //create a gridpane as the root of the scene. Then divide it into four main parts
  val root = new GridPane
  val scene = new Scene(root)
  stage.scene = scene

  root.add(LeftMenu, 0, 0)
  root.add(RightMenu, 2, 0)
  root.add(Controller, 0, 1, 3, 1)
  root.add(Viewer, 1, 0)

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

  def update(): Unit = {
    this.currFrame.update()
  }

  val ticker = new Ticker(() => this.update())
  ticker.start()
}