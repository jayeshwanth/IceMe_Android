/**
 * 
 */
package edu.tamu.isys.ratings;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * @author arpit2408
 *
 */
/**
 * This class will take input in this format
 * 
 * @author arpit2408
 * This class will give output in this format
 */
public class MyFinalReducer extends MapReduceBase  implements Reducer<Text, Text, Text, Text> 
{
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapred.Reducer#reduce(java.lang.Object, java.util.Iterator, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
	 */
	@Override
	/**
	 * Input:-Input with Key(Genre) and value(Movie+AvgRating)
	 * Description:-Takes input from mapper and creates a 
	 * 				key(Genre) value(MovieName MaxavgRatings)
	 * Output:-Output with key(Genre) value(MovieName MaxavgRatings)
	 */
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter arg3)
			throws IOException 
	{
		Float highestavg=0.0f;//to store highestavg here
		String moviename="";//to store movie name with highestavg
		while (values.hasNext()) 
		{
			String str=values.next().toString();
			//To Extract rating from the value
			String rating=str.substring(str.length()-4, str.length());
			if(highestavg<Float.parseFloat(rating))
			{
				highestavg=Float.parseFloat(rating);//Making highest value on comparison
				moviename=str;//Storing moviename and rating with highest avg
			}
		}
		output.collect(key, new Text("\t"+moviename));
	}
}
