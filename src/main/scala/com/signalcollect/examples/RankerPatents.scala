/*
 *  @author Silvan Troxler
 *  
 *  Copyright 2013 University of Zurich
 *      
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *         http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */

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
    
    var i = 0;
    for (line <- Source.fromFile("./dataset/Cit-Patents.txt").getLines()) {
      if (!line.startsWith("#")) {

        if (i % 100000 == 0) {
          println("Adding Edge #" + (i))
        }
        
        // split and trim values
        var citation = line.split("\\s+");
        var citer = citation(0).trim()
        var cited = citation(1).trim()

        // build graph
        graph.addVertex(new Publication(citer))
        graph.addVertex(new Publication(cited))
        graph.addEdge(citer, new Citation(cited))
        
        i += 1;
      }
    }
  }
}
