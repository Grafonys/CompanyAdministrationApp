package com.homework.company.dao.employee;

import com.homework.company.dao.AbstractDaoImpl;
import com.homework.company.dao.Status;
import com.homework.company.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl extends AbstractDaoImpl implements EmployeeService {
    @Override
    public Status save(Employee employee) {
        String query = String.format("INSERT INTO employees_t " +
                        "(name, mid_name, surname, note, profession_id, department_id) " +
                        "VALUES ('%s', '%s', '%s', '%s', %d, %d);",
                employee.getName(),
                employee.getMidName(),
                employee.getSurname(),
                employee.getNote(),
                employee.getProfessionId(),
                employee.getDepartmentId()
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
    public List<Employee> all() {
        String query = "SELECT * FROM employees_t ORDER BY id;";
        List<Employee> employees = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    employees.add(new Employee(resultSet));
                }
                return employees;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String query = "SELECT * FROM employees_t WHERE id = " + id + ";";

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    return Optional.of(new Employee(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status update(Employee employee) {
        String query = String.format(
                "UPDATE employees_t SET name = '%s', mid_name = '%s', surname = '%s', " +
                        "note = '%s', profession_id = %d, department_id = %d WHERE id = %d;",
                employee.getName(),
                employee.getMidName(),
                employee.getSurname(),
                employee.getNote(),
                employee.getProfessionId(),
                employee.getDepartmentId(),
                employee.getId()
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
        String query  = "DELETE FROM employees_t WHERE id = " + id + ";";

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.SQL_EXCEPTION;
        }
    }
}
