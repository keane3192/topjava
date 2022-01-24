<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.time.LocalTime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h1>Displaying Student List</h1>
<table border="1" width="500" align="center">
    <tr bgcolor="00FF7F">
        <th><b>Date</b></th>
        <th><b>Description</b></th>
        <th><b>Calories</b></th>
    </tr>

    <%
        List<Meal> meals =
                (List<Meal>) request.getAttribute("list");
        final LocalTime startTime = LocalTime.of(7, 0);
        final LocalTime endTime = LocalTime.of(12, 0);

        List<MealTo> sorted = MealsUtil.filteredByStreams(meals, startTime, endTime, 2000);


        for (Meal m : meals) {%>
    <%-- Arranging data in tabular form
    --%>
    <%
        String color = "green";
        for (MealTo s : sorted) {
            if (s.getDate().isEqual(m.getDate())) {
                if (s.getExcess()) {
                    color = "red";
                }
            }
    %>
    <%}%>
    <tr style="color: <%=color%>">
        <td><%=m.getDate()%>
        </td>
        <td><%=m.getDescription()%>
        </td>
        <td><%=m.getCalories()%>
        </td>

    </tr>
    <%}%>
</table>

</body>
</html>