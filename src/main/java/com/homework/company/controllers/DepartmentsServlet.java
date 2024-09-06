package com.homework.company.controllers;

import com.homework.company.dao.Status;
import com.homework.company.dao.department.DepartmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homework.company.dao.department.DepartmentServiceImpl;
import com.homework.company.model.Department;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/departments")
public class DepartmentsServlet extends HttpServlet {
    DepartmentService ds;

    @Override
    public void init() {
        ds = new DepartmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            req.setAttribute("departmentToUpdate", ds.findById(Long.parseLong(id)).get());
        }

        voidGet(req, resp);
    }

    private void voidGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> all = ds.all();
        req.setAttribute("all", all);
        req.getRequestDispatcher("/departments.jsp").forward(req, resp);
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
        String parentId = req.getParameter("parentId");
        Department department = new Department(
                req.getParameter("department"),
                req.getParameter("note"),
                parentId.equals("null") ? null : Long.parseLong(parentId)
        );
        Status status = ds.save(department);

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Insert SQL exception!");
        }
        voidGet(req, resp);
    }

    private void put(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Department department = new Department(req);
        Status status = ds.update(department);

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Update SQL exception!");
        }
        voidGet(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Status status = ds.delete(Long.parseLong(req.getParameter("id")));

        switch (status) {
            case AED -> req.setAttribute(
                    "warning",
                    "Cannot delete department because of active dependency in employees_t !");
            case ADD -> req.setAttribute(
                    "warning",
                    "Cannot delete department because of active dependency in department_t !");
            case SQL_EXCEPTION -> req.setAttribute(
                    "warning", "Delete SQL exception!");
        }
        voidGet(req, resp);
    }
}
