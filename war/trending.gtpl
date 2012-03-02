<% include '/WEB-INF/includes/header.gtpl' %>
<%
def counts = request['counts']
%>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
  google.load("visualization", "1", {packages:["corechart"]});
  google.setOnLoadCallback(drawChart);
  function drawChart() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Term');
    data.addColumn('number', 'Mentions');
    data.addRows(<%= counts.size() %>);
<% counts.eachWithIndex() { count, index -> %>
    data.setValue(<%= index %>, 0, '<%= count[0] %>');
    data.setValue(<%= index %>, 1, <%= count[1] %>);
<% } %>
    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
    chart.draw(data, {width: 400, height: 240, hAxis: {title: 'Term'}, vAxis: {minValue: 0}});
  }
</script>
<h2><%= counts.collect { it[0] }.join(' or ') %>?</h2>
<div id="chart_div"></div>
<p><a href="/">Try another</a></p>
<% include '/WEB-INF/includes/footer.gtpl' %>

