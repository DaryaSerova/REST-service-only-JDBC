package ru.aston.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aston.dto.NewOrderDto;
import ru.aston.dto.NewUserDto;
import ru.aston.dto.UserDto;
import ru.aston.repository.OrderRepository;
import ru.aston.service.impl.OrderServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static ru.aston.util.Fixture.*;

public class OrderServiceImplTest {

    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    private OrderService orderService = new OrderServiceImpl(orderRepository);

    @Test
    public void shouldCreateOrder() throws Exception {

        //given
        NewOrderDto newOrderDto = new NewOrderDto();
        newOrderDto.setName("Test_name");

        //when
        doReturn(generateOrder()).when(orderRepository).createOrder(any());
        var result = orderService.createOrder(newOrderDto, 1L);

        //then
        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void shouldThrowExceptionCreateOrder() {

        //given
        //when
        //then
        assertThrows(
                RuntimeException.class,
                () -> orderService.createOrder(null, null));
    }
    @Test
    public void shouldUpdateOrder() throws Exception {

        //given
        var updateOrderDto = generateUpdateOrderDto();
        var order = generateOrder();
        doReturn(order).when(orderRepository).findOrderById(any());
        doReturn(order).when(orderRepository).updateOrder(any());

        //when
        var result = orderService.updateOrder(updateOrderDto, order.getId(), order.getUserId());

        //then
        assertTrue(order.getId().equals(result.getId()));
        assertTrue(order.getName().equals(result.getName()));

    }

    @Test
    public void shouldDeleteOrder() throws Exception {

        //given
        var order = generateOrder();
        doReturn(order).when(orderRepository).findOrderById(any());

        //when
        orderService.deleteOrderById(order.getId(), order.getUserId());
    }


    @Test
    public void shouldFindOrder() throws Exception {

        //given
        var order = generateOrder();
        doReturn(order).when(orderRepository).findOrderById(any());

        //when
        var result = orderService.getOrderById(order.getId());

        //then
        assertTrue(order.getId().equals(result.getId()));
        assertTrue(order.getName().equals(result.getName()));
    }


}
