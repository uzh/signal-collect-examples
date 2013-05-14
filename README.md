Signal/Collect Examples
=======================

**This repository provides code examples to show what [Signal/Collect](https://github.com/uzh/signal-collect) is capable of.**

## How to Compile the Project

First you have to [obtain Signal/Collect and create a .jar file](https://github.com/uzh/signal-collect#how-to-compile-the-project).

Then clone the signal-collect-examples repository into an empty directory: 

```
git clone git://github.com/uzh/signal-collect-examples.git .
```

Now, move the generated .jar file into the same directory. To compile and start an example, use the following command:

```
scalac -cp .:*.jar src/main/scala/com/signalcollect/examples/Ranker.scala; scala  -cp .:*.jar com.signalcollect.examples.Ranker
```

You can run each example in isolation. The shell command above will compile and launch the "Ranker" example. To launch another example, change "Ranker" to the name of the file you wish to run.


## Examples and Data Sets

We cannot include all required datasets into this repository as they would be too large to manage here. Instead we provide download links when needed. All data sets should be stored in the "dataset" directory.

* Ranker 
* RankerPatents: <http://snap.stanford.edu/data/cit-Patents.txt.gz>
* TransitiveClosure: <http://snap.stanford.edu/data/cit-HepPh.txt.gz>

## Requirements
	
* Java 7 or above
* Scala 2.10.1 or above