import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.Image
import scalafx.scene.layout.{Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize, Pane}

object Viewer extends Pane {
    def setBackground(newBG: String) = {
        try {
            val img  = new Image("file:" + newBG)

            if (img.error.value) throw new Exception

            background = new Background(
                Array(
                    new BackgroundImage(
                        img,
                        BackgroundRepeat.NoRepeat,
                        BackgroundRepeat.NoRepeat,
                        BackgroundPosition.Center,
                        new BackgroundSize(100, 100, true, true, true, true)
                    )
                )
            )
        } catch {
            case e: Exception => new Alert(AlertType.Error) {
                contentText = "Could not change the background"
            }.showAndWait()
        }
    }
}