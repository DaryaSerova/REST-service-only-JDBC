package ru.aston.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.dto.*;
import ru.aston.service.OrderService;
import ru.aston.service.PermissionService;
import ru.aston.service.UserService;
import ru.aston.service.impl.OrderServiceImpl;
import ru.aston.service.impl.PermissionServiceImpl;
import ru.aston.service.impl.UserServiceImpl;
import ru.aston.util.DbInitializeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", value = "/")
public class UserServlet extends HttpServlet {

    public UserService userService;
    public OrderService orderService;
    public PermissionService permissionService;
    public ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DbInitializeUtil.initDB();
        this.userService = new UserServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.permissionService = new PermissionServiceImpl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        Object responseBody = null;

        if (action.endsWith("orders")) {
            responseBody = createOrder(req, resp);
        } else if (action.endsWith("permission")) {
            responseBody = addPermission(req, resp);
        } else if (action.endsWith("users")) {
            responseBody = createUser(req, resp);
        }
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        Object responseBody = null;

        if (action.endsWith("orders")) {
            responseBody = getOrder(req, resp);
        } else if (action.endsWith("permission")) {
            responseBody = getPermissionOfUser(req, resp);
        } else if (action.endsWith("users")) {
            responseBody = getUserById(req, resp);
        }
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();

        Object responseBody = null;

        if (action.endsWith("orders")) {
            responseBody = updateOrder(req, resp);
        } else if (action.endsWith("users")) {
            responseBody = updateUser(req, resp);
        }
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getServletPath();
        try {
            if (action.endsWith("orders")) {
                deleteOrder(req, resp);
            } else if (action.endsWith("permission")) {
                deletePermission(req, resp);
            } else if (action.endsWith("users")) {
                deleteUser(req, resp);
            }
        } catch (Exception e){
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

    private UserDto createUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName(req.getParameter("name"));
        return userService.createUser(newUserDto);

    }

    private OrderDto createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NewOrderDto newOrderDto = new NewOrderDto();
        newOrderDto.setName(req.getParameter("name"));
        Long userId = Long.parseLong(req.getParameter("userId"));
        return orderService.createOrder(newOrderDto, userId);
    }

    private UserDtoWithOrders getUserById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");

        if (userId.isEmpty()) {
            throw new RuntimeException("An empty value cannot be passed.");
        }

        Long id = Long.parseLong(userId);
        return userService.getUserById(id);
    }

    private OrderDto getOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderId = req.getParameter("orderId");
        String userId = req.getParameter("userId");

        if (orderId.isEmpty()) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long id = Long.parseLong(orderId);
        return orderService.getOrderById(id);
    }

    private UserPermissionDto getPermissionOfUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");
        if (userId.isEmpty()) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long usId = Long.parseLong(userId);
        return permissionService.getPermissionOfUser(usId);
    }

    private UserDto updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

    private OrderDto updateOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ordId = req.getParameter("orderId");
        String usId = req.getParameter("userId");
        String nameOrder = req.getParameter("name");

        UpdateOrderDto updateOrderDto = new UpdateOrderDto();

        if (ordId == null || usId == null || nameOrder == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }

        Long orderId = Long.parseLong(ordId);
        Long userId = Long.parseLong(usId);
        updateOrderDto.setName(nameOrder);

        return orderService.updateOrder(updateOrderDto, orderId, userId);
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

    private void flushObjectToResponse(HttpServletResponse resp, Object obj) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(obj));
        out.flush();
    }
}

