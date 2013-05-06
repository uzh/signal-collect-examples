package com.signalcollect.examples

import com.signalcollect._
import java.io.FileInputStream
import akka.event.Logging
import com.signalcollect.configuration.ExecutionMode
import scala.io.Source

object RankerPatents extends App {

  // this example will (probably) provoke an OutOfMemoryException as we load a huge dataset
  
  val graph = GraphBuilder.withConsole(true)
                          .withLoggingLevel(Logging.InfoLevel)
                          .build
                          
  val execConfig = ExecutionConfiguration.withExecutionMode(ExecutionMode.Interactive)

  println("loading graph")

  loadGraph

  println("executing")

  val stats = graph.execute(execConfig)

  graph.shutdown

  def loadGraph {
    for (line <- Source.fromFile("./dataset/Cit-Patents.txt").getLines()) {
      if (!line.startsWith("#")) {

        // split and trim values
        var citation = line.split("\\s+");
        var citer = citation(0).trim()
        var cited = citation(1).trim()

        // build graph
        graph.addVertex(new Publication(citer))
        graph.addVertex(new Publication(cited))
        graph.addEdge(citer, new Citation(cited))
      }
    }
  }
}
