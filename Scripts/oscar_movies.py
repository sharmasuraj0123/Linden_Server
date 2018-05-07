import requests
from bs4 import BeautifulSoup
from datetime import datetime

linden_url = 'http://127.0.0.1:8080/admin/addMovie'
omdb_url = 'http://www.omdbapi.com/'
omdb_key = '12d0adae'

page = requests.get('https://www.imdb.com/search/title?count=100&groups=oscar_best_picture_winners&sort=year,desc&ref_=nv_ch_osc_2')

soup = BeautifulSoup(page.content, 'html.parser')

movie_list = soup.find_all(class_='lister-item-header')

for movie in movie_list:
	imdb_id = movie.find('a')['href'].split('/')[2]
	data = requests.get(omdb_url + '?i=' + imdb_id + '&apikey=' + omdb_key).json()

	movie_object = {}
	cast = []

	if 'Title' in data:
		movie_object['name'] = data['Title']
		print('Title: ' + data['Title'])
		
	if 'Released' in data:
		movie_object['releaseDate'] = datetime.strptime(data['Released'], '%d %b %Y').date().strftime('%Y-%m-%d')


	if 'Year' in data:
		movie_object['duration'] = int(data['Runtime'].split(' ')[0])

	if 'Director' in data:
		print('Director: ' + data['Director'])
		name_tokens = data['Director'].split(' ')
		cast_object = {'firstName': name_tokens[0], 'lastName': name_tokens[1]}
		cast.append(cast_object)

	if 'Actors' in data:
		print('Actors: ' + data['Actors'])
		if data['Actors'].upper() != 'N/A':
			for actor in data['Actors'].split(' '):
				name_tokens = actor.split(' ')
				if len(name_tokens) >= 2:
					cast_object = {'firstName': name_tokens[0], 'lastName': ''.join(name_tokens[1:])}
				else:
					cast_object = {'firstName': name_tokens[0]}
				cast.append(cast_object)
			
	if 'Language' in data:
		print('Language: ' + data['Language'])
	
	if 'Plot' in data:
		movie_object['details'] = data['Plot']
		print('Plot: ' + data['Plot'])

	if 'Genre' in data:
		movie_object['genre'] = data['Genre'].upper().replace('-', '_').replace(' ', '').split(',')
		print('Genre: ' + data['Rated'])

	if 'Rated' in data:
		print('Rating: ' + data['Rated'])

	if 'imdbRating' in data:
		movie_object['score'] = float(data['imdbRating'])/2

	print('------------------------------------------------------')
	movie_object['cast'] = cast
	print(movie_object)
	#print(requests.post(url=linden_url, json=movie_object))
