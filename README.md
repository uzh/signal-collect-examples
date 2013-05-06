Signal/Collect Examples
=======================

**This repository provides code examples to show what [Signal/Collect](https://github.com/uzh/signal-collect) is capable of.**

You can run each example in isolation. The following shell commands will compile and launch the "Ranker" example. To launch another example, change "Ranker" to the name of the file you wish to run.

```
# Clone into current Directory
git clone git://github.com/uzh/signal-collect-examples.git .
# Compile and Run
scalac -cp .:*.jar src/main/scala/com/signalcollect/examples/Ranker.scala; scala  -cp .:*.jar com.signalcollect.examples.Ranker
```

## Examples and Data Sets

We cannot include all required datasets into this repository as they would be too large to manage here. Instead we provide download links when needed. All data sets should be stored in the "dataset" directory.

* Ranker 
* RankerPatents: <http://snap.stanford.edu/data/cit-Patents.txt.gz>

