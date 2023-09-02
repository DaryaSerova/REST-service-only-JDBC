package ru.aston.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;
import ru.aston.service.OrderService;
import ru.aston.service.PermissionService;
import ru.aston.service.UserService;
import ru.aston.service.impl.PermissionServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PermissionServlet", value = "/permission")
public class PermissionServlet extends HttpServlet {

    public UserService userService;
    public OrderService orderService;
    public PermissionService permissionService = new PermissionServiceImpl();
    public ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var responseBody = addPermission(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var responseBody = getPermissionOfUser(req, resp);
        flushObjectToResponse(resp, responseBody);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();
        try {
            deletePermission(req, resp);
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }


    private PermissionDto addPermission(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String permissionId = req.getParameter("permissionId");
        String userId = req.getParameter("userId");

        if (userId == null || permissionId == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }

        Long permId = Long.parseLong(permissionId);
        Long usId = Long.parseLong(userId);

        return permissionService.addPermission(permId, usId);

    }

    private UserPermissionDto getPermissionOfUser(HttpServletRequest req, HttpServletResponse resp) {

        String userId = req.getParameter("userId");
        if (userId.isEmpty()) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long usId = Long.parseLong(userId);
        return permissionService.getPermissionOfUser(usId);
    }


    private void deletePermission(HttpServletRequest req, HttpServletResponse resp) {

        String permId = req.getParameter("permissionId");
        String usId = req.getParameter("userId");

        if (permId == null || usId == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }

        Long permissionId = Long.parseLong(permId);
        Long userId = Long.parseLong(usId);

        permissionService.deletePermission(permissionId, userId);
    }

    private void flushObjectToResponse(HttpServletResponse resp, Object obj) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(obj));
        out.flush();
    }
}

