package com.homework.company.controllers;

import java.io.*;
import java.util.List;

import com.homework.company.dao.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.homework.company.dao.profession.ProfessionService;
import com.homework.company.dao.profession.ProfessionServiceImpl;
import com.homework.company.model.Profession;

@WebServlet(value = "/professions")
public class ProfessionsServlet extends HttpServlet {
    private ProfessionService ps;

    public void init() {
        this.ps = new ProfessionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            req.setAttribute("professionToUpdate", ps.findById(Long.parseLong(id)).get());
        }

        voidGet(req, resp);
    }

    private void voidGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Profession> all = ps.all();
        req.setAttribute("all", all);
        req.getRequestDispatcher("/professions.jsp").forward(req, resp);
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
        Profession profession = new Profession(req.getParameter("profession"), req.getParameter("note"));
        Status status = ps.save(profession);

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Insert SQL exception!");
        }
        voidGet(req, resp);
    }

    private void put(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profession professionFromFront = new Profession(req);
        Status status = ps.update(professionFromFront);

        if (status == Status.SQL_EXCEPTION) {
            req.setAttribute("warning", "Update SQL exception!");
        }
        voidGet(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Status status = ps.delete(Long.parseLong(req.getParameter("id")));

        switch (status) {
            case AED -> req.setAttribute(
                    "warning",
                    "Cannot delete profession because of active dependency in employees_t !");
            case SQL_EXCEPTION -> req.setAttribute("warning", "Delete SQL exception!");
        }
        voidGet(req, resp);
    }
}