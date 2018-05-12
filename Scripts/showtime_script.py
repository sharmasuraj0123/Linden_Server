import json
import datetime
import requests

now =  datetime.datetime.now()

date = str(now.year) + "-" + str(now.month) + "-" + str(now.day)


url = "http://data.tmsapi.com/v1.1/movies/showings?startDate="+date+"&zip=11790&radius=10&units=mi&api_key=f2uhq7vfwfq2xxs7stvfazfg"

data = requests.get(url).json()

for x in data:
    print(x)
    break

