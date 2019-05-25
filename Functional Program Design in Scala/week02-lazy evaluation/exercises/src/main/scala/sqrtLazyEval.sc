def sqrtStream(x:Double):Stream[Double]={
  def improve(guess:Double)=(guess+x/guess)/2
  lazy val guesses:Stream[Double]=1#::(guesses map improve)
  guesses
}

def isGoodEnough(guess:Double,x:Double)=math.abs((guess*guess-x)/x)<0.0001

sqrtStream(4).take(10).toList
(sqrtStream(4) filter (isGoodEnough(_,4))).take(10).toList

