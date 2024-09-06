<%@ page import="com.homework.company.model.Department" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Departments</title>
    <style><%@ include file="styles/directory-styles.css"%></style>
</head>
<body>
    <div class="main_block">
        <a class="redirect_btn" href="${pageContext.request.contextPath}/">Home</a>
        <form class="edit_form" method="post" action="${pageContext.request.contextPath}/departments">
            <%
                Department toUpdate = (Department) request.getAttribute("departmentToUpdate");
                boolean isRoot = false;

                if (toUpdate != null) {
                    isRoot = toUpdate.getParentId() == null;
                }
            %>
            <input
                    type="number"
                    name="id"
                    value="<%=toUpdate == null ? -1 : toUpdate.getId()%>"
                    hidden
                    readonly
            >
            <div class="edit_input_container">
                <span class="input_name">Department</span>
                <input
                        class="data_input"
                        type="text"
                        placeholder="department"
                        name="department"
                        value="<%=toUpdate == null ? "" : toUpdate.getDepartment()%>"
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
            <div class="edit_input_container">
                <span class="input_name">Parent id</span>
                <select class="selector" name="parentId">
                    <option value="null" <%=isRoot ? "selected" : ""%>>root</option>
                    <%
                        List<Department> all = (List<Department>) request.getAttribute("all");
                        Long toUpdateParentId = toUpdate == null ? -1L : toUpdate.getParentId();
                        Long excludedId = toUpdate == null ? -1L : toUpdate.getId();
                        for (Department department: all) {
                            if (!department.getId().equals(excludedId)) {
                    %>
                    <option value="<%=department.getId()%>"
                            <%=department.getId().equals(toUpdateParentId) ? "selected" : ""%>
                    >
                        <%=department.getDepartment()%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
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
            <th>department</th>
            <th>note</th>
            <th>parentId</th>
            <th>update</th>
            <th>delete</th>
            </thead>

            <tbody>
            <%
                for (Department department: all) {
            %>
            <tr>
                <td><%=department.getId()%></td>
                <td><%=department.getDepartment()%></td>
                <td><%=department.getNote()%></td>
                <td><%=department.getParentId() == null ? "null" : department.getParentId()%></td>
                <td>
                    <a class="edit_link" href="${pageContext.request.contextPath}/departments?id=<%=department.getId()%>">update</a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/departments">
                        <input type="number" name="id" value="<%=department.getId()%>" hidden readonly>
                        <input type="text" name="type" value="delete" hidden readonly>
                        <input class="edit_btn" type="submit" value="delete">
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>
