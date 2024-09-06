<%@ page import="com.homework.company.model.Employee" %>
<%@ page import="com.homework.company.model.Profession" %>
<%@ page import="com.homework.company.model.Department" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees</title>
    <style><%@ include file="styles/directory-styles.css"%></style>
</head>
<body>
    <div class="main_block">
        <a class="redirect_btn" href="${pageContext.request.contextPath}/">Home</a>
        <form class="edit_form" method="post" action="${pageContext.request.contextPath}/employees">
            <%
                Employee toUpdate = (Employee) request.getAttribute("employeeToUpdate");
            %>
            <input
              type="number"
              name="id"
              value="<%=toUpdate == null ? -1 : toUpdate.getId()%>"
              hidden
              readonly
            >
            <div class="edit_input_container">
                <span class="input_name">Name</span>
                <input
                  class="data_input"
                  type="text"
                  placeholder="name"
                  name="employee_name"
                  value="<%=toUpdate == null ? "" : toUpdate.getName()%>"
                  required
                >
            </div>
            <div class="edit_input_container">
                <span class="input_name">Middle name</span>
                <input
                  class="data_input"
                  type="text"
                  placeholder="mid name"
                  name="employee_mid_name"
                  value="<%=toUpdate == null ? "" : toUpdate.getMidName()%>"
                  required
                >
            </div>
            <div class="edit_input_container">
                <span class="input_name">Surname</span>
                <input
                  class="data_input"
                  type="text"
                  placeholder="surname"
                  name="employee_surname"
                  value="<%=toUpdate == null ? "" : toUpdate.getSurname()%>"
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
                <span class="input_name">Profession</span>
                <select class="selector" name="employee_profession_id">
                    <%
                        List<Profession> professions = (List<Profession>) request.getAttribute("professions");
                        Long toUpdateProfessionId = toUpdate == null ? -1L : toUpdate.getProfessionId();
                        for (Profession profession : professions) {
                    %>
                    <option
                      value="<%=profession.getId()%>"
                      <%=profession.getId().equals(toUpdateProfessionId) ? "selected" : ""%>
                    >
                    <%=profession.getProfession()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="edit_input_container">
                <span class="input_name">Department</span>
                <select class="selector" name="employee_department_id">
                    <%
                        Long toUpdateDepartmentId = toUpdate == null ? -1L : toUpdate.getDepartmentId();
                    %>
                    <option
                      value="null"
                      <%=toUpdateDepartmentId == null ? "selected" : ""%>
                    >
                    </option>
                    <%
                        List<Department> departments = (List<Department>) request.getAttribute("departments");
                        for (Department department : departments) {
                    %>
                    <option
                      value="<%=department.getId()%>"
                      <%=department.getId().equals(toUpdateDepartmentId) ? "selected" : ""%>
                    >
                    <%=department.getDepartment()%>
                    </option>
                    <%
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
            <th>name</th>
            <th>middleName</th>
            <th>surname</th>
            <th>note</th>
            <th>professionId</th>
            <th>departmentId</th>
            <th>update</th>
            <th>delete</th>
            </thead>
            <tbody>
            <%
                List<Employee> all = (List<Employee>) request.getAttribute("all");
                for (Employee employee : all) {
            %>
            <tr>
                <td><%=employee.getId()%></td>
                <td><%=employee.getName()%></td>
                <td><%=employee.getMidName()%></td>
                <td><%=employee.getSurname()%></td>
                <td><%=employee.getNote()%></td>
                <td><%=employee.getProfessionId()%></td>
                <td><%=employee.getDepartmentId() == null ? "null" : employee.getDepartmentId()%></td>
                <td>
                    <a class="edit_link" href="${pageContext.request.contextPath}/employees?id=<%=employee.getId()%>">update</a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/employees">
                        <input type="number" name="id" value="<%=employee.getId()%>" hidden readonly>
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
