object sqrt_better extends App{
//  put auxiliary function inside sqrt block to avoid public member
//  remove redundant x in auxiliary function

  def sqrt(x:Double)={
    def isGoodEnough(guess:Double)=Math.abs(guess*guess-x)/x<0.001
    //  use ratio rather than absolute delta value

    def improve(guess:Double)=(guess+x/guess)/2

    def sqrtIter(guess:Double): Double ={
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))
    }

    sqrtIter(1.0)

  }
  println(sqrt(2.0))


}
