if (!new Auth().authorized(request, response)) {
  forward 'redirect.gtpl'
}

def searches = new Searches()
request['searches'] = searches.find().collect { [terms:it.terms, url:searches.makeURL(it.terms)] }
forward 'WEB-INF/pages/index.gtpl'
