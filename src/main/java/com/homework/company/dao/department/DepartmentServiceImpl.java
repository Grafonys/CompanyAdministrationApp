package com.homework.company.dao.department;

import com.homework.company.dao.AbstractDaoImpl;
import com.homework.company.dao.Status;
import com.homework.company.dao.employee.EmployeeService;
import com.homework.company.dao.employee.EmployeeServiceImpl;
import com.homework.company.model.Department;
import com.homework.company.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentServiceImpl extends AbstractDaoImpl implements DepartmentService {
    @Override
    public Status save(Department department) {
        try (Statement statement = getConnection().createStatement()) {
            String query = String.format("INSERT INTO departments_t (department, note, parent_id) VALUES ('%s', '%s', %s);",
                    department.getDepartment(),
                    department.getNote(),
                    department.getParentId() == null
                            ? "null"
                            : String.valueOf(department.getParentId()));
            statement.executeUpdate(query);
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.SQL_EXCEPTION;
        }
    }

    @Override
    public List<Department> all() {
        String query = "SELECT * FROM departments_t ORDER BY id;";
        List<Department> departments = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    departments.add(new Department(resultSet));
                }
                return departments;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        String query = "SELECT * FROM departments_t WHERE id = " + id + ";";

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    return Optional.of(new Department(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status update(Department department) {
        String query = String.format(
                "UPDATE departments_t SET department = '%s', note = '%s', parent_id = %d WHERE id = %d;",
                department.getDepartment(),
                department.getNote(),
                department.getParentId(),
                department.getId()
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
        if (isHaveActiveDepartmentDependency(id)) { return Status.ADD; }
        String query  = "DELETE FROM departments_t WHERE id = " + id + ";";

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
            if (employee.getDepartmentId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHaveActiveDepartmentDependency(Long id) {
        for (Department department : all()) {
            if (department.getParentId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
