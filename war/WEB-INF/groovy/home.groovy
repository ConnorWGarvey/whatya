if (!new Auth().authorized(request, response)) {
  forward 'redirect.gtpl'
}

def searches = new Searches()
def pastSearches = searches.find()
if (!pastSearches.isEmpty()) pastSearches = pastSearches[0..Math.min(pastSearches.size() - 1, 9)]
request['searches'] = pastSearches.collect { [terms:it, url:searches.makeURL(it.terms)] }
forward 'WEB-INF/pages/index.gtpl'
