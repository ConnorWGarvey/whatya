if (!new Auth().authorized(request, response)) {
  forward 'redirect.gtpl'
}

forward 'index.gtpl'
