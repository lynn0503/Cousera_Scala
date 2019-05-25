val f:PartialFunction[String,String]={case "ping"=>"pong"}
f("ping")
f.isDefinedAt("pong")

//how scala compiler expand {case "ping"=>"pong"}
//new PartialFunction[String,String] {
//  def apply(x:String)=x match{
//    case "ping"=>"pong"
//  }
//   def isDefinedAt(x: String): Boolean = x match{
//    case "ping"=>true
//    case _=>false
//  }
//}

val g:PartialFunction[List[Int],String]={
  case Nil=>"one"
  case x::rest=>rest match{
    case Nil=>"two"
//    case _=>"three"
  }
}
//only match the outermost matching block
g.isDefinedAt(List(1,2,3))
g(List(1,2,3))

