package requestInfo;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceRequestInfo extends Reducer<IntWritable, RequestInformation, IntWritable, RequestInformation> {

    @Override
    public void reduce(IntWritable key, Iterable<RequestInformation> values, Context context)
            throws IOException, InterruptedException {
        RequestInformation aggrigrateInfo = new RequestInformation();

        for (RequestInformation value : values) {
            aggrigrateInfo.addToSum(value.getByteSum().get());
        }

        aggrigrateInfo.setId(new LongWritable(key.get()));
        context.write(key, aggrigrateInfo);
    }
}
