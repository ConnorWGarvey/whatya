get '/',                  forward: '/home.groovy'
get '/authorize/confirm', forward: '/authorize.groovy'
get '/authorize/request', forward: '/requestAuthorize.groovy'
get '/trending',          forward: '/trending.groovy'
get '/failure',           forward: '/WEB-INF/pages/failure.gtpl'

get '/favicon.ico',       redirect: '/images/gaelyk-small-favicon.png'
