import scala.collection.mutable.ArrayBuffer

class Head (val parent: Joint, private var expression: String) {
  def getExpression = this.expression

  def setExpression(newExpression: String): Unit = {
    this.expression = newExpression
  }

  def getCopy(copyJoints: ArrayBuffer[Joint]) = {
    new Head(copyJoints.find(_.name == this.parent.name).get, this.expression)
  }

  def draw(): Unit = {
    Animator.getG.fillOval(this.parent.getPos.x, this.parent.getPos.y, 10, 10)
  }
}
