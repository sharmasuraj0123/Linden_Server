import requests
import json
import sys
from datetime import datetime
import time

tmdb_key = '89a57552ff989080c44c82c3078fb543'
tmdb_url = 'https://api.themoviedb.org/3/tv/'

linden_url = 'http://127.0.0.1:8080/admin/addTV'

tv_id_list = [1418, 1668, 19885, 63351, 1396, 1405, 1399, 2288, 62816, 60708, 61889, 1100, 1421, 60059]

for tv_id in tv_id_list:
	print(str(tv_id) + '***************************')
	tmdb_response = requests.get(url=tmdb_url + str(tv_id) + '?api_key=' + tmdb_key + '&append_to_response=credits')
	data = tmdb_response.json()

	no_of_seasons = data.get('number_of_seasons')
	tv_obj = {}
	tv_obj['name'] = data.get('name')
	tv_obj['year'] = int(data.get('first_air_date').split('-')[0])

	# created by
	tv_obj['created_by'] = []
	created_by = data.get('created_by')
	for i in range(0, len(created_by)):
		tv_obj['created_by'].append(created_by[i].get('name'))
	
	# GENRES
	genres = data.get('genres')
	tv_obj['genre'] = []
	for i in range(0, len(genres)):
		tv_obj['genre'].append(genres[i].get('name').upper())

	# CAST
	cast = data.get('credits').get('cast')
	tv_obj['cast'] = []
	for i in range (0, len(cast)):
		tv_obj['cast'].append(cast[i].get('name'))	

	# print(tv_obj)
	# SEASONS
	seasons = {}
	for i in range (0, no_of_seasons):
		seasons_response = requests.get(url=tmdb_url + str(tv_id) + '/season/' + str(i + 1) + '?api_key=' + tmdb_key)
		seasons_data = seasons_response.json()
		no_of_episodes = len(seasons_data.get('episodes'))
		current_season = 'Season ' + str(i + 1)
		
		for j in range (0, no_of_episodes):
			del seasons_data.get('episodes')[j]['crew']
			del seasons_data.get('episodes')[j]['guest_stars']
			del seasons_data.get('episodes')[j]['id']
			del seasons_data.get('episodes')[j]['production_code']
			del seasons_data.get('episodes')[j]['season_number']
			del seasons_data.get('episodes')[j]['vote_average']
			del seasons_data.get('episodes')[j]['vote_count']
			del seasons_data.get('episodes')[j]['air_date']
			del seasons_data.get('episodes')[j]['still_path']

		seasons[current_season] = seasons_data.get('episodes')
	
	tv_obj['seasons'] = seasons
	print(tv_obj)		

	time.sleep(2)
