import org.scribe.model.Token
import org.scribe.model.Verifier
import org.scribe.oauth.OAuthService

def saveAccessToken(authorization, accessToken) {
  authorization['accessToken'] = accessToken.token
  authorization['accessTokenSecret'] = accessToken.secret
  authorization.save()
}

Auth auth = new Auth()
OAuthService service = auth.makeService()
def authorizationID = auth.getIDFromCookie(request)
def authorization = auth.findAuthorization(authorizationID)
Verifier verifier = new Verifier(params['key'])
Token accessToken = service.getAccessToken(new Token(authorization['requestToken'],
    authorization['requestTokenSecret']), verifier)
saveAccessToken(authorization, accessToken)
redirect '/'

