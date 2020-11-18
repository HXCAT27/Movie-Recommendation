#!/usr/bin/env python
# coding: utf-8

# In[1]:


import random
import json
import csv

dialogue_pattern = {
#     categories
    "genres1":[
                { 
                "slots":["genres1"],
                "nl":"Find me some movies about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"Find me some movies in $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I'm looking for movies about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I want movies in $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I want movies about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I need films about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I need films in $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"Find me some films about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"Find me some films in $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I'm looking for films in $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I'm looking for films about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I want films in $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I want films about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I need films about $genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"I need films in $genres1$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"Find me some movies about $genres1$ in $year$",               
                },
                { 
                "slots":["genres1", "year"],
                "nl":"I'm looking for movies about $genres1$ in $year$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"I want $genres1$ movies in $year$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"I need $genres1$ films in $year$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"Find me some $genres1$ films in $year$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"I'm looking for $genres1$ films in $year$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"I want $genres1$ films in $year$",
                },
                { 
                "slots":["genres1", "year"],
                "nl":"I need $genres1$ films in $year$",
                },
                { 
                "slots":["genres1"],
                "nl":"I need $genres1$ films",
                },
                { 
                "slots":["genres1"],
                "nl":"I want $genres1$ films",
                },
                { 
                "slots":["genres1"],
                "nl":"Find me $genres1$ films",
                },
                { 
                "slots":["genres1"],
                "nl":"I'm looking for $genres1$ films",
                },
                { 
                "slots":["genres1"],
                "nl":"I need $genres1$ movies",
                },
                { 
                "slots":["genres1"],
                "nl":"I want $genres1$ movies",
                },
                { 
                "slots":["genres1"],
                "nl":"Find me $genres1$ movies",
                },
                { 
                "slots":["genres1"],
                "nl":"I'm looking for $genres1$ movies",
                },
                { 
                "slots":["genres1"],
                "nl":"$genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"$genres1$ films",
                },
                { 
                "slots":["genres1"],
                "nl":"$genres1$ movies",
                },
                { 
                "slots":["genres1"],
                "nl":"$genres1$",
                },
                { 
                "slots":["genres1"],
                "nl":"In $genres1$",
                },
                ],
    
    "genres2":[
                { 
                "slots":["genres2"],
                "nl":"Find me some movies about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"Find me some movies in $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I'm looking for movies about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I want movies in $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I want movies about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I need films about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I need films in $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"Find me some films about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"Find me some films in $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I'm looking for films in $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I'm looking for films about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I want films in $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I want films about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I need films about $genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"I need films in $genres2$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"Find me some movies about $genres2$ in $year$",               
                },
                { 
                "slots":["genres2", "year"],
                "nl":"I'm looking for movies about $genres2$ in $year$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"I want $genres2$ movies in $year$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"I need $genres2$ films in $year$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"Find me some $genres2$ films in $year$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"I'm looking for $genres2$ films in $year$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"I want $genres2$ films in $year$",
                },
                { 
                "slots":["genres2", "year"],
                "nl":"I need $genres2$ films in $year$",
                },
                { 
                "slots":["genres2"],
                "nl":"I need $genres2$ films",
                },
                { 
                "slots":["genres2"],
                "nl":"I want $genres2$ films",
                },
                { 
                "slots":["genres2"],
                "nl":"Find me $genres2$ films",
                },
                { 
                "slots":["genres2"],
                "nl":"I'm looking for $genres2$ films",
                },
                { 
                "slots":["genres2"],
                "nl":"I need $genres2$ movies",
                },
                { 
                "slots":["genres2"],
                "nl":"I want $genres2$ movies",
                },
                { 
                "slots":["genres2"],
                "nl":"Find me $genres2$ movies",
                },
                { 
                "slots":["genres2"],
                "nl":"I'm looking for $genres2$ movies",
                },
                { 
                "slots":["genres2"],
                "nl":"$genres2$",
                },
                { 
                "slots":["genres2"],
                "nl":"$genres2$ films",
                },
                ],
                "genres3":[
                { 
                "slots":["genres3"],
                "nl":"Find me some movies about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"Find me some movies in $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I'm looking for movies about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I want movies in $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I want movies about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I need films about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I need films in $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"Find me some films about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"Find me some films in $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I'm looking for films in $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I'm looking for films about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I want films in $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I want films about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I need films about $genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"I need films in $genres3$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"Find me some movies about $genres3$ in $year$",               
                },
                { 
                "slots":["genres3", "year"],
                "nl":"I'm looking for movies about $genres3$ in $year$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"I want $genres3$ movies in $year$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"I need $genres3$ films in $year$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"Find me some $genres3$ films in $year$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"I'm looking for $genres3$ films in $year$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"I want $genres3$ films in $year$",
                },
                { 
                "slots":["genres3", "year"],
                "nl":"I need $genres3$ films in $year$",
                },
                { 
                "slots":["genres3"],
                "nl":"I need $genres3$ films",
                },
                { 
                "slots":["genres3"],
                "nl":"I want $genres3$ films",
                },
                { 
                "slots":["genres3"],
                "nl":"Find me $genres3$ films",
                },
                { 
                "slots":["genres3"],
                "nl":"I'm looking for $genres3$ films",
                },
                { 
                "slots":["genres3"],
                "nl":"I need $genres3$ movies",
                },
                { 
                "slots":["genres3"],
                "nl":"I want $genres3$ movies",
                },
                { 
                "slots":["genres3"],
                "nl":"Find me $genres3$ movies",
                },
                { 
                "slots":["genres3"],
                "nl":"I'm looking for $genres3$ movies",
                },
                { 
                "slots":["genres3"],
                "nl":"$genres3$",
                },
                { 
                "slots":["genres3"],
                "nl":"$genres3$ films",
                },
            ],
                
        
        
#     "genres1":[
                
            
    "year":[
                { 
                "slots":["year"],
                "nl":"$year$",
                },
                { 
                "slots":["year"],
                "nl":"In $year$",
                },
            ],
#     "price":[
#                 { 
#                 "slots":["price"],
#                 "nl":"$price$ price",
#                 },
#                 { 
#                 "slots":["price"],
#                 "nl":"$price$ pricing",
#                 },
#                 { 
#                 "slots":["price"],
#                 "nl":"$price$",
#                 },
#             ],
    "rating":[
                { 
                "slots":["rating"],
                "nl":"$rating$",
                },
                { 
                "slots":["rating"],
                "nl":"$rating$ rating",
                },
            ]
}


