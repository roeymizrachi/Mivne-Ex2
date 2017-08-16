import json
import time
import matplotlib.pyplot as matplotlib
from memory_profiler import profile

#GLOBALS
SIZE_OF_GRAPH_X = 5
SIZE_OF_GRAPH_Y = 10
COLONS = 2

JAVA_COLOR_GRAPH = 'r'
PYTHON_COLOR_GRAPH = 'y'

JAVA_TITLE = 'Java'
PYTHON_TITLE = 'Python'

def readAndFilterJsonFromFile(input_file):
    ''''
    :param input_file: path to file
    function get path to file with Json array.
    load data with json object.
    copy data user name and user pass to json object.
    hashing password
    :return json object
    '''
    with open(input_file) as json_file:
        data = json.load(json_file)
        users = []
        for obj in data:
            if 'username' in obj and 'password' in obj :
                temp_obj = {obj.get('username'):hash(obj.get('password'))}
                users.append(temp_obj)
        json_file.close()
    return users

def writeJsonToFile(arr_json_objs , output_file):
    '''
    function open new file and write to it json object
    :param arr_json_objs: json object
    :param output_file: new file with json object
    :return: None
    '''
    with open(output_file, 'w') as json_file:
        json_obj = {'users': arr_json_objs}
        json.dump(json_obj, json_file)
        json_file.close()

@profile
def parseJson(input_file,output_file):
    '''
    :param input_file: path to file with json array
    :param output_file: path to out file
    :return: None
    '''
    users = readAndFilterJsonFromFile(input_file)
    writeJsonToFile(users, output_file)

def createGraph(times :list, color :str, intervals :list, grid :bool, title :str , graph :object):
    '''
    :param times: array of times
    :param color: charartes of color
    :param intervals: array of intervals , axis X
    :param grid: bool
    :param title: title of graph
    :param graph: object represented graph
    :return: None
    '''
    graph.hist(times , bins= intervals , color=color)
    graph.set_title(title)
    graph.grid(grid)



ARRAY_INTERVALS = [0, 0.001, 0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009, 0.01, 0.02, 0.03]

if __name__ == "__main__":
    # filling times array with values , each value represent a program run
    times = []
    for i in range(10):
        start = time.time()
        parseJson('j.json', 'output.txt')
        end = time.time()
        times.append(float(end-start))

    #read java run values from file
    with open('JavaRunTimes.txt') as run_file:
        java_array_times = run_file.read()
        java_array_times = java_array_times[1:-1].split(', ')
        java_times = []
        for i in java_array_times:
            java_times.append(float(i))

    #tables and two graphs
    table , (graphPython , graphJava) = matplotlib.subplots(ncols=COLONS , figsize=(SIZE_OF_GRAPH_Y,SIZE_OF_GRAPH_X))
    createGraph(times=java_times , color=JAVA_COLOR_GRAPH , graph=graphJava , grid=True , intervals=ARRAY_INTERVALS , title=JAVA_TITLE)
    createGraph(times=times , color=PYTHON_COLOR_GRAPH , graph=graphPython , grid=True , intervals=ARRAY_INTERVALS , title=PYTHON_TITLE)

    matplotlib.show()