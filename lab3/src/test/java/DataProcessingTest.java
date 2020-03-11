import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.junit.Test;
import scala.Tuple2;

import java.util.List;

import static org.junit.Assert.*;

public class DataProcessingTest {
    @Test
    public void getRequestsInfo() {
        String path = "input/sample.txt";
        DataProcessing dataProcessing = new DataProcessing(path);
        JavaRDD<DataInfo> requestsData = dataProcessing.getRequestsInfo();
        List<DataInfo> list = requestsData.filter(
                (Function<DataInfo, Boolean>) t -> {
                        return t.getId() == 34 || t.getId() == 98;
                }).collect();

        int listIp34 = list.get(0).getId() == 34 ? 0 : 1;
        int listIp98 = listIp34 == 0 ? 1 : 0;

        assertEquals(list.get(listIp34).getByteSum(), 0);
        assertEquals(list.get(listIp98).getByteSum(), 611);
    }

    @Test
    public void getBrowserInfo() {
        String path = "input/sample.txt";
        DataProcessing dataProcessing = new DataProcessing(path);
        JavaPairRDD<String, Integer> data = dataProcessing.getBrowserInfo();
        List<Tuple2<String, Integer>> list = data.filter(
                (Function<Tuple2<String, Integer>, Boolean>) t -> t._1().equals("Opera 11") || t._1().equals("Firefox 4") || t._1().equals("Safari 5"))
                .collect();

        for (Tuple2<String, Integer> i : list) {
            if (i._1().equals("Opera 11")) {
                assertEquals((long)i._2(), 20);
            }
            if (i._1().equals("Firefox 4")) {
                assertEquals((long)i._2(), 157);
            }
            if (i._1().equals("Safari 5")) {
                assertEquals((long)i._2(), 15);
            }
        }
    }
}