import scalafx.scene.paint.Color.{Black, White}
import scalafx.scene.shape.{Arc, ArcType, Circle}

class Face(val head: Head, expression: String) {
    private abstract class Eye extends Circle {
        centerY = head.centerY.toDouble - 7
        radius = 3
        fill = Black
    }

    private val leftEye = new Eye {
        centerX = head.centerX.toDouble - 10
    }

    private val rightEye = new Eye {
        centerX = head.centerX.toDouble + 10
    }

    private val mouth = new Arc {
        length = 180

        centerX = head.centerX.toDouble
        centerY = head.centerY.toDouble + 10

        radiusX = 10
        radiusY = 0

        fill = White
        stroke = Black

        `type` = ArcType.Round
    }

    private def happy(): Unit ={
        mouth.radiusY = 5
        mouth.rotate = 180
        head.setExpression("happy")
    }

    private def sad(): Unit = {
        mouth.radiusY = 5
        mouth.rotate = 0
        head.setExpression("sad")
    }

    private def neutral(): Unit = {
        mouth.radiusY = 0
        head.setExpression("neutral")
    }

    def setExpression(newExpression: String): Unit = {
        newExpression.toLowerCase match {
            case "happy" => happy()
            case "sad" => sad()
            case "neutral" => neutral()
            case _ =>
        }
    }

    def remove(): Unit = {
        Viewer.children.removeAll(leftEye, rightEye, mouth)
    }

    def update(): Unit = {
        val angle = head.parentCP.angleToScene

        leftEye.centerX = head.centerX.toDouble - 10
        leftEye.centerY = head.centerY.toDouble - 7

        rightEye.centerX = head.centerX.toDouble + 10
        rightEye.centerY = head.centerY.toDouble - 7

        mouth.centerX = head.centerX.toDouble
        mouth.centerY = head.centerY.toDouble + 10

    }
    setExpression(expression)
    Viewer.children.addAll(leftEye, rightEye, mouth)
}