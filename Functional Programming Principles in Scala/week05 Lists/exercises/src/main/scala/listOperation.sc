def removeAt(xs:List[Int],n:Int):List[Int]=xs match{
  case List()=>throw new Error("remove from empty list")
  case y::ys=>
    if (xs.length<n) throw new Error("index out of bounds")
    else xs.take(n)++ xs.drop(n+1)
}

def flatten(xs:List[Any]):List[Any]=xs match{
  case List()=>List()
  case (i:List[Any])::is => flatten(i) ++ flatten(is)
  case i::is=>i::flatten(is)
}

flatten(List(1,List(0,2),3,4))

List(12,3,45,6,7,8).splitAt(3)