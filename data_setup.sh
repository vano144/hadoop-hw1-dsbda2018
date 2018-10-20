#python3 generate_file.py
hdfs dfs -mkdir /input-data
hadoop fs -rm /input-data/*
hadoop fs -rm -r /result
hdfs dfs -put input/* /input-data
hdfs dfs -ls /input-data

