package observatory

import java.nio.file.Paths
import java.time.LocalDate

import observatory.LayerName.Temperatures
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}


/**
  * 1st milestone: data extraction
  */
object Extraction {

  import org.apache.spark.sql.SparkSession
  import org.apache.spark.sql.functions._

  val spark: SparkSession =
    SparkSession
      .builder()
      .appName("observatory")
      .config("spark.master", "local")
      .getOrCreate()

  def fsPath(resource: String): String =
    Paths.get(getClass.getResource(resource).toURI).toString

  def read_stn(stnPath: String):DataFrame={
    val rdd_stn = spark.sparkContext.textFile(fsPath(stnPath))
    val data=rdd_stn.map(_.split(",").to[List]).filter(_.size==4).filter(line=>
      line.applyOrElse(2," ")!=" " && line.applyOrElse(3," ")!=" " &&
        !(line.head.isEmpty && line.apply(1).isEmpty)
    ).map(line=>{
      val List(stn,wban,lat,lon)=line
      Row(stn,wban,lat.toDouble,lon.toDouble)
    })
    val schema=StructType(List(
      StructField("STN",StringType,true),
      StructField("WBAN",StringType,true),
      StructField("LAT",DoubleType,false),
      StructField("LONG",DoubleType,false)
    ))
    spark.createDataFrame(data, schema)
  }

  def read_tpr(tprPath: String):DataFrame={
    val rdd_tpr= spark.sparkContext.textFile(fsPath(tprPath))
    val data=rdd_tpr.map(line=> {
      val Array(stn, wban, mm, dd, tpr) = line.split(",")
      Row(stn,wban,mm.toInt,dd.toInt,tpr.toDouble)
    })

    val schema=StructType(List(
      StructField("STN",StringType,true),
      StructField("WBAN",StringType,true),
      StructField("MM",IntegerType,true),
      StructField("DD",IntegerType,true),
      StructField("TEMPF",DoubleType,false)
    ))
    spark.createDataFrame(data, schema)
  }

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] ={
    val df_stn=read_stn(stationsFile)
    val df_tpr=read_tpr(temperaturesFile)
    val df_joined=df_stn.join(df_tpr,df_stn("STN") <=> df_tpr("STN") && df_stn("WBAN") <=> df_tpr("WBAN"))
    val df=df_joined.withColumn("TEMPC",expr("5/9*(TEMPF-32)"))

    df.select("MM","DD","LAT","LONG","TEMPC").collect().toList.map(row=>
      (LocalDate.of(year,row.getAs[Int]("MM"),row.getAs[Int]("DD")),
      Location(row.getAs[Double]("LAT"),row.getAs[Double]("LONG")),
      row.getAs[Double]("TEMPC"))
    )

  }


  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    def avg(iterable: Iterable[Double]):Double={
      iterable.sum/iterable.size
    }
    records.groupBy(_._2).map{
      case (k,v)=> (k,
        v.groupBy(_._1.getYear).map{
          case (y,t) => avg(t.map(_._3).toList)}
      )}.flatMap(p=>p._2.map(ele=>(p._1,ele)))
  }

}
