package com.academy.it.boot.demo.controllers;


import com.academy.it.boot.demo.model.Department;
import com.academy.it.boot.demo.services.DepartmentService;
import com.academy.it.boot.demo.services.DepartmentServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/department")
public class DepartmentJsonController extends JsonController {
    private static final String ID = "id";
    private final DepartmentService service = new DepartmentServiceImp();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString = req.getParameter(ID);
        if (idAsString == null) {
            toJson(service.getAllDepartments(), resp);
        } else {
            toJson(service.getDepartment(Integer.parseInt(idAsString)), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Department department = toObject(Department.class, req);
        toJson(service.saveDepartment(department), resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Department department = toObject(Department.class, req);
        toJson(service.saveDepartment(department), resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString = req.getParameter(ID);
        if (idAsString != null) {
            toJson(service.deleteDepartment(Integer.parseInt(idAsString)), resp);
        }
    }

}
