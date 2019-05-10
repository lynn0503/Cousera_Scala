object sqrt{

  def isGoodEnough(guess:Double,x:Double)=Math.abs(guess*guess-x)/x<0.001
//  use ratio rather than absolute delta value

  def improve(guess:Double,x:Double)=(guess+x/guess)/2

  def sqrtIter(guess:Double,x:Double): Double ={
    if (isGoodEnough(guess,x)) guess
    else sqrtIter(improve(guess,x),x)
  }

  def sqrt(x:Double)=sqrtIter(1.0,x)


}