import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Figure {
  def loadJoints = {
    val lines = ArrayBuffer [String] ()
    
    val bufferedSource = Source.fromFile("Structures")

    for (line <- bufferedSource.getLines()) {
      lines += line.trim
    }
    bufferedSource.close()

    val newJoints = ArrayBuffer [Joint] ()
    newJoints += new Joint("Center", None, 0, 0)
    var i = 2

    while (lines(i).trim != "/Joints") {
      val line = lines(i).split(",")
      val name = line(0)
      val parentName = line(1)
      val parent = newJoints.find(_.name == parentName)
      val radius = line(2).toInt
      val angle = line(3).toInt
      newJoints += new Joint(name, parent, radius, angle)
      i += 1
    }
    i += 1

    val newHead = if (lines(i)(0) != '/') {
      val split = lines(i).split(",")
      val parent = newJoints.find(_.name == split(0)).get
      val expression = split(1)
      Some(new Head(parent, expression))
    } else None

    (newJoints, newHead)
  }

  val structure = this.loadJoints

  val joints = this.structure._1

  val head = this.structure._2

  def getCopy = {
    val copyJoints = ArrayBuffer[Joint] ()

    this.joints.foreach(copyJoints += _.getCopy(copyJoints))

    val copyHead = this.head match {
      case Some(head) => Some(head.getCopy(copyJoints))
      case None => None
    }

    new Figure() {
      override def loadJoints = (copyJoints, copyHead)
    }
  }

  def update(): Unit = {
    this.joints.foreach(_.update())
    this.joints.foreach(_.reset())
    if (this.head.isDefined) this.head.get.update()
  }
}
