import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, TextInputDialog}
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints}

import java.io._
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Animator extends JFXApp {
    //create stage for scalaFX
    stage = new JFXApp.PrimaryStage {
        title.value = "Stick Figure Animator"
        width = 1280
        height = 720
    }

    //current animation
    private var animation = new Animation(30)

    //create new Animation
    def newAnimation(): Unit = {
        animation.close()
        animation = new Animation(30)
    }

    //select one frame later as the current frame
    def nextFrame(): Unit = {
        animation.nextFrame()
    }

    def frames = animation.frames

    def keyFrames = animation.keyFrames

    def currFrame = animation.getCurrFrame

    def frameCount = animation.frameCount

    //select one frame earlier as the current frame
    def previousFrame(): Unit = {
        animation.previousFrame()
    }

    //load an animation
    def loadAnimation(): Unit = {
        val dialog = new TextInputDialog {
            initOwner(stage)
            title = "Load Animation"
            headerText = "Type the name of the animation file"
            contentText = "File name: "
        }
        val result = dialog.showAndWait()

        if (result.isDefined) {
            try {
                val source = scala.io.Source.fromFile(result.get + ".txt")
                val lines = source.getLines().toArray
                animation.close()
                animation = new Animation(lines.head.toInt)
                animation.read(lines.tail)
                source.close()
            } catch {
                case e: FileNotFoundException => new Alert(AlertType.Error) {
                    contentText = "File not found"
                }.showAndWait()
            }
        }
    }

    //save current animation
    def saveAnimation(): Unit = {
        val dialog = new TextInputDialog() {
            initOwner(stage)
            title = "Save Animation"
            headerText = "Please type the name of the new file"
            contentText = "File name: "
        }
        val result = dialog.showAndWait()

        if (result.isDefined) {
            try {
                val file = new PrintWriter(new File(result.get + ".txt"))
                animation.write(file)
                file.close()
            } catch {
                case e: Exception => new Alert(AlertType.Error) {
                    contentText = "Could not save the file"
                }.showAndWait()
            }
        }
    }

    //play the animation once from start to finnish
    def playAnimation() = {
        animation.play()
    }

    //change background
    def setBackground(): Unit = {
        val dialog = new TextInputDialog(defaultValue = "basic.png") {
            initOwner(stage)
            title = "Change background"
            headerText = "Please type the name of the background file."
            contentText = "File name: "
        }
        val result = dialog.showAndWait()

        if (result.isDefined) animation.setBackground(result.get)
    }

    //add new Figure to current animation
    def addFigure(): Unit = {
        val dialog = new TextInputDialog(defaultValue = "Basic") {
            initOwner(stage)
            title = "Add Figure"
            headerText = "Please type the name of the structure."
            contentText = "Structure name: "
        }
        val dialogResult = dialog.showAndWait()

        if (dialogResult.isDefined) {
            val lines = ArrayBuffer [String] ()
            val bufferedSource = Source.fromFile("Structures")

            for (line <- bufferedSource.getLines()) {
                lines += line.trim
            }
            bufferedSource.close()

            val startIdx = lines.indexOf(dialogResult.get)
            val endIdx = lines.indexOf("/" + dialogResult)

            if (startIdx != -1 || endIdx != -1) animation.addFigure(dialogResult.get)
            else {
                new Alert(AlertType.Error) {
                    initOwner(Animator.stage)
                    title = "Error"
                    headerText = "Couldn't find a structure \"" + dialogResult.get + "\""
                }.showAndWait()
            }
        }
    }

    def deleteFigure(figure: Figure): Unit = {
        animation.deleteFigure(figure)
    }

    //add new Frame to current Animation
    def addFrame(): Unit = {
        animation.addFrameToEnd()
    }

    def deleteFrame(): Unit = {
        animation.deleteLastFrame()
    }

    def saveFrameData(): Unit = {
        animation.saveFrameData()
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

    //add objects LeftMenu, RightMenu, Controller and Viewer to root GridPane
    root.add(LeftMenu, 0, 0)
    root.add(RightMenu, 2, 0)
    root.add(Viewer, 1, 0)
    root.add(PlayController, 0, 1)
    root.add(FrameController, 2, 1)
    root.add(Timeline, 1, 1)

    //Define grid row and column sizes
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

    //implement constraints to root Gridpane
    root.columnConstraints = Array[ColumnConstraints](column0, column1, column2)
    root.rowConstraints = Array[RowConstraints](row0, row1)


    def update() = {
        animation.update()
    }

    val ticker = new Ticker(update)
    ticker.start()
}