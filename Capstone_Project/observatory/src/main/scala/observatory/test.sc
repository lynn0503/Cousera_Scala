import scala.math.pow
//val a=List((19,1,"q"),(19,2,"w"),(20,2,"e"),(20,1,"w"))
//a.groupBy(_._1)
//a.map(_._1).toList
//
//val b=Map((1,(1,2,3)),(2,(4,5,6)))
//b.map(
//  case (k,v)=>
//)

//val arr="a,b,,c,d".split(",").toList
//arr.apply(2)
//"1,2,,4,5,,,8".split(",").to[List]
//
//List(1,2,3,4).zipWithIndex.min
//val a=for (x<- 1 to 3;y<- 4 to 6) yield (x,y)

//List(" "," ","a","b").apply(1)

//val delta=90.0
//val b=1.0 until (1.0+delta) by (delta/128)
//b.size
val a=1.0 to 10.0 by 9/9.0
a.size
//for (m<- 0 to 3; x <- 0 until pow(2,m).toInt; y<- 0 until pow(2,m).toInt) yield (x,y,m)


List((1,1),(1,3),(2,4),(2,10)).groupBy(_._1)