# load json file
# "movieId": {"genres1": "Imported Food", "genres1": "ON", "year": "Mississauga", "price": "low", "rating": 2.5}
with open('movies_info_filter.json', 'r') as f:
    movies_info_dict = json.load(f)
    
# ratings_filter : userId, movieId, rating
dialogue_list = {}
dialogue_id = 0


# ratings = pd.read_csv('./ml-latest-small/ratings.csv')
# ratings = ratings.drop(['timestamp'], axis=1)
with open('./ml-latest-small/ratings.csv') as f:
    f_csv = csv.reader(f)
    headers = next(f_csv)
#     userId,movieId,rating
    for row in f_csv:
#         print(row)
        userId = row[0]
        movieId = row[1]
        rating = row[2]

        new_dialogue = {}
        new_dialogue["userId"] = userId
        new_dialogue["movieId"] = movieId
        new_dialogue["rating"] = rating
        new_dialogue["content"] = []

        slot_list = ["genres1","genres2","genres3","year"]
        random.shuffle(slot_list)
        slot_list.insert(0, "rating")
        said_slot = set()

        for slot in slot_list:
            if slot in said_slot:
                continue
            pattern_dict = random.choice(dialogue_pattern[slot])
            utterance_content = pattern_dict["nl"]
            utterance_dict = {}
            utterance_dict["slots"] = {}
            for current_slot in pattern_dict["slots"]:
                slot_val = str(movies_info_dict[movieId][current_slot])
                utterance_content = utterance_content.replace('$'+current_slot+'$', slot_val, 1)
                utterance_dict["slots"][current_slot] = slot_val
                said_slot.add(current_slot)
            utterance_dict["nl"] = utterance_content
            new_dialogue["content"].append(utterance_dict)
        dialogue_list[dialogue_id] = new_dialogue
        dialogue_id += 1

with open('dialogue_data.json', 'w') as f:
    json.dump(dialogue_list, f, indent=4)


# In[ ]:




