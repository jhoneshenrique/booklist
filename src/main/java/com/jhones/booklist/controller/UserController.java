package com.jhones.booklist.controller;

import com.jhones.booklist.model.User;
import com.jhones.booklist.service.implementation.UserServiceImplementation;
import com.jhones.booklist.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    UserServiceImplementation userService;

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public String loadSaveUserForm(){

        return "/saveUserForm";
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message","All the fields must be filled");
            return "redirect:/user/create";
        }

        String password = new BCryptPasswordEncoder().encode(user.getPass());
        user.setPass(password);

        userService.save(user);

        return "redirect:/";
    }



}
