package ru.aston.controller;

import ru.aston.dto.NewOrderDto;
import ru.aston.dto.NewUserDto;
import ru.aston.dto.UpdateOrderDto;
import ru.aston.dto.UpdateUserDto;
import ru.aston.service.OrderService;
import ru.aston.service.PermissionService;
import ru.aston.service.UserService;
import ru.aston.service.impl.OrderServiceImpl;
import ru.aston.service.impl.PermissionServiceImpl;
import ru.aston.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {

    public UserService userService;
    public OrderService orderService;
    public PermissionService permissionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = new UserServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.permissionService = new PermissionServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/{userId}/orders":
                createOrder(req, resp);
                break;
            case "/admin/permission/{userId}":
                addPermission(req, resp);
                break;
            default:
                createUser(req, resp);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/{userId}/orders/{orderId}":
                getOrder(req, resp);
                break;
            case "/admin/permission/":
                getPermissionOfUser(req, resp);
                break;
            case "/{userId}":
                getUser(req, resp);
                break;
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/{userId}/orders/{orderId}":
                updateOrder(req, resp);
                break;
            case "/{userId}":
                updateUser(req, resp);
                break;
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/{userId}/orders/{orderId}":
                deleteOrder(req, resp);
                break;
            case "/admin/permission/{userId}":
                deletePermission(req, resp);
                break;
            case "/{userId}":
                deleteUser(req, resp);
                break;
        }
    }

    private void addPermission(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

        String permissionId = req.getParameter("permissionId");
        String userId = req.getParameter("userId");

        if (userId != null && permissionId != null) {

            Long permId = Long.parseLong(permissionId);
            Long usId = Long.parseLong(userId);

            permissionService.addPermission(permId, usId);
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName(req.getParameter("name"));
        userService.createUser(newUserDto);
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        NewOrderDto newOrderDto = new NewOrderDto();
        newOrderDto.setName(req.getParameter("name"));
        Long userId = Long.parseLong(req.getParameter("userId"));
        orderService.createOrder(newOrderDto, userId);
    }

    private void getUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");

        if (userId.isEmpty()) {
            userService.getAllUsers();
        } else {
            Long id = Long.parseLong(userId);
            userService.getUserById(id);
        }
    }

    private void getOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderId = req.getParameter("id");
        String userId = req.getParameter("userId");

        if (orderId.isEmpty()) {
            Long usId = Long.parseLong(userId);
            orderService.getAllOrdersByUserId(usId);
        } else {
            Long id = Long.parseLong(orderId);
            orderService.getOrderById(id);
        }
    }

    private void getPermissionOfUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");

        if (userId.isEmpty()) {
            permissionService.getPermissionsOfAllUsers();
        } else {
            Long usId = Long.parseLong(userId);
            permissionService.getPermissionOfUser(usId);
        }

    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");
        String name = req.getParameter("name");

        UpdateUserDto updateUserDto = new UpdateUserDto();

        if (userId != null && name != null) {

            Long id = Long.parseLong(userId);
            updateUserDto.setName(name);

            userService.updateUser(updateUserDto, id);
        }
    }

    private void updateOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ordId = req.getParameter("orderId");
        String usId = req.getParameter("userId");
        String nameOrder = req.getParameter("name");

        UpdateOrderDto updateOrderDto = new UpdateOrderDto();

        if (ordId != null && usId != null && nameOrder != null) {

            Long orderId = Long.parseLong(ordId);
            Long userId = Long.parseLong(usId);
            updateOrderDto.setName(nameOrder);

            orderService.updateOrder(updateOrderDto, orderId, userId);
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = Long.parseLong(req.getParameter("userId"));
        userService.deleteUserById(userId);

    }

    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long orderId = Long.parseLong(req.getParameter("orderId"));
        Long userId = Long.parseLong(req.getParameter("userId"));
        orderService.deleteOrderById(orderId, userId);

    }

    private void deletePermission(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long permissionId = Long.parseLong(req.getParameter("permissionId"));
        Long userId = Long.parseLong(req.getParameter("userId"));
        permissionService.deletePermission(permissionId, userId);
    }
}

