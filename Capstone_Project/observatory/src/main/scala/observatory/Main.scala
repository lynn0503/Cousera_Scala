package observatory


object Main extends App {

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

//  val colors=List(
//    (60.0,Color(255,255,255)),
//    (32.0,Color(255,0,0)),
//    (12.0,Color(255,255,0)),
//    (0.0,Color(0,255,255)),
//    (-15.0,Color(0,0,255)),
//    (-27.0,Color(255,0,255)),
//    (-50.0,Color(33,0,107)),
//    (60.0,Color(0,0,0))
//  )
  val data=for (year<- 1975 to 1976) yield {
    val records=Extraction.locateTemperatures(year,"/stations.csv",s"/$year.csv")
    val records_avg=Extraction.locationYearlyAverageRecords(records)
    (year,records_avg)
  }
  Interaction.generateTiles(data,Interaction.generateImage)

//  val img=Visualization.visualize(records_avg,colors)
//  img.output(new java.io.File("some-image.png"))




}
