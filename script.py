import requests
import json
import pymysql
import sys

# CMD
# sudo apt-get install python3-pymysql

# http://www.omdbapi.com/?i=tt0137523&apikey=12d0adae
omdb_url = 'http://www.omdbapi.com'
omdb_key = '12d0adae'

# https://api.themoviedb.org/3/movie/550?api_key=89a57552ff989080c44c82c3078fb543
tmdb_key = '89a57552ff989080c44c82c3078fb543'
tmdb_url = 'https://api.themoviedb.org/3/movie/'

if len(sys.argv) != 3:
	print('Please enter the command in the following format: python script.py <mysql_username> <mysql_password>')
	sys.exit()

username = sys.argv[1]
password = sys.argv[2]

try:
	db = pymysql.connect(user=username, passwd=password, host='127.0.0.1')
	cur = db.cursor()
	print('Connected to MySQL Server.')
	cur.execute('CREATE DATABASE IF NOT EXISTS linden')
	cur.execute('use linden')
	print('Using Linden')
	cur.execute('DROP TABLE IF EXISTS Movies')
	# create movie table
	cur.execute("""CREATE TABLE IF NOT EXISTS
			Movies(
			Id 	VARCHAR(10) NOT NULL,
			Name 	VARCHAR(100) NOT NULL,
			Year 	INTEGER NOT NULL,
			PRIMARY KEY(Id))
			""")
	print('Table Created!')
	#sys.exit()

except pymysql.Error as e:
	print('MySQL Error [%d]: %s' % (e.args[0], e.args[1]))
	sys.exit()


movie_id = 0
counter = 0

while(True):
	movie_id += 1
	# Hitting the tmdb API
	try:
		tmdb_response = requests.get(url = tmdb_url + str(movie_id) +'?api_key=' +tmdb_key)
		tmdb_data = tmdb_response.json()
		print("Success: 1")
	except:
		print(tmdb_response)
		continue

	if 'status_code' in tmdb_data:
		# movie_id += 1
		# print(tmdb_response)
		continue
	
	try:
		# extrating the imdb ID
		imdb_id = tmdb_data['imdb_id']

		# Hitting the omdb API
		omdb_response = requests.get(url = omdb_url + '?i=' + imdb_id + '&apikey=' + omdb_key)
		data = omdb_response.json()
		print("Success 2")
	except:
		print(omdb_response)
		continue
	
	if 'Title' not in data:
		print('ERROR: NO TITLE FOUND')
		print(data)
		sys.exit()
		continue

	try:
		cur.execute("INSERT INTO Movies (Id, Name, Year) VALUES(\""+imdb_id+"\",\" "+data['Title']+"\", "+data['Year']+")")
	except:
		print(imdb_id+"\",\" "+data['Title']+"\", "+data['Year'])
		# sys.exit()

	db.commit()
	
	print('Number: ' + str(movie_id))
	if 'Title' in data: 	print('Title: ' + data['Title'])
	if 'Released' in data: 	print('Released: ' + data['Released'])
	if 'Year' in data:	print('Year: ' + data['Year'])
	if 'Runtime' in data:	print('Runtime: ' + data['Runtime'])
	if 'Poster' in data:	print('Poster: ' + data['Poster'])
	if 'Director' in data:	print('Director: ' + data['Director'])
	if 'Actors' in data:	print('Actors: ' + data['Actors'])
	if 'Language' in data:	print('Language: ' + data['Language'])
	if 'Plot' in data:	print('Plot: ' + data['Plot'])
	if 'Genre' in data:	print('Genre: ' + data['Genre'])
	if 'Rated' in data:	print('Rating: ' + data['Rated'])
	print('----------------------------------------------------------')
	# movie_id += 1
	counter += 1


cur.close()
db.close()


