package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Interaction.tileLocation

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 {

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, using bilinear interpolation
    *         See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
    point: CellPoint,
    d00: Temperature,
    d01: Temperature,
    d10: Temperature,
    d11: Temperature
  ): Temperature = {
    d00*(1-point.x)*(1-point.y)+d10*point.x*(1-point.y)+d01*point.y*(1-point.x)+d11*point.x*point.y
  }

  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param tile Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the given color scale
    */
  def visualizeGrid(
    grid: GridLocation => Temperature,
    colors: Iterable[(Temperature, Color)],
    tile: Tile
  ): Image = {
    val points= for (i<-0 until 256; j <- 0 until 256) yield tileLocation(Tile(tile.x*256+i,tile.y*256+j,tile.zoom+8))
    val loc_colors=points.map(p=> {
      val y_ceil=math.ceil(p.lat).toInt
      val x_floor=math.floor(p.lon).toInt
      val pt_xy=CellPoint(p.lon-x_floor,y_ceil-p.lat)
      val d00=grid(GridLocation(x_floor,y_ceil))
      val d10=grid(GridLocation(x_floor+1,y_ceil))
      val d01=grid(GridLocation(x_floor,y_ceil-1))
      val d11=grid(GridLocation(x_floor+1,y_ceil-1))
      (p.lat,p.lon,bilinearInterpolation(pt_xy,d00,d01,d10,d11))
    }).map(p=>(p._1,p._2,Visualization.interpolateColor(colors,p._3)))
    val pixels=loc_colors.sortBy(lc=>(-lc._1,lc._2)).map(lc=>Pixel.apply(lc._3.red,lc._3.green,lc._3.blue,255)).toArray
    Image.apply(256,256,pixels)
  }

}
