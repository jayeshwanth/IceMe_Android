package edu.tamu.isys.ratings;

import java.math.BigDecimal;
import java.util.Random;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 * 
 */

/**
 * @author arpit2408
 *
 */
public class Program {

	public static float randfloat() {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    float randomNum = rand.nextFloat() ;

	    return randomNum;
	}
	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(Program.class);
		conf.setJobName("Movie_Rating_Average_List");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(FloatWritable.class);
		conf.setMapperClass(MyInitialMapper.class);
		conf.setCombinerClass(MyInitialReducer.class);
		conf.setReducerClass(MyInitialReducer.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		float answer = randfloat();
		System.out.println(answer+"answer");
		FileOutputFormat.setOutputPath(conf, new Path("/outputPhasearpit"+answer));    
		JobClient.runJob(conf);

		JobConf job2 = new JobConf(Program.class);
		job2.setJobName("program");

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		job2.setMapperClass(MyFinalMapper.class);
		job2.setCombinerClass(MyFinalReducer.class);
		job2.setReducerClass(MyFinalReducer.class);
		//job2.setNumReduceTasks(0);
		job2.setInputFormat(TextInputFormat.class);
		job2.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(job2, new Path("/outputPhasearpit"+answer));
		FileOutputFormat.setOutputPath(job2, new Path(args[1]));
		JobClient.runJob(job2);
	}
	/**
	 * Input:-float value and its precision 
	 * Description:This function takes float value
	 *  			as input and produces float value(with two 
	 *  			precision in this case)
	 * Output:-Float value with two precision 
	 * 
	 * @param d
	 * @param precision
	 * @return
	 */
	public static float roundToTwoDecimalPlace(float number_float, int precision) 
	{
		BigDecimal bigvartemp = new BigDecimal(Float.toString(number_float));
		bigvartemp = bigvartemp.setScale(precision, BigDecimal.ROUND_HALF_UP);
		return bigvartemp.floatValue();
	}
}	


