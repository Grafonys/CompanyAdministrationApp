package com.homework.company.model;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
public class Profession extends BaseEntity {
    private String profession;

    public Profession(ResultSet resultSet) {
        super(resultSet);
        try {
            this.profession = resultSet.getString("profession");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Profession(String profession, String note) {
        super(note);
        this.profession = profession;
    }

    public Profession(HttpServletRequest req) {
        super(Long.valueOf(req.getParameter("id")), req.getParameter("note"));
        this.profession = req.getParameter("profession");
    }

    @Override
    public String toString() {
        return "Profession{" +
                "id='" + getId() +
                "'profession='" + profession + '\'' +
                "note='" + getNote() +
                "'}";
    }
}
