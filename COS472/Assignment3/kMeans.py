import ast
import math
import random
import sys
import numpy as np
from typing import Dict, List
import argparse

data = {} #dynamically updating

MAX_ITERATIONS=10
CONVERGENCE_TOLERANCE=0.0001

def main():
    global entries

    num_categories = 2
    kMeans(num_categories)

def kMeans(num_categories: int):
    '''
    This algorithm uses Python's nested dictionaries to structure the data AS FOLLOWS:

    data's nested dictionary structure:
    {
#     "clusters": [
#         {
#             "centroid": [x_1, x_2, ..., x_n],
#             "points": [
#                 {"coordiantes": [x_1, x_2, ..., x_n],
#                 {"coordinates": [x_1, x_2, ..., x_n],
                  ...
#             ]
#         },
#         ...
#     ]
    
    '''
    
    init_dict(num_categories)
    init_centroids(num_categories)
    init_clusters()
    print("\n --- \n\nData after Initialization:\n\n", data_formatter())
    iterations_made = update_centroids()
    
    if(iterations_made < MAX_ITERATIONS):
        print(f"Data after {iterations_made} iterations, and model converged \n\n", data_formatter())
    else:
        print(f"Max Iterations reached ({iterations_made}).  Early termination executed. \n\n", data_formatter())
        
def init_dict(num_categories: int):
    '''
    initializes the dictionary to store clusters and calls helper method: init_centroids
     to inititialize centroids at random
    '''

    global data
    data = {
        "clusters": [
            {
                "centroid": [],           
                "points": []   
            }
            for _ in range(num_categories)
        ]
    }
 
def init_centroids(num_categories: int):
    '''
    helper method called by init_dict to initialize centrods at random from the dataset
    '''

    global data
    global entries
    unique_values = set()

    for x in range(num_categories):
        while True:
            next_index = random.randint(0, len(entries)-1)
            if next_index not in unique_values: # if random entry has not aleady been selected
                unique_values.add(next_index)
                data["clusters"][x]["centroid"] = entries[next_index]  # Assign centroid
                break

def init_clusters():
    '''
    initialize the dictionary with the data using euclidean distances
    '''
    
    global data
    global entries

    for entry in entries :
        min_dist = {
            "centroid": "None",
            "euc_dist": sys.maxsize
        }

        for cluster in data["clusters"]:
            current_centroid = cluster["centroid"]
            cur_dist = euclidean_calculator(current_centroid,entry)

            if cur_dist < min_dist["euc_dist"]: #a closer centroid has been found, store it in the temp val: min_dist
                min_dist["euc_dist"] = cur_dist
                min_dist["centroid"] = current_centroid

        for cluster in data["clusters"]:
            if cluster["centroid"] == min_dist["centroid"]:
                cluster["points"].append({
                    "coordinates": entry
                })

def update_centroids()-> int:
    '''
    Recalcuate centroids with mean values, then check if centroid have converged
    '''

    global data

    updated_centroids = [ #this stores the updated centroids for use in update_clusters
        {
            "centroid": [],
            "points": [] 
        }   
        for _ in range(len(data["clusters"]))
    ]

    previous_centroids = [cluster["centroid"][:] for cluster in data["clusters"]]  # save original centroids for convergence comparison

    i = 1 #iterations
    while True and i < MAX_ITERATIONS: 

        for j, cluster in enumerate(data["clusters"]): #update the centroid of each cluster
            
            num_points = len(cluster["points"])

            new_centroid = np.zeros(len(entries[0]))
            
            for point in cluster["points"]: #calculate mean - first add values
                new_centroid += np.array(point["coordinates"]) 

            new_centroid  /= num_points #second divide by num_points

            cluster["centroid"]=new_centroid
            updated_centroids[j] = {"centroid":new_centroid}

        if check_convergence(i, previous_centroids, updated_centroids): #centroids have converged
            break

        else: #update previous centroid values and iterate again
            previous_centroids = [cluster["centroid"][:] for cluster in updated_centroids]
            i+=1
    return i




def check_convergence(i: int, previous_centroids: List[Dict], updated_centroids: List[Dict]):
    '''
    this helper function checks for convergence after each iteration
    '''
    for prev, updated in zip(previous_centroids, updated_centroids):
        if euclidean_calculator(prev,updated["centroid"]) > CONVERGENCE_TOLERANCE:
            return False
    return True

def euclidean_calculator(p1: List[float], p2: List[float]) -> float:
    '''
    helper function calculates the euclidean distance between two points
    '''

    if len(p1) != len(p2):
        raise ValueError("len(p1) != len(p2)")
    
    sum = 0
    for x, y in zip(p1, p2):
        sum+=(x-y)**2
    
    return math.sqrt(sum)

def data_formatter():
    '''
    this functions handled by the main driver function formats outputs
    '''
    return_string = ""

    for c, cluster in enumerate(data["clusters"]):
        formatted_centroid = np.array2string(
            np.array(cluster["centroid"]),
            precision=2,
            separator=', '
        )
        return_string += f"Centroid {c+1}: {formatted_centroid}\n\n"
        for point in cluster["points"]:
            return_string += f"point: {point}\n"

        return_string += "\n"

    return_string += "--- \n"
    return return_string

if __name__ == "__main__":

    parser = argparse.ArgumentParser(description="Parse entries for processing.")
    parser.add_argument(
        "entries",
        type=str,
    )
    args = parser.parse_args()
    
    entries = ast.literal_eval(args.entries)

    if not isinstance(entries, list) or not all(isinstance(point, list) for point in entries):
        raise ValueError("Entries must be a list of lists.")

    main()
