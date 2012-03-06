import groovy.json.JsonSlurper
import org.apache.commons.codec.net.URLCodec
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Verb
import org.scribe.oauth.OAuthService

def findTerms() {
  def terms = []
  for (i in 0..20) {
    def term = params["search${i}"]
    if (term == null) {
      break
    }
    terms << term
  }
  return terms
}

def accessToken = new Auth().findAccessToken(request, response)
if (accessToken == null) {
  forward 'redirect.gtpl'
}
def terms = findTerms()
def search = new Search()
request['counts'] = terms.collect { [it, search.count(it, accessToken)] }
forward 'trending.gtpl'
