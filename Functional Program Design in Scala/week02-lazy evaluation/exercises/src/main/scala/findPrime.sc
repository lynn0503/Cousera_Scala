def isPrime(n:Int):Boolean={
  (2 to n/2) forall (i=>n%i!=0)
}

((1000 to 10000) filter isPrime)(0)

//improve performance
((1000 to 10000).toStream filter isPrime)

//stream constructor
//tl:=>Stream[T] pass by name
object Stream{
  def cons[T](hd:T,tl:=>Stream[T]) = new Stream[T]{
    def isEmpty=false
    def head=hd
    def tail=tl
  }
//  or more efficient way
//  lazy val avoids multiple recomputed
def cons[T](hd:T,tl:=>Stream[T]) = new Stream[T]{
    def isEmpty=false
    def head=hd
    lazy val tail=tl
  }
}

class Stream[+T]{
  def filter(p:T=>Boolean):Stream[T]=
    if (isEmpty) this
    else if (p(head)) cons(head,tail.filter(p))
    else tail.filter(p)
}


//list constructor
//tl:List[T] pass by value
