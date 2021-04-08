import scalafx.scene.shape.Circle
import scalafx.Includes._

import scala.collection.mutable.ArrayBuffer

class Head (val parentJoint: Joint, private var expression: String) extends Circle {
  def getExpression = this.expression

  def setExpression(newExpression: String): Unit = {
    this.expression = newExpression
  }

  def getCopy(copyJoints: ArrayBuffer[Joint]) = {
    new Head(copyJoints.find(_.name == this.parentJoint.name).get, this.expression) {
      this.centerX = Head.this.centerX.toDouble
      this.centerY = Head.this.centerY.toDouble
    }
  }

  def update(): Unit = {

  }

  Viewer.children += this
}
