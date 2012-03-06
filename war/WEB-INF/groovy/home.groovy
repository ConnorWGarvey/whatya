if (!new Auth().authorized(request, response)) {
  forward 'redirect.gtpl'
}

def search = new Search()
request['searches'] = search.find().collect { [terms:it.terms, url:search.makeURL(it.terms)] }
forward 'index.gtpl'
