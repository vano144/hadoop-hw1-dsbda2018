python3 generate_file.py
hdfs dfs -mkdir /input-data
hdfs dfs -mkdir /result
hadoop fs -rm /input-data/*
hadoop fs -rm /result/*
hdfs dfs -put input/* /input-data
hdfs dfs -ls /input-data

