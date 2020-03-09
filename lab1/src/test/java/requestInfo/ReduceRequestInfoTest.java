package requestInfo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReduceRequestInfoTest {
    private ReduceDriver<IntWritable, RequestInformation, IntWritable, RequestInformation> reduceDriver;

    @Before
    public void setUp() {
        ReduceRequestInfo reducer = new ReduceRequestInfo();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

    @Test
    public void reduce() throws IOException {
        List<RequestInformation> info = new ArrayList<>();
        info.add(new RequestInformation());
        info.add(new RequestInformation());
        info.add(new RequestInformation());
        info.get(0).setId(new LongWritable(1));
        info.get(0).setByteSum(new LongWritable(40000));
        info.get(1).setId(new LongWritable(1));
        info.get(1).setByteSum(new LongWritable(7000));
        info.get(2).setId(new LongWritable(1));
        info.get(2).setByteSum(new LongWritable(13000));

        RequestInformation result = new RequestInformation();
        result.setId(new LongWritable(1));
        result.setByteSum(new LongWritable(60000));
        result.setCount(new LongWritable(3));

        reduceDriver.withInput(new IntWritable(1), info);
        reduceDriver.withOutput(new IntWritable(1), result);
        reduceDriver.runTest();
    }
}