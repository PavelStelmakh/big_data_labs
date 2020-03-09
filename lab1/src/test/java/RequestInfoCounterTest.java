import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;
import requestInfo.MapRequestInfo;
import requestInfo.RequestInformation;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestInfoCounterTest {
    private MapDriver<LongWritable, Text, IntWritable, RequestInformation> mapDriver;

    @Before
    public void setUp() {
        MapRequestInfo mapper = new MapRequestInfo();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    @Test
    public void testMap() throws IOException {
        mapDriver.withInput(new LongWritable(1),
                new Text("ip1 - - [24/Apr/2011:04:06:01 -0400] \\\"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\\\" 200 40028 \\\"-\\\" \\\"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\\\"\\n"));
        mapDriver.withInput(new LongWritable(1),
                new Text("ip2 - - [24/Apr/2011:04:20:11 -0400] \\\"GET /sun_ss5/pdf.gif HTTP/1.1\\\" 200 390 \\\"http://host2/sun_ss5/\\\" \\\"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16\\\"\\n"));
        mapDriver.withInput(new LongWritable(1),
                new Text("ip1 - - [24/Apr/2011:04:10:19 -0400] \\\"GET /~strabal/grease/photo1/97-13.jpg HTTP/1.1\\\" 200 56928 \\\"-\\\" \\\"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\\\"\\n"));

        RequestInformation info1 = new RequestInformation("ip1 - - [24/Apr/2011:04:06:01 -0400] \\\"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\\\" 200 40028 \\\"-\\\" \\\"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\\\"\\n");
        RequestInformation info2 = new RequestInformation("ip2 - - [24/Apr/2011:04:20:11 -0400] \\\"GET /sun_ss5/pdf.gif HTTP/1.1\\\" 200 390 \\\"http://host2/sun_ss5/\\\" \\\"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16\\\"\\n");
        RequestInformation info3 = new RequestInformation("ip1 - - [24/Apr/2011:04:10:19 -0400] \\\"GET /~strabal/grease/photo1/97-13.jpg HTTP/1.1\\\" 200 56928 \\\"-\\\" \\\"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\\\"\\n");

        mapDriver.withOutput(new IntWritable((int)info1.getId().get()), info1);
        mapDriver.withOutput(new IntWritable((int)info2.getId().get()), info2);
        mapDriver.withOutput(new IntWritable((int)info3.getId().get()), info3);
        mapDriver.runTest();
    }
}