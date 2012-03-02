import com.google.appengine.api.datastore.Entity
import groovyx.gaelyk.GaelykBindings
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.scribe.builder.api.YammerApi
import org.scribe.builder.ServiceBuilder
import org.scribe.model.Token
import org.scribe.oauth.OAuthService

@GaelykBindings
class Auth {
  final String CONSUMER_KEY = AuthTokens.CONSUMER_KEY
  final String CONSUMER_SECRET = AuthTokens.CONSUMER_SECRET
  
  boolean authorized(request, response) {
    return (findAccessToken(request, response) != null)
  }
  
  private void clearAuthorizationCookie(response) {
    def cookie = new Cookie('authorizationID', '')
    cookie.setPath('/')
    cookie.setMaxAge(0)
    response.addCookie(cookie)
  }
  
  Token findAccessToken(HttpServletRequest request, HttpServletResponse response) {
    String authorizationID = getIDFromCookie(request)
    if (authorizationID == null) {
      clearAuthorizationCookie(response)
      request['url'] = '/authorize/request'
      return null
    }
    def token = findAccessToken(authorizationID)
    if (token == null) {
      clearAuthorizationCookie(response)
      request['url'] = '/authorize/request'
    }
    return token
  }
  
  Token findAccessToken(String authorizationID) {
    Entity authorization = findAuthorization(authorizationID)
    if (authorization == null) {
      return null
    }
    def accessToken = authorization['accessToken']
    def accessTokenSecret = authorization['accessTokenSecret']
    if ([accessToken, accessTokenSecret].any { it == null }) {
      return null
    }
    return new Token(accessToken, accessTokenSecret)
  }
  
  Entity findAuthorization(String authorizationID) {
    datastore.execute {
      select single
      from authorization
      where id == authorizationID
    } // FIXME: null if not exist
  }
  
  OAuthService makeService() {
    new ServiceBuilder().
        provider(YammerApi.class).
        apiKey(CONSUMER_KEY).
        apiSecret(CONSUMER_SECRET).
        build()
  }
  
  String getIDFromCookie(request) {
    assert request != null
    def cookies = request.cookies
    def cookie = cookies?.find {
      it.name == 'authorizationID'
    }
    return cookie?.value
  }
}
