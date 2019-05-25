import scala.util.Try

trait M[T]{
  def flatMap[U](f:T=>M[U]):M[U]
}

def unit[T](x:T):M[T]

Try(1+2)