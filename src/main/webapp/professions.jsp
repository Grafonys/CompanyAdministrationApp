<%@ page import="java.util.List" %>
<%@ page import="com.homework.company.model.Profession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Professions</title>
    <style><%@ include file="styles/directory-styles.css"%></style>
</head>
<body>
    <div class="main_block">
        <a class="redirect_btn" href="${pageContext.request.contextPath}/">Home</a>
        <form class="edit_form" method="post" action="${pageContext.request.contextPath}/professions">
            <%
                Profession toUpdate = (Profession) request.getAttribute("professionToUpdate");
            %>
            <input
                    type="number"
                    name="id"
                    value="<%=toUpdate == null ? -1 : toUpdate.getId()%>"
                    hidden
                    readonly
            >
            <div class="edit_input_container">
                <span class="input_name">Profession</span>
                <input
                        class="data_input"
                        type="text"
                        placeholder="profession"
                        name="profession"
                        value="<%=toUpdate == null ? "" : toUpdate.getProfession()%>"
                        required
                >
            </div>

            <div class="edit_input_container">
                <span class="input_name">Note</span>
                <input
                        class="data_input"
                        type="text"
                        placeholder="note"
                        name="note"
                        value="<%=toUpdate == null ? "" : toUpdate.getNote()%>"
                >
            </div>

            <input
                    type="text"
                    name="type"
                    value="<%=toUpdate == null ? "post" : "put"%>"
                    hidden
                    readonly
            >
            <input class="submit_btn" type="submit" value="<%=toUpdate == null ? "save" : "update"%>">
        </form>

        <%
            String warning = (String) request.getAttribute("warning");
            if (warning != null) {
        %>
        <p class="warning"><%=warning%></p>
        <%
            }
        %>

        <table class="table">
            <thead>
            <th>id</th>
            <th>profession</th>
            <th>note</th>
            <th>update</th>
            <th>delete</th>
            </thead>
            <tbody>
            <%
                List<Profession> all = (List<Profession>) request.getAttribute("all");
                for (Profession profession: all) {
            %>
            <tr>
                <td><%=profession.getId()%></td>
                <td><%=profession.getProfession()%></td>
                <td><%=profession.getNote()%></td>
                <td>
                    <a class="edit_link" href="${pageContext.request.contextPath}/professions?id=<%=profession.getId()%>">update</a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/professions">
                        <input type="number" name="id" value="<%=profession.getId()%>" hidden readonly>
                        <input type="text" name="type" hidden readonly value="delete">
                        <input class="edit_btn" type="submit" value="delete">
                    </form>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>

</body>
</html>
