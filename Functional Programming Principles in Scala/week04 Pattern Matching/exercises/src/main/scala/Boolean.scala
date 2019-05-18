abstract class Boolean {
  def ifThenElse[T](t: => T, e: => T): T
  //  t for then part, e for else part
  def &&(x: => Boolean): Boolean = ifThenElse(x, fals)
  def ||(x: => Boolean): Boolean = ifThenElse(tru, x)
  def unary_! : Boolean = ifThenElse(fals, tru)
  def ==(x: Boolean): Boolean = ifThenElse(x, x.unary_!)
  def !=(x: Boolean): Boolean = ifThenElse(x.unary_!, x)
  def <(x: Boolean): Boolean = ifThenElse(fals, x)
  def >(x: Boolean): Boolean = ifThenElse(x.unary_!, fals)
}

object tru extends Boolean {
  def ifThenElse[T](t: => T, e: => T) = t
  override def toString: String = "true"
}

object fals extends Boolean {
  def ifThenElse[T](t: => T, e: => T) = e
  override def toString: String = "false"
}

object Main extends App {
  val a = tru
  val b = fals
  println(a > b)
  println(a == b)
}
