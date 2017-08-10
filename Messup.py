import json
from memory_profiler import profile

def readAndFilterJsonFromFile(input_file):
    # read from file
    with open(input_file) as json_file:
        data = json.load(json_file)
        json_file.close()
        filtered_array = []
        # filter the username and password from the json file
        for obj in data:
            if 'username' in obj and 'password' in obj:
                temp_obj = {obj.get('username'): hash(obj.get('password'))}
                filtered_array.append(temp_obj)

        return filtered_array

def writeJsonToFile(users, output_file):
    # write the filtered users to file and print it
    with open(output_file, 'w') as outputFile:
        output_json = {'users': users}
        json.dump(output_json, outputFile)
        outputFile.close()

@profile
def parseJson(input_file, output_file):
    users = readAndFilterJsonFromFile(input_file)
    writeJsonToFile(users, output_file)
    print('JSON Object:', users)

if __name__ == "__main__":
    parseJson('j.json', 'output.txt')
