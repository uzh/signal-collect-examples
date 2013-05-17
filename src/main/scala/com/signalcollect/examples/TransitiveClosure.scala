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
import scala.collection.mutable.Set
import scala.io.Source
import com.signalcollect.nodeprovisioning.torque.TorqueNodeProvisioner
import com.signalcollect.nodeprovisioning.torque.TorqueHost
import com.signalcollect.nodeprovisioning.torque.TorqueJobSubmitter
import com.signalcollect.nodeprovisioning.torque.TorquePriority
import com.signalcollect.configuration.ExecutionMode

/**
 * SubType connection from one Type to another Type
 */
class SubType(t: Any) extends OnlySignalOnChangeEdge(t) {
  type Source = Type

  /**
   * Signaling the current state of a Type combined with its own ID
   */
  def signal = {
    source.state + (source.id.toString.toInt)
  }
}

/**
 * Type which is based on a DataFlowVertex
 */
class Type(vertexId: Any, initialState: Set[Int] = Set()) extends DataFlowVertex(vertexId, initialState) {
  type Signal = Set[Int]

  /**
   * Combination of the current state of a vertex and the collected signal
   */
  def collect(signal: Set[Int]): Set[Int] = {
    state ++ (signal)
  }

}

/**
 * Builds a tree consisting of several Types and SubType-connections, then executes the computation on that graph
 */
object TransitiveClosure extends App {

  val dataFileName = "Cit-HepPh.txt"
  val dataFilePath = "dataset/" + dataFileName

  println("Building graph...")
  val graph = GraphBuilder
    .withConsole(true)
    .build

  var i = 1
  for (line <- Source.fromFile(dataFilePath).getLines()) {
    if (!line.startsWith("#") && i <= 500) { // limit number of edges to load

      // split and trim values
      var citation = line.split("\\s+");
      citation(0) = citation(0).trim()
      citation(1) = citation(1).trim()

      // build graph
      graph.addVertex(new Type(citation(1)))
      graph.addVertex(new Type(citation(0)))
      graph.addEdge(citation(0), new SubType(citation(1)))

      if (i % 1000 == 0) {
        println("Node i=" + i)
      }
      i += 1
    }
  }

  println("Graph: awaitIdle...")
  graph.awaitIdle

  println("Starting computation...")
  val stats = graph.execute(ExecutionConfiguration.withExecutionMode(ExecutionMode.Interactive))
//  println(stats)
//  graph.foreachVertex(println(_))
  graph.shutdown
}
