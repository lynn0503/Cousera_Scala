package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(b()*b()-4*a()*c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    val sqrt=Signal(math.sqrt(delta()))
    val neB=Signal(-1*b())
    val twA=Signal(2*a())
    Signal{
      if (delta()>=0) Set((neB()+sqrt())/twA(),(neB()-sqrt())/twA())
      else Set()
    }
  }
}
