import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

public class Main {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir","D:\\study\\hadoop\\winutils\\hadoop-3.0.0" );
        String path = "input/000000";

        DataProcessing dataProcessing = new DataProcessing(path);

        JavaRDD<DataInfo> requestsData = dataProcessing.getRequestsInfo();
        JavaPairRDD<String, Integer> countData = dataProcessing.getBrowserInfo();

        countData.foreach(data1 -> {
            System.out.println("Browser: "+ data1._1() + " count: " + data1._2());
        });

        try {
            requestsData.coalesce(1).saveAsTextFile("result");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
