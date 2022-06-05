package com.example.hojozat;

import com.example.hojozat.entities.RestaurantEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController{

    @RequestMapping(value = {"/home","/"}, method = RequestMethod.GET)
    public ModelAndView homePage() {
        List<RestaurantEntity> restaurantEntities = RestaurantEntity.getAllRestaurants("");
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("restaurants", restaurantEntities);
        int pageSize = 3;
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("pageNumber",  0);
        modelAndView.addObject("pages", (double) restaurantEntities.size() % pageSize == 0 ? restaurantEntities.size() / pageSize : restaurantEntities.size() / pageSize + 1);
        return  modelAndView;
    }
}
