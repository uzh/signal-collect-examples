package com.signalcollect.examples

import com.signalcollect._
import java.io.FileInputStream
import org.semanticweb.yars.nx.parser.NxParser
import akka.event.Logging
import com.signalcollect.configuration.ExecutionMode

class Publication(id: String, initialState: Double = 0.15)
   extends DataGraphVertex(id, initialState) {
 type Signal = Double
 def collect = {
   initialState + (1 - initialState) * signals.sum
 }
}

class Citation(targetId: String)
   extends DefaultEdge(targetId) {
 def signal = {
   source match {
     case p: Publication => p.state * weight / p.sumOfOutWeights
   }
 }
}

object Ranker extends App {

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
   val is = new FileInputStream("./dataset/references.nt")
   val parser = new NxParser(is)
   while (parser.hasNext) {
     val triple = parser.next
     val citer = triple(0).toString
     val cited = triple(2).toString
     graph.addVertex(new Publication(citer))
     graph.addVertex(new Publication(cited))
     graph.addEdge(citer, new Citation(cited))
   }

 }

}
