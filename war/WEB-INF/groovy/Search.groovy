import groovy.json.JsonSlurper
import org.apache.commons.codec.net.URLCodec
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.oauth.OAuthService

class Search {
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
}
