package ua.d8.testmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.d8.testmvc.domain.User;
import ua.d8.testmvc.service.UserNotFoundException;
import ua.d8.testmvc.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    public static final String USERS = "users";
    public static final String EDITUSER = "view";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users",users);
        logger.debug("getall users. result="+users);

        return USERS;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@RequestParam("userid") Long userId, Model model) {
        User user = userService.getById(userId);

        model.addAttribute("user",user);
        logger.debug("view user. user=" + user);

        return EDITUSER;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid User user, BindingResult result) {

        if(result.hasErrors()){
            return EDITUSER;
        }
        else{
            userService.update(user);
            logger.debug("edit user. user=" + user);

            return "redirect:/ds/users";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("userid") Long userId) {
        userService.deleteById(userId);
        logger.debug("delete user. userId=" + userId);

        return "redirect:/ds/users";
    }


    @ExceptionHandler(UserNotFoundException.class)
    public @ResponseBody String handleException(Exception e) throws Exception {
        return e.getMessage();
    }
}
