package simulator

abstract class Circuits extends Gates{
  def halfAdder(a:Wire,b:Wire,s:Wire,c:Wire):Unit={
    val d,e=new Wire
    orGate2(a,b,d)
    andGate(a,b,c)
    inverter(c,e)
    andGate(d,e,s)
  }

  def fullAdder(a:Wire,b:Wire,cin:Wire,sum:Wire,cout:Wire):Unit={
    val s,c1,c2=new Wire
    halfAdder(b,cin,s,c1)
    halfAdder(a,s,sum,cout)
    orGate2(c1,c2,cout)
  }
}
