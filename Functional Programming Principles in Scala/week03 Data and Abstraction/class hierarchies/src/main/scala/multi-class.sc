
abstract class IntSet{
  def incl(x:Int):IntSet
  def contains(x:Int):Boolean
  def union(other:IntSet):IntSet
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet{
  def contains(x:Int):Boolean=
    if (x<elem) left contains x
    else if (x>elem) right contains x
    else true

  def incl(x:Int):IntSet=
    if (x<elem) new NonEmpty(elem,left incl x,right)
    else if (x>elem) new NonEmpty(elem,left,right incl x)
    else this
  //  already have the value then return this tree
  //  include new element in new tree, old tree still maintained

  def union(other:IntSet):IntSet=
    ((left union right) union other) incl elem

  override def toString ="{"+left+elem+right+"}"
}

object Empty extends IntSet{
//  singleton object
//  no other Empty instance can/need to be created
//  singleton objects are values
//  reference without new
  def contains(x:Int):Boolean=false
  def incl(x:Int):IntSet=new NonEmpty(x, Empty, Empty)
  def union(other:IntSet)=other
  override def toString = "."

}



val t1 = new NonEmpty(3, Empty, Empty)
val t2 = t1 incl 4
val t3= t2 incl 5
val t4=t3 incl 1

t3 union t4

