package ru.aston.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.dto.UpdateOrderDto;
import ru.aston.service.OrderService;
import ru.aston.service.impl.OrderServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    public OrderService orderService = new OrderServiceImpl();
    public ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var responseBody = createOrder(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var responseBody = getOrder(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var responseBody = updateOrder(req, resp);
        flushObjectToResponse(resp, responseBody);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        String action = req.getServletPath();
        try {
            deleteOrder(req, resp);

        } catch (Exception e) {
            resp.setStatus(500);
        }
    }

    private OrderDto createOrder(HttpServletRequest req, HttpServletResponse resp) {

        NewOrderDto newOrderDto = new NewOrderDto();

        String name = req.getParameter("name");
        Long userId = Long.parseLong(req.getParameter("userId"));

        if (name.isEmpty() || userId == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }

        newOrderDto.setName(name);

        return orderService.createOrder(newOrderDto, userId);
    }


    private OrderDto getOrder(HttpServletRequest req, HttpServletResponse resp) {

        String orderId = req.getParameter("orderId");

        if (orderId == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long id = Long.parseLong(orderId);
        return orderService.getOrderById(id);
    }

    private OrderDto updateOrder(HttpServletRequest req, HttpServletResponse resp) {

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

    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) {

        String ordId = req.getParameter("orderId");
        String usId = req.getParameter("userId");

        if (ordId == null || usId == null) {
            throw new RuntimeException("An empty value cannot be passed.");
        }
        Long orderId = Long.parseLong(ordId);
        Long userId = Long.parseLong(usId);

        orderService.deleteOrderById(orderId, userId);
    }


    private void flushObjectToResponse(HttpServletResponse resp, Object obj) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(obj));
        out.flush();
    }
}

