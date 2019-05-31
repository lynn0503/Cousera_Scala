import frp.{BankAccountSignal, Signal, Var}

def consolidatted(accts:List[BankAccountSignal]):Signal[Int]=
  Signal(accts.map(_.balance()).sum)

val a,b=new BankAccountSignal()
val c=consolidatted(List(a,b))

c()
a.deposit(20)
c()
b.deposit(30)
c()
val xchange=Signal(246.00)
val inDollar=Signal(c() * xchange())
inDollar()
b.withdraw(10)
inDollar()

val num=Var(1)
val twice=Signal(num()*2)
num()=2
twice()

var num1=Var(1)
var twice1=Signal(num1()*2)
num1=Var(2)
//num1 now is a new variable
//it will not affect the value of twice1
twice1()


