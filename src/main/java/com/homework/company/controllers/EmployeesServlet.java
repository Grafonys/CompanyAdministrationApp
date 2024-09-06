package com.homework.company.controllers;

import com.homework.company.dao.department.DepartmentService;
import com.homework.company.dao.employee.EmployeeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homework.company.dao.Status;
import com.homework.company.dao.department.DepartmentServiceImpl;
import com.homework.company.dao.employee.EmployeeService;
import com.homework.company.dao.profession.ProfessionService;
import com.homework.company.dao.profession.ProfessionServiceImpl;
import com.homework.company.model.Department;
import com.homework.company.model.Employee;
import com.homework.company.model.Profession;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/employees")
public class EmployeesServlet extends HttpServlet {
    private ProfessionService ps;
    private DepartmentService ds;
    private EmployeeService es;

    public void init() {
        this.ps = new ProfessionServiceImpl();
        this.ds = new DepartmentServiceImpl();
        this.es = new EmployeeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            req.setAttribute("employeeToUpdate", es.findById(Long.parseLong(id)).get());
        }
        voidGet(req, resp);
    }

    private void voidGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Profession> professions = ps.all();
        req.setAttribute("professions", professions);
        List<Department> departments = ds.all();
        req.setAttribute("departments", departments);
        List<Employee> all = es.all();
        req.setAttribute("all", all);
        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        switch (type) {
            case "post" -> post(req, resp);
            case "put" -> put(req, resp);
            case "delete" -> delete(req, resp);
            default -> doGet(req, resp);
        }
    }

    private void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long departmentId = req.getParameter("employee_department_id").equals("null")
                ? null
                : Long.parseLong(req.getParameter("employee_department_id"));

        Employee employee = new Employee(
                req.getParameter("employee_name"),
                req.getParameter("employee_mid_name"),
                req.getParameter("employee_surname"),
                req.getParameter("note"),
                Long.parseLong(req.getParameter("employee_profession_id")),
                departmentId
        );
        Status status = es.save(employee);

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Insert SQL exception!");
        }
        voidGet(req, resp);
    }

    private void put(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = new Employee(req);
        Status status = es.update(employee);

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Update SQL exception!");
        }
        voidGet(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Status status = es.delete(Long.parseLong(req.getParameter("id")));

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Delete SQL exception!");
        }
        voidGet(req, resp);
    }
}
