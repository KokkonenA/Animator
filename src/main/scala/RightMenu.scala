import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, VBox}
import scalafx.scene.paint.Color.Black
import scalafx.Includes._
import scalafx.scene.control.Button

object RightMenu extends VBox {
    this.background = new Background(Array(new BackgroundFill((Black), CornerRadii.Empty, Insets.Empty)))

    //buttons for the right side menu
    val figureButton = new Button("Figure")
    val speechButton = new Button("Speech")
    val bgButton = new Button("BG")
    val frameButton = new Button("Frame")

    figureButton.onAction = (event) => {
        println("figure added")
        Animator.getCurrFrame.addFigure()
    }

    speechButton.onAction = (event) => {
        Animator.getCurrFrame.addSpeech()
        println("speech Bubble added")
    }

    bgButton.onAction = (event) => {
        Animator.getCurrFrame.setBackground("file:Basic.png")
        println("background changed")
    }

    frameButton.onAction = (event) => {
        if (Animator.addFrame) println("New frame added")
        else println("Couldn't add new frame")
        //println("Now there are " + Animator.frames.size + " frames")
    }

    //add the buttons as the children of the rightMenu
    this.children = Array [Button] (figureButton, speechButton, bgButton, frameButton)
}
