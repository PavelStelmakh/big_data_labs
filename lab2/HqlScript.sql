CREATE TABLE IF NOT EXISTS byteCounter ( ip string, trash1 string,
trash2 string, trash3 string, trash4 string, trash5 string,
trash6 string, trash7 string, trash8 string, byte int) 
COMMENT 'Byte sum' 
ROW FORMAT DELIMITED FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' STORED 
AS TEXTFILE;
LOAD DATA LOCAL INPATH '/opt/hive/examples/sample.txt' OVERWRITE INTO TABLE byteCounter;
SELECT * FROM byteCounter;
CREATE TABLE IF NOT EXISTS Statistic (ip string, sumByte int, avgByte int);
INSERT INTO Statistic (ip, sumbyte, avgbyte)
SELECT ip, SUM(byte) as sumByte, AVG(byte) as avgByte
FROM byteCounter
GROUP BY ip;
SELECT * FROM Statistic;