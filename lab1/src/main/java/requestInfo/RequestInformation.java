package requestInfo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class RequestInformation implements Writable {
    private LongWritable id;
    private LongWritable count;
    private LongWritable byteSum;

    public RequestInformation() {
        id = new LongWritable(0);
        count = new LongWritable(0);
        byteSum = new LongWritable(0);
    }

    public RequestInformation(String input) {
        String[] inputs = input.split(" ");
        this.id = new LongWritable(Long.parseLong(inputs[0].substring(2)));
        if (inputs[9].equals("-")) {
            this.byteSum = new LongWritable(0);
        }
        else {
            this.byteSum = new LongWritable(Long.parseLong(inputs[9]));
        }
        count = new LongWritable(0);
    }

    public void write(DataOutput dataOutput) throws IOException {
        id.write(dataOutput);
        count.write(dataOutput);
        byteSum.write(dataOutput);
    }

    public void readFields(DataInput dataInput) throws IOException {
        id.readFields(dataInput);
        count.readFields(dataInput);
        byteSum.readFields(dataInput);
    }

    public void addToSum(long add) {
        byteSum.set(byteSum.get() + add);
        count.set(count.get() + 1);
    }

    @Override
    public String toString() {
        int tmp = (int)count.get();
        if (tmp == 0)
            tmp = 1;

        return (byteSum.get() / tmp) + " " + byteSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInformation that = (RequestInformation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(count, that.count) &&
                Objects.equals(byteSum, that.byteSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, byteSum);
    }

    public LongWritable getId() {
        return id;
    }

    public void setId(LongWritable id) {
        this.id = id;
    }

    public LongWritable getCount() {
        return count;
    }

    public void setCount(LongWritable count) {
        this.count = count;
    }

    public LongWritable getByteSum() {
        return byteSum;
    }

    public void setByteSum(LongWritable byteSum) {
        this.byteSum = byteSum;
    }
}