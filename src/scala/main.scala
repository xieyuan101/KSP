package scala

import java.util
import org.apache.spark._
import scala.collection.JavaConverters._
import scala.collection.mutable.Map
import com.ksp.kspUtil.EppsteinUtil

object Distribution{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Distribution")
    val sc = new SparkContext(conf)
    val rdd = sc.makeRDD(List("一品天下-2_7 天府广场-4_1"))
    val rdd1= rdd.map(String=>odDistributionResult(String)).collect()
    val rdd2=rdd1.reduce((x,y) => x ++ y)
    val map=rdd2.head
  }
  def distribution(map:Map[Iterator[String],Double],x:Int): Map[Iterator[String],Double] = {
    val e=Math.E
    val Q= -3.2
    var p=0.0
    var fenMu=0.0
    val probability_Passenger= new Array[Double](10)
    val costMin=map.values.min
    var kspMap=scala.collection.mutable.Map[Iterator[String],Double]()
    for(value <- map.values){//分配概率
      fenMu = fenMu + Math.pow(e,(Q*value / costMin))
    }
    var count =0
    for(value <- map.values){
      p = Math.pow(e, (Q * value / costMin)) / fenMu
      val kspPassenger = x.asInstanceOf[Double] * p//计算人数
      probability_Passenger(count)= kspPassenger
    }
    val keys = map.keySet
    var count1=0
    for(key <- keys){
      kspMap += (key -> probability_Passenger(count1))
    }
    return kspMap
//    kspMap.keys.foreach{ i =>
//      print( "Key = " + i )
//      println(" Value = " + kspMap(i) )}
  }

  def odDistributionResult(str:String): Map[Iterator[String],Double] ={
    // val ksp = EppsteinUtil.getOneODPair("data/cd.txt", "一品天下-2_7", "天府广场-4_1", 2)
    var str1=str.split(" ")
    val sou = str1.head
    val tar = str1.tail.head
    val ksp = EppsteinUtil.getOneODPair("data/cd.txt", sou, tar, 2)
    val iter = ksp.iterator()
    val passenger = 1000 //OD对的总人数，暂为所有OD设置为1000
    var text:Map[Iterator[String],Double] = Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getNodes.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)
//      println(text.toList)
    }
    return distribution(text,passenger)
  }
}


//object TestMain {
//  def main(args: Array[String]): Unit = {
//    val ksp = EppsteinUtil.getOneODPair("data/cd.txt", "一品天下-2_7", "天府广场-4_1", 2)
//    println(ksp.getClass)
//    val iter = ksp.iterator()
//    while(iter.hasNext){
//      val p = iter.next()
//    一条路径的站点构成
//      val nodesIter = p.getNodes.iterator()
//      println("费用:"  + p.getTotalCost)
//      while(nodesIter.hasNext){
//        val node = nodesIter.next()
//        println(node)
//      }
//      println("===========================================")
//    }
//  }
//}

//def main(args: Array[String]): Unit = {
//  //val passenger = scala.util.Random.nextInt(1000)//生成随机数
//  //暂没考虑以换乘次数+1作为要求搜索的路径条数
//  val ksp = EppsteinUtil.getOneODPair("data/cd.txt", "一品天下-2_7", "天府广场-4_1", 2)
//  println(ksp.getClass)
//  val iter = ksp.iterator()
//  val passenger = 1000 //OD对的总人数，暂为所有OD设置为1000
//  var text:Map[Iterator[String],Double] = Map()
//  while(iter.hasNext) {
//  val p = iter.next()
//  //      一条路径的站点构成
//  val nodesIter = p.getNodes.iterator()
//  //      println("费用:"  + p.getTotalCost)
//  text += (nodesIter.asScala -> p.getTotalCost)
//  println(text.toList)
//}
//  distribution(text,passenger)
//}