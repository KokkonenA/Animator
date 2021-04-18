import scalafx.scene.image.Image
import scalafx.scene.layout.{Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize, Pane}

object Viewer extends Pane {
    def setBackground(newBG: String) = {
        background = new Background(
            Array(
                new BackgroundImage(
                    new Image(newBG),
                    BackgroundRepeat.NoRepeat,
                    BackgroundRepeat.NoRepeat,
                    BackgroundPosition.Center,
                    new BackgroundSize(100, 100, true, true, true, true)
                )
            )
        )
    }
}