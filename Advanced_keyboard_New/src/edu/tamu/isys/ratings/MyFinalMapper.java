/**
 * 
 */
package edu.tamu.isys.ratings;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
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
 * 
 */
public class MyFinalMapper extends MapReduceBase implements  Mapper<LongWritable, Text, Text, Text> 
{
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapred.Mapper#map(java.lang.Object, java.lang.Object, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
	 */
	@Override
	/**
	 * @author arpit2408
	 * Input:-Output File from previous Reducer
	 * Output:-Map of genre as key and movie+rating as value
	 * Description:-Creates a mapper wih genre as key and movie+rating as value
	 * Throws IOException
	 */
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter arg3)
			throws IOException 
	{
		//Takes value of first line as input
		String line = value.toString();
		if(line!=null && !line.equals("") && !line.equals(" "))
		{
			String[] words=line.split("::");//Splitting on basis of delimiter("::")
			String movieName_Rating;//to store the concatenated value of movie & rating
			String genreName;
			genreName=words[0];
			movieName_Rating=words[1];
			output.collect(new Text(genreName), new Text(movieName_Rating));
			System.out.println();
		}
	}
}

