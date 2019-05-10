
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class sumTest extends FunSuite {
  import sum.sumInts
  import sum.sumCube
  import sum.sumFact
  test(testName = "sum ints"){
    assert(sumInts(1,100)==5050)
  }
  test(testName = "sum cube"){
    assert(sumCube(1,5)==225)
  }
  test(testName = "sum factorial"){
    assert(sumFact(1,3)==9)
  }

  test(testName = "sum factorial including 0"){
    assert(sumFact(0,3)==10)
  }


}
