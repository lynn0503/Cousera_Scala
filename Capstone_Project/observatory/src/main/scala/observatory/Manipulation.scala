package observatory

/**
  * 4th milestone: value-added information
  */
object Manipulation {

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a latitude in [-89, 90] and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Temperature)]): GridLocation => Temperature = {
    val memo=scala.collection.mutable.Map[GridLocation,Temperature]()
    def gloc2tpr(gloc:GridLocation):Temperature={
      if (memo.contains(gloc)) memo.apply(gloc)
      else {
        val tpr = Visualization.predictTemperature(temperatures,Location(gloc.lat.toDouble,gloc.lon.toDouble))
        memo+=((gloc,tpr))
        tpr
    }}
    gloc2tpr
  }

  /**
    * @param temperaturess Sequence of known temperatures over the years (each element of the collection
    *                      is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude, returns the average temperature at this location
    */
  def average(temperaturess: Iterable[Iterable[(Location, Temperature)]]): GridLocation => Temperature = {
    def gloc2avg(gloc:GridLocation):Temperature={
      val tpr=temperaturess.map(t=>makeGrid(t)(gloc))
      tpr.sum/tpr.size
    }
    gloc2avg
  }

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing the deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Temperature)], normals: GridLocation => Temperature): GridLocation => Temperature = {
    def gloc2dev(gloc:GridLocation):Temperature={
      val tpr=makeGrid(temperatures)(gloc)
      val avg=normals(gloc)
      tpr-avg
    }
    gloc2dev
  }


}

