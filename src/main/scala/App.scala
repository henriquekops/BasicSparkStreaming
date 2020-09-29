import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App {


  def main(args: Array[String]): Unit = {

    /*
    > Create a local Streaming context with two working threads
    > Use a batch interval of 5 seconds
    > Master requires 2 cores to prevent starvation
     */

    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))

    /*
    > Create a DStream from TCP source that will connect to hostname:port
    > 'lines' is a stream of received data
    > Each record is a line of text
     */
    val lines = ssc.socketTextStream("127.0.0.1", 9999)

    /*
    > New DStream 'words' is originated by 'flatMap' (one-to-many operation)
    > It means that each record in 'lines' will generate a new line trough 'split' operation
    > Then every new line is combined to form newest DStream
     */
    val words = lines.flatMap(_.split(" "))

    /*
    > Count each 'word' batch using one-to-one operation 'map'
    > Generates (w, 1) pairs, where 'w' is contained in 'words'
    > Generates new key-value DStream
     */
    val pairs = words.map(w => (w, 1))

    /*
    > Each pair is then reduced by key
    > Sum is the merge operator for every reduced key
    > Generates a new RDD with merged keys and summed values
     */
    val wordCounts = pairs.reduceByKey(_ + _)

    /*
    > Prints first ten elements of returned RDD
     */
    wordCounts.print()

    /*
    > After setting up the computation, start the processing
    > Wait the computation to terminate
     */
    ssc.start()
    ssc.awaitTermination()
  }
}
