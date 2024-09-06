package com.homework.company.model;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
public class Employee extends BaseEntity{

    private String name;
    private String midName;
    private String surname;
    private Long professionId;
    private Long departmentId;

    public Employee(String name, String midName, String surname, String note, Long professionId, Long departmentId) {
        super(note);
        this.name = name;
        this.midName = midName;
        this.surname = surname;
        this.professionId = professionId;
        this.departmentId = departmentId;
    }

    public Employee(ResultSet resultSet) {
        super(resultSet);
        try {
            this.name = resultSet.getString("name");
            this.midName = resultSet.getString("mid_name");
            this.surname = resultSet.getString("surname");
            this.professionId = resultSet.getLong("profession_id");
            this.departmentId = resultSet.getLong("department_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee(HttpServletRequest req) {
        super(Long.parseLong(req.getParameter("id")), req.getParameter("note"));
        this.name = req.getParameter("employee_name");
        this.midName = req.getParameter("employee_mid_name");
        this.surname = req.getParameter("employee_surname");
        this.professionId = Long.parseLong(req.getParameter("employee_profession_id"));
        String departmentId = req.getParameter("employee_department_id");
        this.departmentId = departmentId.equals("null") ? null : Long.parseLong(departmentId);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", midName='" + midName + '\'' +
                ", surname='" + surname + '\'' +
                ", professionId=" + professionId +
                ", departmentId=" + departmentId +
                '}';
    }
}
