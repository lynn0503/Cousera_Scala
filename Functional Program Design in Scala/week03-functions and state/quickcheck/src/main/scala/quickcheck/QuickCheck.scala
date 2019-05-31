package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._


abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for{
    el <- arbitrary[A]
    hp <- oneOf(const(empty),genHeap)
  }yield insert(el,hp)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min of two elements")=forAll{(e1:A,e2:A)=>
    findMin(insert(e2,insert(e1,empty)))== (if (e1<=e2) e1 else e2)
  }

  property("empty heap")= forAll{(ele:A)=>
    deleteMin(insert(ele,empty))==empty
  }

  property("sort sequence")=forAll{(h:H)=>
    def helper(acc:List[A],hp:H):List[A]={
      if (isEmpty(hp)) acc
      else helper(acc :+ findMin(hp),deleteMin(hp))
    }
    helper(List(),h)== helper(List(),h).sorted
  }

  property("min of two heaps")=forAll{(h1:H,h2:H)=>
    (findMin(meld(h1,h2))== findMin(h1)) || (findMin(meld(h1,h2))==findMin(h2))
  }

  property("associative meld")=forAll{(h1:H,h2:H,h3:H)=>
    def helper(acc:List[A],hp:H):List[A]={
      if (isEmpty(hp)) acc
      else helper(acc :+ findMin(hp),deleteMin(hp))
    }
    helper(List(),meld(h1,meld(h2,h3)))==helper(List(),meld(h2,meld(h1,h3)))
  }

}
