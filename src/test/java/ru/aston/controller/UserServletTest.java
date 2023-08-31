package ru.aston.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.aston.CommonTestcontainer;
import ru.aston.dto.NewUserDto;
import ru.aston.dto.UserDto;
import ru.aston.dto.UserDtoWithOrders;
import ru.aston.model.User;
import ru.aston.repository.UserRepository;
import ru.aston.repository.impl.UserRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class UserServletTest extends CommonTestcontainer {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserRepository userRepository = new UserRepositoryImpl();

    @Test
    public void shouldCreateUser() throws Exception {
        //given
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName("test_name");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn(newUserDto.getName());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        //when
        new UserServlet().doPost(request, response);

        //then
        verify(request, atLeast(1)).getParameter("name");
        writer.flush();
        UserDto result = objectMapper.readValue(stringWriter.toString(), UserDto.class);
        assertNotNull(result.getId());
        assertEquals(result.getName(), newUserDto.getName());
    }

    @Test
    public void shouldGetUser() throws Exception {
        //given
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName("test_user");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);

        when(request.getParameter("userId")).thenReturn(user.getId().toString());

        //when
        new UserServlet().doGet(request, response);

        //then
        verify(request, atLeast(1)).getParameter("userId");
        writer.flush();
        UserDtoWithOrders result = objectMapper.readValue(stringWriter.toString(), UserDtoWithOrders.class);
        assertNotNull(result.getId());
        assertEquals(result.getName(), newUserDto.getName());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //given
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName("test_user");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);

        when(request.getParameter("userId")).thenReturn(user.getId().toString());
        when(request.getParameter("name")).thenReturn("new_name");
        //when
        new UserServlet().doPut(request, response);

        //then
        verify(request, atLeast(1)).getParameter("userId");
        writer.flush();
        UserDto result = objectMapper.readValue(stringWriter.toString(), UserDto.class);
        assertNotNull(result.getId());
        assertEquals(result.getName(), "new_name");
    }


    @Test
    public void shouldDeleteUser() throws Exception {
        //given
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setName("test_user");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        User user = new User();
        user.setName("test_user");
        user = userRepository.createUser(user);

        when(request.getParameter("userId")).thenReturn(user.getId().toString());
        var userId = user.getId();

        //when
        new UserServlet().doDelete(request, response);

        //then
        verify(request, atLeast(1)).getParameter("userId");
        writer.flush();
        assertThrows(
                RuntimeException.class,
                () -> userRepository.findUserById(userId));
    }

}
