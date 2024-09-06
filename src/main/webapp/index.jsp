<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style><%@ include file="styles/homepage-styles.css"%></style>
</head>
<body>
    <div class="links_block">
        <a class="link_to_db" href="${pageContext.request.contextPath}/professions">Professions</a>
        <a class="link_to_db" href="${pageContext.request.contextPath}/departments">Departments</a>
        <a class="link_to_db" href="${pageContext.request.contextPath}/employees">Employees</a>
    </div>
</body>
</html>