import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Figure () {
  private var pos = new Pos(Animator.viewerW/2, Animator.viewerH/2)

  private def loadJoints() = {
    val lines = ArrayBuffer [String] ()
    
    val bufferedSource = Source.fromFile("Structures")

    for (line <- bufferedSource.getLines()) {
      lines += line.trim
    }
    bufferedSource.close()

    val newJoints = ArrayBuffer [Joint] ()
    var i = 2

    while (lines(i).trim != "/Joints") {
      val line = lines(i).split(",")
      val name = line(0)
      val parentName = line(1)
      val parent = if(parentName == "Center") None else newJoints.find(_.name == parentName)
      val radius = line(2).toInt
      val angle = line(3).toInt
      newJoints += new Joint(name, parent, radius, angle)
      i += 1
    }
    i += 1

    val newHead = if (lines(i)(0) != '/') {
      val split = lines(i).split(",")
      println(split(0))
      val parent = newJoints.find(_.name == split(0)).get
      val expression = split(1)
      Some(new Head(parent, expression))
    } else None

    (newJoints, newHead)
  }

  val structure = this.loadJoints()

  val joints = this.structure._1

  val head = this.structure._2

  def draw(): Unit = {
    Animator.getG.fillOval(this.pos.x, this.pos.y, 10, 10)
    this.joints.foreach(_.draw())
  }

  def getPos = this.pos

  def moveJoint(moving: Joint, newPos: Pos): Unit = {
    moving.setPos(newPos)
  }

  def getCopy = {
    val copyPos = this.pos

    val copyJoints = ArrayBuffer[Joint] ()

    this.joints.foreach(copyJoints += _.getCopy(copyJoints))

    val copyHead = this.head match {
      case Some(head) => Some(head.getCopy(copyJoints))
      case None => None
    }

    new Figure()
  }
}
