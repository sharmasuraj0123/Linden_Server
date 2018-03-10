import requests
import json

# http://www.omdbapi.com/?i=tt0137523&apikey=12d0adae
omdb_key = '12d0adae'
omdb_url = 'http://www.omdbapi.com'

# https://api.themoviedb.org/3/movie/550?api_key=89a57552ff989080c44c82c3078fb543
tmdb_key = '89a57552ff989080c44c82c3078fb543'
tmdb_url = 'https://api.themoviedb.org/3/movie/'

print('Running!')

movie_id = 1
counter = 0

while(counter < 100):
	# Hitting the tmdb API
	tmdb_response = requests.get(url = tmdb_url + str(movie_id) +'?api_key=' +tmdb_key)
	tmdb_data = tmdb_response.json()

	if 'status_code' in tmdb_data:
		movie_id += 1
		# print(tmdb_response)
		continue

	# print(tmdb_data)

	# extrating the imdb ID
	imdb_id = tmdb_data['imdb_id']

	# Hitting the omdb API
	omdb_response = requests.get(url = omdb_url + '?i=' + imdb_id + '&apikey=' + omdb_key)

	data = omdb_response.json()

	print('Title: '+ data['Title'])
	print('Released:' +data['Released'])
	print('Year: '+ data['Year'])
	print('Runtime: '+ data['Runtime'])
	print('Poster: '+ data['Poster'])
	print('Director: '+ data['Director'])
	print('Actors: '+ data['Actors'])
	print('Language: '+ data['Language'])
	print('Plot: '+ data['Plot'])
	print('Genre: '+ data['Genre'])
	print('Rating: '+data['Rated'])
	print('----------------------------------------------------------')
	movie_id += 1
	counter += 1


