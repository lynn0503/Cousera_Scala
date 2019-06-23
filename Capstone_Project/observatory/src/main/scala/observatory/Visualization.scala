package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import scala.math._


/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    val phix=location.lat*Pi/180
    val lamx=location.lon*Pi/180
    val weights=temperatures.map(i=>{
      val phi=i._1.lat*Pi/180
      val lam=i._1.lon*Pi/180
      val sigma={if (lamx==lam && phix==phi) 0
        else if (abs(lam)==abs(lamx) && abs(phi)+abs(phix)==180) Pi
        else acos(sin(phi)*sin(phix)+cos(phi)*cos(phix)*cos(abs(lam-lamx)))
      }
      val distance=6371*sigma
      val weight=1/pow(distance,6)
      (weight,i._2,distance)
    })
    if (weights.exists(_._3<1)) weights.minBy(_._3)._2
    else{
      weights.map(w=>w._1*w._2).sum/weights.map(_._1).sum
    }
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    val pmax=points.maxBy(_._1)
    val pmin=points.minBy(_._1)
    val List(ps,pl)=points.map(p=>(abs(p._1-value),p._1,p._2)).toList.sortBy(_._1).take(2).sortBy(_._2)
    if (value>=pmax._1) pmax._2 else if (value<=pmin._1) pmin._2 else if (ps._1==0) ps._3
    else{
      val r=(value-ps._2)/(pl._2-ps._2)*(pl._3.red-ps._3.red)+ps._3.red
      val g=(value-ps._2)/(pl._2-ps._2)*(pl._3.green-ps._3.green)+ps._3.green
      val b= (value-ps._2)/(pl._2-ps._2)*(pl._3.blue-ps._3.blue)+ps._3.blue
      Color(r.toInt,g.toInt,b.toInt)
    }
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {

    val points=for(lon<- -180 to 179; lat<- -89 to 90) yield Location(lat,lon)
    val all_tpr=points.map(p=> (p,predictTemperature(temperatures,p)))

    val array_pixel=all_tpr.toArray.sortBy(l=>(-l._1.lat,l._1.lon)).map(a=>{
      val c=interpolateColor(colors,a._2)
      Pixel.apply(c.red,c.green,c.blue,255)
    })
//    List(temperatures.size,unknown_loc.size,all_tpr.size)
    Image.apply(360,180,array_pixel)
  }

}

