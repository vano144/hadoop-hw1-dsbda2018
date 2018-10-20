hadoop jar ./target/HW1-1.0-SNAPSHOT.jar LongestWords /input-data /result
echo "Now results..."
hadoop dfs -text '/result/part-r-*'
