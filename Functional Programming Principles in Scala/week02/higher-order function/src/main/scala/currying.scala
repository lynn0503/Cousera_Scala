object currying extends App{
//  multiple parameters

  def sum(f:Int=>Int)(a:Int,b:Int):Int=
    if (a>b) 0 else f(a)+sum(f)(a+1,b)

//  println(sum(x=>x)(1,100))

  def product(f:Int=>Int)(a:Int,b:Int):Int=
    if (a>b) 1 else f(a)*product(f)(a+1,b)

  println(product(x=>x*x)(3,4))

  def fact(n:Int):Int=product(x=>x)(1,n)
  println(fact(5))


}
