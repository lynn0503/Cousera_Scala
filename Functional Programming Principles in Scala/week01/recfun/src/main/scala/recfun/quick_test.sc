def countChange(money: Int, coins: List[Int]): Int = {
  if (money==0) 1
  else if (money>0 && coins.nonEmpty)
    countChange(money-coins.head,coins)+countChange(money,coins.tail)
  else 0

}

countChange(6,List(1,2))