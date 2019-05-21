val data=List("a","a","b","b","a","c")

def pack[T](xs:List[T]):List[List[T]]=xs match{
  case Nil=>Nil
  case x::xi=>
    val (head,tail)= xs partition (y => y==x)
    head::pack(tail)
}

def encode[T](xs:List[List[T]]):List[(T,Int)]=xs match{
  case Nil=>Nil
  case x::xi=> (x.head,x.length)::encode(xi)
}

encode(pack(data))

def sum(x:List[Int]):Int={
  0::x reduceLeft(_+_)
}


def prod(y:List[Int]):Int={
  1::y reduceLeft(_*_)
}

def sumf(x:List[Int]):Int={
  (x foldLeft 0)(_+_)
}


def prodf(x:List[Int]):Int={
  (x foldLeft 1)(_+_)
}

def plus2(x:List[Int]):List[Int]={
  x map(xi=>xi+2)
}

//sum(List(2,1,3,5))
//sumf(List(2,1,3,5))
//prod(List(2,1,3,5))
//prodf(List(2,1,3,5))
//plus2(List(2,1,3,5))

def concatR[T](xs:List[T],ys:List[T]):List[T]={
  (xs foldRight ys)(_::_)
}
//foldLeft here is not valid
//since :: can not operated on arbitrary element
//val list1=1::List(2,3)-------√
//val list2=List(2,3)::1-------×

concatR(List(2,1,3,5),List("a","b","c"))

def reverse[T](xs:List[T]):List[T]={
  (xs foldLeft List[T]())((xs,x)=>x::xs)
}

reverse(List(2,1,3,5))