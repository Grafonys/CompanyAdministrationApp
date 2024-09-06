package com.homework.company.model;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
public class Department extends BaseEntity {
    private String department;
    private Long parentId;

    public Department(ResultSet resultSet) {
        super(resultSet);
        try {
            this.department = resultSet.getString("department");
            this.parentId = resultSet.getLong("parent_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Department(String department, String note, Long parentId) {
        super(note);
        this.department = department;
        this.parentId = parentId;
    }

    public Department(HttpServletRequest req) {
        super(Long.parseLong(req.getParameter("id")), req.getParameter("note"));
        this.department = req.getParameter("department");
        String parentId = req.getParameter("parentId");
        this.parentId = parentId.equals("null") ? null : Long.parseLong(parentId);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + getId() +
                ", department='" + department + '\'' +
                "note='" + getNote() +
                ", parentId=" + parentId +
                '}';
    }
}
