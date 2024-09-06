package com.homework.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {
    private Long id;
    private String note;

    public BaseEntity(ResultSet resultSet) {
        try {
            this.id = resultSet.getLong("id");
            this.note = resultSet.getString("note");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseEntity(String note) {
        this.note = note;
    }
}
