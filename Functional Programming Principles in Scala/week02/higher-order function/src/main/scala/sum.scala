object sum{
  def sum(f:Int => Int, a:Int,b:Int): Int ={
    def loop(a:Int,acc:Int): Int ={
      if (a>b) acc
      else loop(a+1,acc+f(a))
    }
    loop(a,0)
  }

  def fact(n:Int): Int ={
    def loop(n:Int,acc:Int): Int ={
      if (n<=1) acc
      else loop(n-1,n*acc)
    }
    loop(n,1)

  }

  def sumInts(a:Int,b:Int)=sum(x=>x,a,b)
  def sumCube(a:Int,b:Int)=sum(x=>x*x*x,a,b)
  def sumFact(a:Int,b:Int)=sum(fact,a,b)

}
