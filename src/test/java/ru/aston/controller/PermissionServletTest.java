package ru.aston.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.aston.CommonTestcontainer;
import ru.aston.dto.PermissionDto;
import ru.aston.dto.UserPermissionDto;
import ru.aston.model.User;
import ru.aston.repository.PermissionRepository;
import ru.aston.repository.UserRepository;
import ru.aston.repository.impl.PermissionRepositoryImpl;
import ru.aston.repository.impl.UserRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PermissionServletTest extends CommonTestcontainer {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserRepository userRepository = new UserRepositoryImpl();
    private PermissionRepository permissionRepository = new PermissionRepositoryImpl();

    @Test
    public void shouldAddPermission() throws Exception {
        //given

        User user = createUser();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("permissionId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn(user.getId().toString());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        //when
        new PermissionServlet().doPost(request, response);

        //then
        verify(request, atLeast(1)).getParameter("permissionId");
        writer.flush();
        PermissionDto result = objectMapper.readValue(stringWriter.toString(), PermissionDto.class);

        assertNotNull(result.getPermissionId());
        assertNotNull(result.getUserId());
        assertEquals(result.getPermissionId(), Long.valueOf(1L));
        assertEquals(result.getUserId(), user.getId());
    }

    @Test
    public void shouldGetPermission() throws Exception {
        //given
        User user = createUser();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        PermissionDto permissionDto = permissionRepository.addPermission(1L, user.getId());
        when(request.getParameter("userId")).thenReturn(user.getId().toString());

        //when
        new PermissionServlet().doGet(request, response);

        //then
        verify(request, atLeast(1)).getParameter("userId");
        writer.flush();
        UserPermissionDto result = objectMapper.readValue(stringWriter.toString(), UserPermissionDto.class);

        assertNotNull(result.getUserId());
        assertEquals(result.getPermission().size(), 1);
        assertEquals(result.getPermission().get(0).getId(), permissionDto.getPermissionId());
    }


    @Test
    public void shouldDeletePermission() throws Exception {
        //given
        User user = createUser();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        permissionRepository.addPermission(1L, user.getId());
        when(request.getParameter("permissionId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn(user.getId().toString());

        //when
        new PermissionServlet().doDelete(request, response);

        //then
        verify(request, atLeast(1)).getParameter("userId");
        writer.flush();
        assertThrows(
                RuntimeException.class,
                () -> permissionRepository.findPermissionByUserId(user.getId()));
    }


    private User createUser() {
        User user = new User();
        user.setName("test_user");
        return userRepository.createUser(user);
    }


}
