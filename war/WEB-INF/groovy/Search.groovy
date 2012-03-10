import groovyx.gaelyk.datastore.Key

class Search {
  String authorizationID
  int count
  Date created
  Date lastRun
  List<String> terms
  
  public Search() {
    count = 1
    Date now = new Date()
    created = now
    lastRun = now
  }
  
  @Key String getId() {
    return terms.join('|')
  }
}
