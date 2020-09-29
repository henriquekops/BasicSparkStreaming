spark-submit \
  --driver-java-options \
  "-Dlog4j.configuration=file:$(pwd)/log4j-driver.properties" \
  target/scala-2.11/spark_2.11-0.1.jar