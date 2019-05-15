import week4._
//put only runtime code in worksheet
def singleton[T](elem:T)=new Cons[T](elem, new Nil[T])
val single_int=singleton[Int](1)
val single_bool=singleton[Boolean](true)

def nth[T](n:Int, xs:List[T]):T={
  if (xs.isEmpty) throw new IndexOutOfBoundsException
  else if (n==0) xs.head
  else nth(n-1,xs.tail)
}

nth(0,single_int)
nth(0,single_bool)

val list=new Cons(1,new Cons(2,new Cons(3,new Nil)))
nth(2,list)
nth(-1,list)





