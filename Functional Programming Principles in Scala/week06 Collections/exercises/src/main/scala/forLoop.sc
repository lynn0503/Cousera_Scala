def isPrime(n:Int):Boolean={
  (2 until n) forall (i => n%i!=0)
}

(1 to 3) flatMap (i =>
  (1 to i) map (j => (i, j))) filter ( pair => isPrime(pair._1 + pair._2))

for {
  i <- 1 to 3
  j <- 1 to i
  if isPrime(i+j)
}
  yield (i,j)

def scalarProduct(xs: List[Double], ys: List[Double]) : List[Double] ={
  for(x<-xs; y<-ys) yield x*y
//  two nested for loop
}

def scalarProduct1(xs: List[Double], ys: List[Double]) : List[Double] ={
  for((x,y)<-xs zip ys) yield x*y
}

scalarProduct(List(1,2),List(3,4))
scalarProduct1(List(1,2),List(3,4))
