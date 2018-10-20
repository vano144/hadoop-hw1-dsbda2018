#Hadoop Longest words

It is app, which finds the longest word[s] in file[s]. 

## Minimal system requirements
* Java 8
* Apache Maven 4.0.0
* Hadoop 3.1.1

## How to start

* Create jar

  ```bash build.sh```

* Prepare data

  ```bash data_setup.sh```
  
It will copy date from `./input` to hdfs `/input-data/input`
and create into hdfs `/result/` for results. Apart from it, folders `/input-data/input` and `/result/` will be erased. 
  
* Run it

  ```bash run.sh```
  
## How to generate more files?

Before start, you shoud run next:

  ```python3 generate_file.py``` 
  
It will generate random one file in `./input `

