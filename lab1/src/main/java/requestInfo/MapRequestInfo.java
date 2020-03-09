package requestInfo;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MapRequestInfo extends Mapper<LongWritable, Text, IntWritable, RequestInformation> {
    private final static IntWritable intKey = new IntWritable(1);
    private RequestInformation info;

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        info = new RequestInformation(value.toString());

        intKey.set((int)info.getId().get());
        UserAgent browser = new UserAgent(value.toString());

        try {
            context.getCounter("Browsers", browser.getBrowser().getName()).increment(1);
            context.write(intKey, info);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
