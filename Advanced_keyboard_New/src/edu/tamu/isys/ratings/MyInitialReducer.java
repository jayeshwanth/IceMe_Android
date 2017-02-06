/**
 * 
 */
package edu.tamu.isys.ratings;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class MyInitialReducer extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> 
{
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapred.Reducer#reduce(java.lang.Object, java.util.Iterator, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
	 */
	/**
	 * Input:-Input with Key(Genre+Movie) and value(individual rating)
	 * Description:-Takes input from mapper and creates a 
	 * 				key(Genre+Movie) value(avgRatings)
	 * Output:-Output with key(Genre+Movie) value(avgRatings)
	 */
	public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
		float sumRatings = 0;
		int countRating=0;
		while (values.hasNext()) 
		{
			sumRatings += values.next().get();//Calculating Sum of Ratings
			countRating++;//Counting no. of ratings for movie 
		}
		output.collect(key, new FloatWritable(Program.roundToTwoDecimalPlace(sumRatings/countRating, 2)));
	}
}
