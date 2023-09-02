package ru.aston.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.dto.UserDto;
import ru.aston.dto.UserDtoWithOrders;
import ru.aston.service.UserService;
import ru.aston.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {

    public UserService userService = new UserServiceImpl();
    public ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var responseBody = createUser(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var responseBody = getUserById(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var responseBody = updateUser(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            deleteUser(req, resp);
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }

    private UserDto createUser(HttpServletRequest req, HttpServletResponse resp) {
        NewUserDto newUserDto = new NewUserDto();

        String name = req.getParameter("name");
        if (name.isEmpty()) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        newUserDto.setName(name);
        return userService.createUser(newUserDto);

    }


    private UserDtoWithOrders getUserById(HttpServletRequest req, HttpServletResponse resp) {

        String userId = req.getParameter("userId");

        if (userId.isEmpty()) {
            throw new RuntimeException("An empty value cannot be passed.");
        }

        Long id = Long.parseLong(userId);
        return userService.getUserById(id);
    }


    private UserDto updateUser(HttpServletRequest req, HttpServletResponse resp) {

        String userId = req.getParameter("userId");
        String name = req.getParameter("name");

        UpdateUserDto updateUserDto = new UpdateUserDto();

        if (userId == null || name == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long id = Long.parseLong(userId);
        updateUserDto.setName(name);
        return userService.updateUser(updateUserDto, id);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {

        String usId = req.getParameter("userId");

        if (usId == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long userId = Long.parseLong(usId);

        userService.deleteUserById(userId);
    }

    private void flushObjectToResponse(HttpServletResponse resp, Object obj) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(obj));
        out.flush();
    }
}

