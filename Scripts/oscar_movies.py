import requests
from bs4 import BeautifulSoup

omdb_url = 'http://www.omdbapi.com/'
omdb_key = '12d0adae'

page = requests.get('https://www.imdb.com/search/title?count=100&groups=oscar_best_picture_winners&sort=year,desc&ref_=nv_ch_osc_2')

soup = BeautifulSoup(page.content, 'html.parser')

movie_list = soup.find_all(class_='lister-item-header')

for movie in movie_list:
	imdb_id = movie.find('a')['href'].split('/')[2]
	
	movie_data = requests.get(omdb_url + '?i=' + imdb_id + '&apikey=' + omdb_key).json()
	print(movie_data)
	print('--------------------------------')
