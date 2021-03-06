import math.Ordering

def mgSort[T](m:List[T])(ord:Ordering[T]):List[T]= {
  val len:Int=m.length
  if (len<2) m
  else {
    def merge(a:List[T],b:List[T]):List[T]= (a,b) match{
      case (Nil,b) => b
      case (a,Nil)=> a
      case (ai::as,bi::bs)=>if (ord.lt(ai,bi)) ai::merge(as,b) else bi::merge(a,bs)
    }
    val (h1,h2)=m.splitAt(len/2)
    merge(mgSort[T](h1)(ord),mgSort[T](h2)(ord))
  }
}

//mgSort(List(3,2,9,1,18,4))(Ordering.Int)
mgSort(List("a","c","b","f","d"))(Ordering.String)