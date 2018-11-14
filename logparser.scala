package com.test.scala

import scala.io.Source
import java.io.{ FileNotFoundException, IOException }

case class log(ip: String, date: String,hour: Int, min: Int, sec: Int, offset: String, methodType: String, uri: String, protocol: String, response: Int,port: String, url:String, content:String);

object ReadLogFile {

  val filename = "apache-web.log"
  val logRegex = """^(\S+) - - \[(\S+):(\S+):(\S+):(\S+) \+(\S+)] \"(\S+) (\S+) (\S+)\" (\S+) (\S+) \"(\S+)\" \"(.*)\"""".r; 
  
  private var current=0;
 

   
  def parseLog(line: String): log = {
    val logRegex(ip, date,hour, min, sec, offset, methodType, uri,protocol, response,port,url,content ) = line;
    //println(">>>> "+ip+" "+date+"  "+hour+"  "+min+" "+sec+"  "+offset+"  "+methodType+"  "+uri+"  "+protocol+"  "+response+"  "+port+" "+url+" "+content)
    return log(ip, date, hour.toInt, min.toInt, sec.toInt,offset, methodType, uri, protocol, response.toInt, port,url,content);
  }

  def assertInt(variable: String): Int = {
    if (variable.forall(_.isDigit)) {
      return variable.toInt
    } else {
      return 0;
    }
  }

  def main(args: Array[String]): Unit = {

    var urls=
    try {

      /code/
      
     
      
       for (l <- Source.fromFile(filename).getLines) {
         if(l.matches(logRegex.toString)){
           //Printing few of the attributes from the log file
          //println(parseLog(l).methodType + " HTTP method was called for the URL : "+parseLog(l).url+" at "+parseLog(l).hour+":"+parseLog(l).min+":"+parseLog(l).sec+" on "+parseLog(l).date )
        
           var a = 1
            a: Int  
       
           
            
            //hits max url access
        
            //sort url
            val urls=parseLog(l).url;
            l.sortBy(f=>urls)
            
            println(parseLog(l).methodType + " HTTP method was called for the URL : "+parseLog(l).url+" at "+parseLog(l).hour+":"+parseLog(l).min+":"+parseLog(l).sec+" on "+parseLog(l).date )
         
          val data=urls match {
            case "http://localhost/photos/1"=>{ current=0;current *=2}
            case "http://localhost/" => { current=0;current *=3}
            case "http://localhost/account/login"=> { current=0;current *=5}
            case "http://localhost/setup"=> { current=0;current *=7}
            case default => {}
            
            }
           
           
         }else{
           println("No match found for :"+l);
         }
         
      }
      
    if(current%2==0)
      println("http://localhost/photos/1 has highest match");
    else if(current%3==0)
       println("http://localhost/ has highest match");

    else if(current%5==0)
       println("http://localhost/account/login has highest match");
    else if(current%7==0)
       println("http://localhost/setup has highest match");
    
    } catch {
      case e: FileNotFoundException => println("Couldn't find that file.")
      case e: IOException           => println("Got an IOException!")
    }

  }

}