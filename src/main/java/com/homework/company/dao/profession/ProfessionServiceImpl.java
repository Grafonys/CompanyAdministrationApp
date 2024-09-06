package com.homework.company.dao.profession;

import com.homework.company.dao.Status;
import com.homework.company.dao.employee.EmployeeServiceImpl;
import com.homework.company.model.Employee;
import com.homework.company.dao.AbstractDaoImpl;
import com.homework.company.dao.employee.EmployeeService;
import com.homework.company.model.Profession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessionServiceImpl extends AbstractDaoImpl implements ProfessionService {

    @Override
    public Status save(Profession profession) {
        String query = String.format("INSERT INTO professions_t (profession, note) VALUES ('%s','%s');",
                profession.getProfession(), profession.getNote());

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.SQL_EXCEPTION;
        }
    }

    @Override
    public List<Profession> all() {
        String query = "SELECT * FROM professions_t ORDER BY id;";
        List<Profession> professions = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    professions.add(new Profession(resultSet));
                }
                return professions;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Profession> findById(Long id) {
        String query = "SELECT * FROM professions_t WHERE id = " + id + ";";

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    return Optional.of(new Profession(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status update(Profession profession) {
        String query = String.format(
                "UPDATE professions_t SET profession = '%s', note = '%s' WHERE id = %d;",
                profession.getProfession(),
                profession.getNote(),
                profession.getId()
        );

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.SQL_EXCEPTION;
        }
    }

    @Override
    public Status delete(Long id) {
        if (isHaveActiveEmployeeDependency(id)) { return Status.AED; }
        String query  = "DELETE FROM professions_t WHERE id = " + id + ";";

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.SQL_EXCEPTION;
        }
    }

    private boolean isHaveActiveEmployeeDependency(Long id) {
        EmployeeService es = new EmployeeServiceImpl();
        List<Employee> employees = es.all();

        for (Employee employee : employees) {
            if (employee.getProfessionId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
