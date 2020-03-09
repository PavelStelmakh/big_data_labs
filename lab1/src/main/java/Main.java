import org.apache.hadoop.util.ToolRunner;

public class Main {
     public static void main(String[] args) throws Exception {
        ToolRunner.run(new RequestInfoCounter(), args);
    }
}
