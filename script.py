import requests
import json
import sys
from datetime import datetime

# http://www.omdbapi.com/?i=tt0137523&apikey=12d0adae
omdb_url = 'http://www.omdbapi.com'
omdb_key = '12d0adae'

# https://api.themoviedb.org/3/movie/550?api_key=89a57552ff989080c44c82c3078fb543
tmdb_key = '89a57552ff989080c44c82c3078fb543'
tmdb_url = 'https://api.themoviedb.org/3/movie/'

linden_url = 'http://127.0.0.1:8080/admin/addMovie'

movie_id = 1
counter = 0

while counter < 100:
    # Hitting the tmdb API
    tmdb_response = requests.get(url=tmdb_url + str(movie_id) + '?api_key=' + tmdb_key)
    tmdb_data = tmdb_response.json()

    print(tmdb_data)

    if 'status_code' in tmdb_data:
        movie_id += 1
        continue

    # extrating the imdb ID
    imdb_id = tmdb_data['imdb_id']

    # Hitting the omdb API
    omdb_response = requests.get(url=omdb_url + '?i=' + imdb_id + '&apikey=' + omdb_key)

    data = omdb_response.json()

    movieObject = dict()
    cast = list()

    print('Number: ' + str(counter + 1))
    if 'Title' in data:
        movieObject["name"] = data["Title"]
        print('Title: ' + data['Title'])
    if 'Released' in data:
        movieObject['releaseDate'] = datetime.strptime(data['Released'], '%d %b %Y').date().strftime('%Y-%m-%d')
        print('Released: ' + data['Released'])
    if 'Year' in data:
        print('Year: ' + data['Year'])
    if 'Runtime' in data:
        movieObject['duration'] = int(data['Runtime'].split(' ')[0])
        print('Runtime: ' + data['Runtime'])
    if 'Poster' in data:
        print('Poster: ' + data['Poster'])
    if 'Director' in data:
        print('Director: ' + data['Director'])
        nameTokens = data['Director'].split(' ')
        castObject = {'firstName': nameTokens[0], 'lastName': nameTokens[1]}
        cast.append(castObject)
    if 'Actors' in data:
        print('Actors: ' + data['Actors'])
        if data['Actors'].upper() != 'N/A':
            for actor in data['Actors'].split(', '):
                nameTokens = actor.split(' ')
                if len(nameTokens) >= 2:
                    castObject = {'firstName': nameTokens[0], 'lastName': ''.join(nameTokens[1:])}
                else:
                    castObject = {'firstName': nameTokens[0]}
                cast.append(castObject)
    if 'Language' in data:
        print('Language: ' + data['Language'])
    if 'Plot' in data:
        movieObject['details'] = data['Plot']
        print('Plot: ' + data['Plot'])
    if 'Genre' in data:
        movieObject['genre'] = data['Genre'].upper().replace('-', '_').replace(' ', '').split(',')
        print('Genre: ' + data['Genre'])
    if 'Rated' in data:
        print('Rating: ' + data['Rated'])
    if 'imdbRating' in data:
        movieObject['score'] = float(data['imdbRating'])/2
    print('----------------------------------------------------------')
    movieObject['cast'] = cast
    print(movieObject)
    print(requests.post(url=linden_url, json=movieObject))
    movie_id += 1
    counter += 1
