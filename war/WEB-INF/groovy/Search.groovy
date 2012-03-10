import groovyx.gaelyk.datastore.Key

class Search {
  List<String> terms
  String authorizationID
  
  @Key String getId() {
    return terms.join('|')
  }
}
