package com.jhones.booklist.controller;

import com.jhones.booklist.model.User;
import com.jhones.booklist.service.implementation.UserServiceImplementation;
import com.jhones.booklist.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    UserServiceImplementation userService;

    @RequestMapping(value = "/user/form", method = RequestMethod.GET)
    public String loadSaveUserForm(){
        return "/saveUserForm";
    }

    @PostMapping("/user/create")
    public RedirectView saveUser(User user,
                                 @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println(fileName);
        System.out.println(user.getLogin());
        System.out.println(user.getPass());
        user.setPhoto(fileName);
        System.out.println(user.getPhoto());

        user.setPass(new BCryptPasswordEncoder().encode(user.getPass()));
        System.out.println(user.getPass());

        User savedUser = userService.save(user);

        String uploadDir = "/home/jhones/Desktop/booklist_project/booklist/user-photos/" + savedUser.getLogin();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/", true);
    }


}
