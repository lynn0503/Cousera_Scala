class Rational(x:Int,y:Int){
  require(y!=0,"denominator must not be zero")
//  pre-condition
  def this(x:Int)=this(x,1)
//  auxiliary constructors
  private def gcd(a:Int,b:Int):Int=if (b==0) a else gcd(b,a%b)
  def numer=x/gcd(x,y)
  def denom=y/gcd(x,y)
//  def numer  call-by-name
//  val numer  call-by value

  def add(r:Rational)=new Rational(numer*r.denom+r.numer*denom,denom*r.denom)
  def mul(r:Rational)=new Rational(numer*r.numer,denom*r.denom)
  def neg=new Rational(-numer,denom)
  def sub(r:Rational)=add(r.neg)
  override def toString=numer+"/"+denom

  def less(that:Rational) =this.numer*that.denom < that.numer*this.denom
//this self reference
}

val x=new Rational(1,3)
val y=new Rational(5,7)
val z=new Rational(3,2)
val s=new Rational(2)
x.add(y).mul(z)
x.add(y.neg)
x.sub(y)

