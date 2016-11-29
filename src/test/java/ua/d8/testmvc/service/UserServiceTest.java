package ua.d8.testmvc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;
import ua.d8.testmvc.conf.Conf;
import ua.d8.testmvc.domain.User;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Conf.class})
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
     public void getAll_ShouldReturnAllUsers(){
        assertTrue(!userService.getAll().isEmpty());
    }

    @Test
    public void getById_ShouldReturnUser() {
        assertNotNull(userService.getById(2L));
    }

    @Test(expected=UserNotFoundException.class)
    public void deleteById_ShouldDeleteUser(){
        userService.deleteById(1L);
        userService.getById(1L);
    }

    @Test
    public void updateUser_ShouldUpdateUser(){
        Long id = 3L;
        User user = userService.getById(id);
        String oldName=  user.getName();
        String newName=  oldName+"new";
        user.setName(newName);

        userService.update(user);

        assertNotEquals(userService.getById(id).getName(), oldName);
        assertEquals(userService.getById(id).getName(), newName);
    }
}
