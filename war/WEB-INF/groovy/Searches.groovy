import com.google.appengine.api.datastore.Entity
import groovy.json.JsonSlurper
import groovyx.gaelyk.datastore.Key
import groovyx.gaelyk.GaelykBindings
import org.apache.commons.codec.net.URLCodec
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.oauth.OAuthService

@GaelykBindings class Searches {
  int count(String term, Token accessToken) {
    def search = new URLCodec().encode(term)
    def url = 'https://www.yammer.com/api/v1/search.json?search=' +
        "${search}&num_per_page=1"
    OAuthService service = new Auth().makeService()
    OAuthRequest aRequest = new OAuthRequest(Verb.GET, url)
    service.signRequest(accessToken, aRequest)
    Response response = aRequest.send()
    def slurper = new JsonSlurper()
    def results = slurper.parseText(response.getBody())
    return results.count.messages
  }
  
  List<Entity> find() {
    datastore.execute {
      from 'Search'
    }
  }
  
  void save(List<String> terms, String authorizationID) {
    datastore.withTransaction {
      try {
        datastore.get('Search', termsKey)
      }
      catch (EntityNotFoundException) {
        String termsKey = terms.join('|')
        Search search = new Search(terms:terms, authorizationID:authorizationID)
        def entity = search as Entity
        entity.save()
      }
    }
  }
  
  String makeURL(List<String> terms) {
    def result = new StringBuilder('/trending?')
    def codec = new URLCodec()
    terms.eachWithIndex { term, index ->
      if (index != 0) {
        result.append('&')
      }
      result.append("search${index}=${codec.encode(term)}")
    }
    return result
  }
}
