/**
 * 
 */
package edu.tamu.isys.ratings;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
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
public class MyInitialMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
	private static FloatWritable rating = new FloatWritable(1);
	private Text genre_movie_composite_key = new Text();

	public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
		String line = value.toString();
		String[] elements=line.split("::");
		String movieName;
		String genreName;
		int i=0;
		while (i<elements.length) {
			Integer.parseInt(elements[i++]);
			movieName=elements[i++];
			genreName=elements[i++];
			Integer.parseInt(elements[i++]);
			Integer.parseInt(elements[i++]);
			elements[i++].toString();
			rating.set(Float.parseFloat(elements[i++]));
			elements[i++].toString();
			int j=0;
			String[] genre;
			if(genreName!=null && !genreName.equals("") && !genreName.equals(" "))
			{
				genre=genreName.split(",");
				while(j<genre.length)
				{
					if(genre[j]!=null && !genre[j].equals("") && !genre[j].equals(" "))
					{
						genre_movie_composite_key.set(genre[j++]+"::"+movieName);
						output.collect(genre_movie_composite_key, rating);
					}
				}
			}
		}
	}
}
