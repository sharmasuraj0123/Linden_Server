import requests
import sys
import time

tmdb_key = '89a57552ff989080c44c82c3078fb543'
tmdb_url = 'https://api.themoviedb.org/3/tv/'

linden_url = 'http://127.0.0.1:8080/admin/addTvShow'

tv_id_list = [1421, 42009, 62560, 60708, 1418, 1668, 1405, 2288, 62816, 61889, 60059]

image_path = 'https://image.tmdb.org/t/p/w500'

for tv_id in tv_id_list:
    print(str(tv_id) + '***************************')
    tmdbResponse = requests.get(
        url=tmdb_url + str(tv_id) + '?api_key=' + tmdb_key + '&append_to_response=images,videos,credits')
    data = tmdbResponse.json()

    numberOfSeasons = data.get('number_of_seasons')
    tv_obj = {'name': data.get('name'), 'releaseDate': data.get('first_air_date'), 'details': data.get('overview'),
              'numberOfSeasons': numberOfSeasons, 'poster': (image_path + data.get('poster_path')), 'created_by': []}
    # created by
    created_by = data.get('created_by')
    for i in range(0, len(created_by)):
        tv_obj['created_by'].append(created_by[i].get('name'))

    # GENRES
    genres = data.get('genres')
    tv_obj['genre'] = []
    for i in range(0, len(genres)):
        if genres[i].get('name').find('&') == -1:
            tv_obj['genre'].append(genres[i].get('name').upper())

    # images
    if 'images' in data:
        images = list()
        if 'backdrops' in data['images']:
            for image in data['images']['backdrops']:
                path = image_path + image['file_path']
                images.append(path)
            tv_obj['photos'] = images

    # videos
    if 'videos' in data:
        videos = list()
        for video in data['videos']['results']:
            temp_path = video['key']
            videos.append(temp_path)
        tv_obj['videos'] = videos

    # CAST
    cast = data.get('credits').get('cast')
    casts = list()
    # tv_obj['cast'] = []
    for actor in cast[:5]:
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

    tv_obj['cast'] = casts

    # print(tv_obj)
    # SEASONS
    seasons = []
    for i in range(0, numberOfSeasons):
        seasons_response = requests.get(url=tmdb_url + str(tv_id) + '/season/' + str(i + 1) + '?api_key=' + tmdb_key)
        seasons_data = seasons_response.json()
        season = {}
        numberOfEpisodes = len(seasons_data.get('episodes'))
        season['numberOfEpisodes'] = numberOfEpisodes
        season['seasonNumber'] = (i + 1)
        season['releaseDate'] = seasons_data.get('air_date')
        season['poster'] = image_path + seasons_data.get('poster_path')
        season['episodes'] = []

        for j in range(0, numberOfEpisodes):
            del seasons_data.get('episodes')[j]['crew']
            del seasons_data.get('episodes')[j]['guest_stars']
            del seasons_data.get('episodes')[j]['id']
            del seasons_data.get('episodes')[j]['production_code']
            del seasons_data.get('episodes')[j]['season_number']
            del seasons_data.get('episodes')[j]['vote_average']
            del seasons_data.get('episodes')[j]['vote_count']
            del seasons_data.get('episodes')[j]['still_path']
            seasons_data.get('episodes')[j]['releaseDate'] = seasons_data.get('episodes')[j]['air_date']
            del seasons_data.get('episodes')[j]['air_date']
            seasons_data.get('episodes')[j]['episodeNumber'] = seasons_data.get('episodes')[j]['episode_number']
            del seasons_data.get('episodes')[j]['episode_number']
            seasons_data.get('episodes')[j]['details'] = seasons_data.get('episodes')[j]['overview']
            del seasons_data.get('episodes')[j]['overview']
            season['episodes'].append(seasons_data.get('episodes')[j])

        seasons.append(season)
    tv_obj['contentType'] = 'TVSHOW'
    tv_obj['seasons'] = seasons
    print(tv_obj)

    print(requests.post(url=linden_url, json=dict(token=sys.argv[1], obj=tv_obj)))

    time.sleep(2)
