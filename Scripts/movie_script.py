import sys
from datetime import datetime
import requests

# https://api.themoviedb.org/3/movie/343611/casts?api_key=89a57552ff989080c44c82c3078fb543

# https://api.themoviedb.org/3/movie/343611?api_key=89a57552ff989080c44c82c3078fb543&append_to_response=images,videos,credits

linden_url = 'http://127.0.0.1:8080/admin/addMovie'

url1 = 'https://api.themoviedb.org/3/movie/'
url2 = '?api_key=89a57552ff989080c44c82c3078fb543&append_to_response=images,videos,credits'

cast_url1 = 'https://api.themoviedb.org/3/movie/'
cast_url2 = '/casts?api_key=89a57552ff989080c44c82c3078fb543'



image_path = 'https://image.tmdb.org/t/p/w500'
youtube_path = 'https://www.youtube.com/watch?v='

movies = {299536,284054, 284053, 333339, 343611, 374720, 244786, 399055, 399035, 407451, 383498, 439015, 429300, 22}
# infinity war, black-panther, thor-ragnarok, ready-player-one, jack-reacher-never-go-back, dunkirk, whiplash,
# the-shape-of-water, The Commuter, a-wrinkle-in-time,
# Dead pool 2, slender man, adrift, pirates-of-the-caribbean-the-curse-of-the-black-pearl

for i in movies:
    
    response1 = requests.get(url=url1 + str(i)+url2)
    data = response1.json()
    
    movieObject = dict()
    cast = list()
    videos = list()
    images = list()
      
    if 'original_title' in data:
        movieObject["name"] = data["original_title"]
        print('name: ' + data['original_title'])
    
    if 'overview' in data:
        movieObject["details"] = data["overview"]
        print('details: ' + data['overview'])
        
    if 'release_date' in data:
        movieObject["releaseDate"] = data["release_date"]
        print('releaseDate: ' + data['release_date'])
        
    if 'revenue' in data:
        movieObject["boxOffice"] = data["revenue"]
        print('boxOffice: ' + str(data['revenue']))
        
    if 'poster_path' in data:
        movieObject["poster"] = image_path + data["poster_path"]
        print('poster: ' + data['poster_path'])
        
    if 'genres' in data:
        genres = list()
        for genre in data['genres']:
            temp_genre = genre['name'].upper().replace('-', '_').replace(' ', '')
            genres.append(temp_genre)
        movieObject["genre"] = genres

    if 'runtime' in data:
        movieObject["duration"] = data["runtime"]
        print('duration: ' + str(data['runtime']))
        
    if 'images' in data:
        images = list()
        if 'backdrops' in data['images']:
            for image in data['images']['backdrops']:
                path = image_path + image['file_path']
                images.append(path)
            movieObject['photos']=images
        
    if 'videos' in data:
        videos = list()
        for video in data['videos']['results']:
            temp_path = video['key']
            videos.append(temp_path)
        movieObject['videos']=videos

    
    response2 = requests.get(url=cast_url1 + str(i)+cast_url2)
    cast_data = response2.json()
    
    casts = list()
    if 'cast' in cast_data:
        for actor in cast_data['cast'][:5]:
            castObject = {}
            if 'name' in actor: 
                nameTokens = actor['name'].strip().split(' ')

                if len(nameTokens) >= 2:
                    castObject['firstName'] = nameTokens[0] 
                    castObject['lastName'] = ''.join(nameTokens[1:])
                    
                else:
                    castObject['firstName'] = nameTokens[0]

            print(actor)
            photo_path = image_path + str(actor['profile_path'])
            castObject['imageURL'] = photo_path
                
            casts.append(castObject)
        movieObject['cast'] = casts

    movieObject['contentType'] = 'MOVIE'

    requests.post(url=linden_url, json=dict(token=sys.argv[1], obj=movieObject))


    