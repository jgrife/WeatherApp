# WeatherApp

- I am using this [feed](https://api.zippopotam.us/us/98115 "feed") for postal code lookup, if you enter all digits in the input field and click the submit button. If you don't enter a valid postal code, or if the feed returns more than one `places` for a postal code, then I am not dealing with that, at this point and you just won't get a result. However, for a valid postal code, I then take the response from this call and use the resolved latitude/logitude to make City lookup call (see below).
- I am using this [feed](https://www.metaweather.com/api/#location "feed") for the City lookup. This feed takes either a latitude/longitude (see above), or a city query.
 - If this feed returns [no results](https://www.metaweather.com/api/location/search/?query=zzz "no results"), then nothing is displayed to the user.
 - If it returns exactly [one result](https://www.metaweather.com/api/location/search/?query=seattle "one result"), then I take that locations id `woeid` and use that in the next call to get the Forecast.
 - If it returns [more than one result](https://www.metaweather.com/api/location/search/?query=sea "more than one result") then the list of those locations are pushed into a `PopupMenu` in the view.
- Once I have the locations id `woeid`, then I make a call to this [feed](https://www.metaweather.com/api/location/44418/ "feed"), to get the forecast and populate the view with it.
