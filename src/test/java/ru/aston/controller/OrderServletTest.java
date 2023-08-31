package ru.aston.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.aston.CommonTestcontainer;
import ru.aston.dto.NewOrderDto;
import ru.aston.dto.OrderDto;
import ru.aston.model.Order;
import ru.aston.model.User;
import ru.aston.repository.OrderRepository;
import ru.aston.repository.UserRepository;
import ru.aston.repository.impl.OrderRepositoryImpl;
import ru.aston.repository.impl.UserRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class OrderServletTest extends CommonTestcontainer {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserRepository userRepository = new UserRepositoryImpl();
    private OrderRepository orderRepository = new OrderRepositoryImpl();

    @Test
    public void shouldCreateOrder() throws Exception {
        //given
        NewOrderDto newOrderDto = new NewOrderDto();
        newOrderDto.setName("test_name");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        User user = createUser();

        when(request.getParameter("name")).thenReturn(newOrderDto.getName());
        when(request.getParameter("userId")).thenReturn(user.getId().toString());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        //when
        new OrderServlet().doPost(request, response);

        //then
        verify(request, atLeast(1)).getParameter("name");
        writer.flush();
        OrderDto result = objectMapper.readValue(stringWriter.toString(), OrderDto.class);
        assertNotNull(result.getId());
        assertEquals(result.getName(), newOrderDto.getName());
    }

    @Test
    public void shouldGetOrder() throws Exception {
        //given
        NewOrderDto newOrderDto = new NewOrderDto();
        newOrderDto.setName("test_name");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        User user = createUser();
        Order order = createOrder(user.getId());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("orderId")).thenReturn(order.getId().toString());

        //when
        new OrderServlet().doGet(request, response);

        //then
        verify(request, atLeast(1)).getParameter("orderId");
        writer.flush();
        OrderDto result = objectMapper.readValue(stringWriter.toString(), OrderDto.class);
        assertNotNull(result.getId());
        assertEquals(result.getName(), newOrderDto.getName());
        assertEquals(result.getUserId(), order.getUserId());
    }

    @Test
    public void shouldUpdateOrder() throws Exception {
        //given
        User user = createUser();
        Order order = createOrder(user.getId());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("orderId")).thenReturn(order.getId().toString());
        when(request.getParameter("userId")).thenReturn(user.getId().toString());
        when(request.getParameter("name")).thenReturn("new_name");

        //when
        new OrderServlet().doPut(request, response);

        //then
        verify(request, atLeast(1)).getParameter("userId");
        writer.flush();
        OrderDto result = objectMapper.readValue(stringWriter.toString(), OrderDto.class);
        assertNotNull(result.getId());
        assertEquals(result.getName(), "new_name");
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        //given
        User user = createUser();
        Order order = createOrder(user.getId());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("orderId")).thenReturn(order.getId().toString());
        when(request.getParameter("userId")).thenReturn(user.getId().toString());

        Long orderId = order.getId();

        //when
        new OrderServlet().doDelete(request, response);

        //then
        verify(request, atLeast(1)).getParameter("orderId");
        writer.flush();
        assertThrows(
                RuntimeException.class,
                () -> orderRepository.findOrderById(orderId));
    }

    private User createUser() {
        User user = new User();
        user.setName("test_user");
        return userRepository.createUser(user);
    }

    private Order createOrder(Long userId) {
        Order order = new Order();
        order.setName("test_name");
        order.setUserId(userId);
        return orderRepository.createOrder(order);
    }
}
