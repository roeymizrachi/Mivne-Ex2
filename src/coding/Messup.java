package coding;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
* from this class we execute the UsersJSONParser
* send the full path of the source JSON file and the full path
* of the output file.
*/
public class Messup {
	public static void main(String[] args) {
		int numOfLoops = 10;
		System.gc();
		PrintUsageMemory("Before Parser");
		float[] times = new float[numOfLoops];
		for (int i = 0; i < numOfLoops; i++) {
			long start = System.currentTimeMillis();
			new UsersJSONParser().Execute("src/ExternalFiles/j.json" , "src/ExternalFiles/users.txt");
			long end = System.currentTimeMillis();
			times[i] = ((float)(end - start))/999;
		}
		WriteArrayToFile(times , "src/ExternalFiles/JavaRunTimes.txt");
		PrintUsageMemory("After Parser");
	}
	
	/**
	 * Print the Usage Memory on this time
	 * @param state
	 */
	private static void PrintUsageMemory(String state){
		Runtime runtime = Runtime.getRuntime();
		long usedMemory  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); 
		System.out.println(String.format("Memory %s : %s Bytes" , state , usedMemory));
	}
	
	/**
	 * Print the given array to file
	 * @param array
	 * @param Path full path of destination file
	 */
	private static void WriteArrayToFile(float[] array , String Path)
	{
		try {
			FileWriter writer = new FileWriter(Path);
			writer.write(Arrays.toString(array));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}