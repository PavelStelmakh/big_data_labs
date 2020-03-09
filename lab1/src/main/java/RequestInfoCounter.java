import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import requestInfo.MapRequestInfo;
import requestInfo.ReduceRequestInfo;
import requestInfo.RequestInformation;

public class RequestInfoCounter extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s needs two arguments, input and output " +
                    "files\n", getClass().getSimpleName());
            return -1;
        }

        Job job = new Job();
        job.setJarByClass(RequestInfoCounter.class);
        job.setJobName("WordCounter");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //FileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(RequestInformation.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapperClass(MapRequestInfo.class);
        job.setReducerClass(ReduceRequestInfo.class);

        int returnValue = job.waitForCompletion(true) ? 0:1;

        for (CounterGroup group : job.getCounters()) {
            for (Counter counter : group) {
                System.out.println(counter.getName() + "  " + counter.getValue());
            }
        }

//        for (Counter count : job.getCounters().getGroup("Browsers")) {
//        }

        if(job.isSuccessful()) {
            System.out.println("Job was successful");
        } else if(!job.isSuccessful()) {
            System.out.println("Job was not successful");
        }

        return returnValue;
    }
}
