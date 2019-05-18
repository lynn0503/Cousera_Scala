//if put eval and show in trait Expr
//then error not found: Num,Sum???why


trait Expr{
  def eval: Int = this match {
    case Num(n)       => n
    case Sum(e1, e2)  => e1.eval + e2.eval
    case Prod(e1, e2) => e1.eval * e2.eval
  }

  def show: String = this match {
    case Num(n)       => n.toString
    case Var(x)       => x
    case Sum(e1, e2)  => e1.show + "+" + e2.show
    case Prod(e1, e2) => e1.show + "*" + e2.show
  }
}


case class Num(n: Int) extends Expr
case class Var(x: String) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr



//def eval(e:Expr): Int = e match {
//  case Num(n)       => n
//  case Sum(e1, e2)  => eval(e1) + eval(e2)
//  case Prod(e1, e2) => eval(e1) * eval(e2)
//}
//
//def show(e:Expr): String = e match {
//  case Num(n)       => n.toString
//  case Var(x)       => x
//  case Sum(e1, e2)  => show(e1) + "+" + show(e2)
//  case Prod(e1, e2) => show(e1) + "*" + show(e2)
//}



Var("x").show
Num(1).show
Sum(Num(1),Num(2)).show
