package coding;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class represent a JSON parser.
 * by given source file.
 * filter the all 'username' and 'password' and write them into JSON array.
 * the whole filtered inserted to the file as an JSON Object with key 'users'
 */
public class UsersJSONParser {

	private final String USERNAME_FIELD = "username";
	private final String PASSWORD_FIELD = "password";

	/**
	 * Execute the Parser
	 * @param InputFilePath full path of the input file
	 * @param OutputFilePath full path of the ouputFile
	 */
	public void Execute(String InputFilePath , String OutputFilePath) {
		if(InputFilePath == null) throw new IllegalArgumentException("InputFilePath are NULL");
		if(OutputFilePath == null) throw new IllegalArgumentException("OuputFilePath are NULL");
		JSONArray jsonArray = (JSONArray)ReadFromJsonFile(InputFilePath);
		JSONArray filterArray = JsonFilter(jsonArray);
		JSONObject ouputObject = new JSONObject();
		ouputObject.put("users", filterArray);
		WriteJsonToFile(OutputFilePath, ouputObject);
//		System.out.println("JSON Object: " + ouputObject.toJSONString());
	}

	/**
	 * Write JSON object to file
	 * @param Path path to the output file
	 * @param obj the JSON object want to write to file
	 */
	private void WriteJsonToFile(String Path , JSONObject obj) {
		FileWriter writer;
		try {
			writer = new FileWriter(Path);
			writer.write(obj.toJSONString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Filter from array the user and password values
	 * @param jsonArray JSON Array
	 * @return JSON array of filtered parameters
	 */
	private JSONArray JsonFilter(JSONArray jsonArray) {
		JSONArray result = new JSONArray();
		if(jsonArray == null) return result;
		for (int index = 0; index < jsonArray.size(); index++) {
			JSONObject currentObj = (JSONObject)jsonArray.get(index);
			if(!currentObj.containsKey(USERNAME_FIELD) || !currentObj.containsKey(USERNAME_FIELD)) continue;
			String username = currentObj.get(USERNAME_FIELD).toString();
			int password = currentObj.get(PASSWORD_FIELD).toString().hashCode();
			result.add(String.format("%s:%d", username , password));
		}
		return result;
	}

	/**
	 * Read From File
	 * @param Path input file path
	 * @return Object represent the file
	 */
	private Object ReadFromJsonFile(String Path){
		Object result = null;
		try {
			JSONParser parser = new JSONParser();
			result =  parser.parse(new FileReader(Path));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
