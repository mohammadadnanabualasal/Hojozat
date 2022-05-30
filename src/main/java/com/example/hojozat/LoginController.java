package com.example.hojozat;

import com.example.hojozat.entities.RestaurantEntity;
import com.example.hojozat.entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView LoginPage(HttpSession session,@RequestParam(value = "formError", defaultValue = "") String formError)
    {
        if(session.getAttribute("user") != null)
        {
            return new ModelAndView("redirect:/home");
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("formError", formError);
        return  modelAndView;
    }

    @RequestMapping(value = "/restaurantLogin", method = RequestMethod.GET)
    public ModelAndView restaurantLoginPage(HttpSession session, @RequestParam(value = "formError", defaultValue = "") String formError)
    {
        if(session.getAttribute("user") != null || session.getAttribute("restaurantUser") != null)
        {
            return new ModelAndView("redirect:/home");
        }
        ModelAndView modelAndView = new ModelAndView("restaurantLogin");
        modelAndView.addObject("formError", formError);
        return  modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session)
    {
        session.removeAttribute("user");
        session.removeAttribute("restaurantUser");
        return  new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public ModelAndView doLoginAction(HttpSession session, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password)
    {
        if(session.getAttribute("user") != null)
        {
            return new ModelAndView("redirect:/home");
        }else {
            if (UserEntity.getUserByEmail(email) != null && UserEntity.getUserByEmail(email).getPassword().equals(password))
            {
                session.setAttribute("user", UserEntity.getUserByEmail(email));
                return new ModelAndView("redirect:/home");
            }else {
                return new ModelAndView("redirect:/login?formError=either the email is not exist or the password is incorrect.");
            }
        }
    }

    @RequestMapping(value = "/doRestaurantLogin", method = RequestMethod.GET)
    public ModelAndView doRestaurantLogin(HttpSession session, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password)
    {
        if(session.getAttribute("user") != null || session.getAttribute("restaurantUser") != null)
        {
            return new ModelAndView("redirect:/home");
        }else {
            if (RestaurantEntity.getRestaurantByEmail(email) != null && RestaurantEntity.getRestaurantByEmail(email).getPassword().equals(password))
            {
                session.setAttribute("restaurantUser", RestaurantEntity.getRestaurantByEmail(email));
                return new ModelAndView("redirect:/myRestaurant");
            }else {
                return new ModelAndView("redirect:/restaurantLogin?formError=either the email is not exist or the password is incorrect.");
            }
        }
    }
}
