import com.google.appengine.api.datastore.Entity
import java.util.UUID
import javax.servlet.http.Cookie
import org.scribe.builder.api.YammerApi
import org.scribe.builder.ServiceBuilder
import org.scribe.model.Token
import org.scribe.oauth.OAuthService

String saveAuthorization(Token requestToken) {
  String authorizationID = UUID.randomUUID().toString()
  Entity authorization = new Entity('authorization')
  authorization['id'] = authorizationID
  authorization['requestToken'] = requestToken.token
  authorization['requestTokenSecret'] = requestToken.secret
  authorization.save()
  return authorizationID
}

def service = new Auth().makeService()
Token requestToken = service.getRequestToken()
request['authorizationURL'] = service.getAuthorizationUrl(requestToken)[1..-2]
def authorizationID = saveAuthorization(requestToken)
def cookie = new Cookie('authorizationID', authorizationID)
cookie.setPath('/')
cookie.setMaxAge(365 * 24 * 60 * 60)
response.addCookie(cookie)
forward 'WEB-INF/pages/authorize.gtpl'

