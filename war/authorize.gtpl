<% include '/WEB-INF/includes/header.gtpl' %>
<p><a href="<%= request.authorizationURL %>" target="_blank">Get an authorization token from Yammer
    and enter it into the box below. Click here to start.</a></p>
<p>
  <form action="/authorize.groovy" method="GET">
    <input type="text" name="key" placeholder="API key from authorization" /><br />
    <input type="submit" value="Authorize WhatYa" />
  </form>
</p>
<% include '/WEB-INF/includes/footer.gtpl' %>

