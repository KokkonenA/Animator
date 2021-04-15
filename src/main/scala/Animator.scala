import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.TextInputDialog
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints}

object Animator extends JFXApp {
    //create stage for scalaFX
    stage = new JFXApp.PrimaryStage {
        title.value = "Stick Figure Animator"
        width = 1280
        height = 720
    }

    private var animation = new Animation

    def newAnimation(): Unit = {
        this.animation = new Animation
    }

    def nextFrame(): Unit = {

    }

    def previousFrame(): Unit = {

    }

    //load an animation
    def loadAnimation(): Unit = {

    }

    //save current animation
    def saveAnimation(): Unit = {

    }

    //play the animation once from start to finnish
    def playAnimation() = {

    }

    def setBackground(newBG: String): Unit = {
        this.animation.setBackground(newBG)
    }

    def addFigure(): Unit = {
        val dialog = new TextInputDialog(defaultValue = "Basic") {
            initOwner(stage)
            title = "Add Figure"
            headerText = "Please type the name of the structure."
            contentText = "Structure name: "
        }

        val result = dialog.showAndWait()

        if (result.isDefined) this.animation.addFigure(result.get)
    }

    def addFrame(): Unit = {

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
}