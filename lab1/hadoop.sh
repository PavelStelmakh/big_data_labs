hdfs dfsadmin -safemode leave
hdfs dfs -rm output/*
hdfs dfs -rmdir output
hdfs dfs -rm input/*
hdfs dfs -rmdir input
hadoop fs -mkdir -p input
hdfs dfs -put 000000 input
hadoop jar lab1-1.0-SNAPSHOT-jar-with-dependencies.jar input output3
hdfs dfs -cat output3/part-r-00000