//insert function should be above insort function
//otherwise error not found

def insert(x:Int,xs:List[Int]):List[Int]=xs match{
  case List()=>List(x)
  case y::ys => if (x <= y) x::xs else y::insert(x,ys)
}

def insort(xs:List[Int]):List[Int]=xs match{
  case List()=>List()
  case y::ys=> insert(y,insort(ys))
}


insort(List(2,3,1,4,2,5,7,6))