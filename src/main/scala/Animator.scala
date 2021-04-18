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

    //current animation
    private var animation = new Animation

    //create new Animation
    def newAnimation(): Unit = {
        Viewer.children.clear() //clear current objects in viewer
        this.animation = new Animation
    }

    //select one frame later as the current frame
    def nextFrame(): Unit = {
        this.animation.nextFrame()
    }

    def currIdx = this.animation.getCurrIdx

    def frameCount = this.animation.getFrameCount

    //select one frame earlier as the current frame
    def previousFrame(): Unit = {
        this.animation.previousFrame()
    }

    //load an animation
    def loadAnimation(): Unit = {

    }

    //save current animation
    def saveAnimation(): Unit = {

    }

    //play the animation once from start to finnish
    def playAnimation() = {
        this.animation.play()
    }

    //change background
    def setBackground(newBG: String): Unit = {
        this.animation.setBackground(newBG)
    }

    //add new Figure to current animation
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

    //add new Frame to current Animation
    def addFrame(): Unit = {
        this.animation.addFrame()
    }

    def deleteFrame(): Unit = {
        this.animation.deleteFrame()
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
        this.animation.update()
        Timeline.update()
    }

    val ticker = new Ticker(update)
    ticker.start() // Remember this!
}