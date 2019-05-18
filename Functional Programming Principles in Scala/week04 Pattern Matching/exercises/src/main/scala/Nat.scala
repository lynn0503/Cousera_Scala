//define natural numbers without primitive types
//Peano numbers

abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor = new Succ(this)
  def +(that: Nat): Nat
  def -(that: Nat): Nat
}

object Zero extends Nat {
  def isZero = true
  def predecessor = throw new Error("0 has no predecessor")
  def +(that: Nat) = that
  def -(that: Nat) =
    if (that.isZero) this else throw new Error("negative number")

  override def toString: String = "0"
}

class Succ(n: Nat) extends Nat {
  def isZero = false
  def predecessor = n
  def +(that: Nat) = new Succ(n + that)
  def -(that: Nat) = if (that.isZero) this else (n - that.predecessor)

  override def toString = n.toString + "+1"
}

object nat {
  val z = Zero
  val a = z.successor.successor
  println(z.successor.+(a))

}
