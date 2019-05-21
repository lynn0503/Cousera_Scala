def merge(a:List[Int],b:List[Int]):List[Int]= a match{
  case Nil=>b
  case ai::as=> b match{
    case Nil=>a
    case bi::bs=>{
      if (ai<=bi) ai::merge(as,b)
      else bi::merge(a,bs)
    }
  }
}

def mergePairPattern(a:List[Int],b:List[Int]):List[Int]= {
  (a,b) match{
    case (Nil,b) => b
    case (a,Nil)=> a
    case (ai::as,bi::bs)=>if (ai<=bi) ai::mergePairPattern(as,b) else bi::mergePairPattern(a,bs)
  }
}
def mgSort(m:List[Int]):List[Int]= {
  val len:Int=m.length
  if (len<2) m
  else mergePairPattern(mgSort(m.splitAt(len/2)._1),mgSort(m.splitAt(len/2)._2))
}


mgSort(List(3,2,9,1,18,4))