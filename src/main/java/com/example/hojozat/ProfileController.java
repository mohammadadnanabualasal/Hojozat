package com.example.hojozat;

import com.example.hojozat.entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView myProfilePage(HttpSession session)
    {
        if (session.getAttribute("user") != null){
            return  new ModelAndView("myProfile");
        }
        return  new ModelAndView("redirect:/login");

    }

    @RequestMapping(value = "/profile/{profileId}", method = RequestMethod.GET)
    public ModelAndView profilePage(@PathVariable("profileId") String id, HttpSession session)
    {
        UserEntity user =  UserEntity.getUserById(id);
        if (user != null){
            session.setAttribute("requestedUser", user);
        }
        return  new ModelAndView("profile");
    }

    @RequestMapping(value = "/removeMyAccount", method = RequestMethod.GET)
    public ModelAndView profilePage(HttpSession session)
    {
        UserEntity user =  (UserEntity) session.getAttribute("user");
        if (user != null){
            session.removeAttribute("user");
            UserEntity.removeUser(user);
        }
        return  new ModelAndView("redirect:/home");
    }
}
