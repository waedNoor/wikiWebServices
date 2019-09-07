# wikiWebServices
<<<<<<< master

Wikipedia is the largest online encyclopedia, with millions of Articles in many
different articles.There is currently more than 500,000 articles in Arabic.

Wikipedia provides an API for searching and retrieving articles, documentation can
be found here https://www.mediawiki.org/wiki/API:Search.

First thing we call the Wiki API to retrieve articles related to (Amman, Jordan)and save the retrieved information in database.

then creare two restful API:
1- #search API in wiki articles:
https://github.com/waedNoor/wikiWebServices/wikiApi/search/find?query={}
as above api we search in retrieved data in db to find articles based on title and text/snippet.

2- #statistics about articles

https://github.com/waedNoor/wikiWebServices/wikiApi/wordCount/{function}
as above api we do statistics about articles including :min, max,Mid size of articles based on "wordCount + total of all word counts.
if you hit "https://github.com/waedNoor/wikiWebServices/wikiApi/wordCount/max", this api will return the biggest size of (word count + total of word counts)
Also if you hit "https://github.com/waedNoor/wikiWebServices/wikiApi/wordCount/min", this api will return the Smallest size of (word count + total of word counts)
And if you hit  "https://github.com/waedNoor/wikiWebServices/wikiApi/wordCount/mid", this api will return the midian size of (word count + total of word counts) in this api i suppose the middle value depend on the number of articles that's retrieved
i meant if number of articles is odd , the midian value is the middle one
but if number of articles are not odd , the median value is the "the two word counts in the middle ".
