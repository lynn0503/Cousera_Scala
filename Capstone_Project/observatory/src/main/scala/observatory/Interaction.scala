package observatory

import java.nio.file.Paths
import java.time.LocalDate

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Visualization.{interpolateColor, predictTemperature}

import math._
import scala.reflect.io.File

/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  val colors=List(
    (60.0,Color(255,255,255)),
    (32.0,Color(255,0,0)),
    (12.0,Color(255,255,0)),
    (0.0,Color(0,255,255)),
    (-15.0,Color(0,0,255)),
    (-27.0,Color(255,0,255)),
    (-50.0,Color(33,0,107)),
    (60.0,Color(0,0,0))
  )

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(tile: Tile): Location = {
    val total_tiles = pow(2, tile.zoom)
    val lon_deg = tile.x / total_tiles * 360 - 180
    val lat_deg = atan(sinh(Pi * (1 - 2 * tile.y / total_tiles))) * 180 / Pi
    Location(lat_deg,lon_deg)
  }
  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)], tile: Tile): Image = {
    val points= for (i<-0 until 256; j <- 0 until 256) yield tileLocation(Tile(tile.x*256+i,tile.y*256+j,tile.zoom+8))
    val all_tpr=points.map(p=> (p,predictTemperature(temperatures,p)))
    val array_pixel=all_tpr.toArray.sortBy(l=>(-l._1.lat,l._1.lon)).map(a=>{
      val c=interpolateColor(colors,a._2)
      Pixel.apply(c.red,c.green,c.blue,255)
    })
    Image.apply(256,256,array_pixel)
  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data associated with
    *                   `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */

  def generateTiles[Data](
    yearlyData: Iterable[(Year, Data)],
    generateImage: (Year, Tile, Data) => Unit
  ): Unit = {
    yearlyData.foreach(yd=>{
      for (m<- 0 to 3; x <- 0 until pow(2,m).toInt; y<- 0 until pow(2,m).toInt)
        yield generateImage(yd._1,Tile(x,y,m),yd._2)
    })
  }


  def generateImage(y:Year,t:Tile,d:Iterable[(Location, Temperature)]):Unit={
    val img=tile(d,colors,t)
    val folder=s"C:/Users/lynn0/Desktop/scala_cousera/github/Cousera_Scala/Capstone_Project/observatory/target/temperatures/$y/${t.zoom}"
    val filename=s"/${t.x}-${t.y}.png"
    val dir= new java.io.File(folder)
    if (!dir.exists()) dir.mkdirs()
    img.output(new java.io.File(folder,filename).getPath)
  }

}
