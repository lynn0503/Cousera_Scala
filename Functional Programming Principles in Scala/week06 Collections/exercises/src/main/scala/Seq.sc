//Range subclass of Sequence
(1 to 10) flatMap (x=> (1 to 3) map (y=>(x,y)))
(1 to 10) map (x=> (1 to 3) map (y=>(x,y)))
((1 to 10) map (x=> (1 to 3) map (y=>(x,y)))).flatten
//vector product
def vectorProd(xs:Vector[Int],ys:Vector[Int]):Vector[Int]={
  (xs zip ys) map (xy=>xy._1*xy._2)
}

def vectorProdPat(xs:Vector[Int],ys:Vector[Int]):Vector[Int]={
  xs zip ys map {case(x,y)=>x*y}
}

vectorProd(Vector(1,2,3),Vector(4,5,6))
vectorProdPat(Vector(1,2,3),Vector(4,5,6))

def isPrime(n:Int):Boolean={
  (2 to n/2) forall (i => n%i==0)
}

isPrime(10)

