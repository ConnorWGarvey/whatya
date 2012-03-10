<% include '/WEB-INF/includes/header.gtpl' %>
<p>What's trending on Yammer?</p>
<p>
  <form action="/trending" method="GET">
    <input type="text" name="search0" placeholder="This"><br>
    <input type="text" name="search1" placeholder="or that"><br>
    <input type="submit" value="See what's trending">
  </form>
</p>
<p>
  Some ideas:<br />
<% def searches = request.searches
   if (!searches.isEmpty()) {
     searches.each { %>
  <a href="<%=it.url%>"><%=it.terms.terms.join(' or ')%></a><br />
<%   }
   }
   else { print 'No ideas yet...' } %>
</p>
<% include '/WEB-INF/includes/footer.gtpl' %>

