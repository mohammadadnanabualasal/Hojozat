package com.example.hojozat;


import com.example.hojozat.entities.RestaurantEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {

    @RequestMapping(value = "/search")
    public ModelAndView searchPage(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber
            ,@RequestParam(value = "term", required = false, defaultValue = "") String searchTerm ) {
        List<RestaurantEntity> restaurantEntities = RestaurantEntity.getAllRestaurants(searchTerm);
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("restaurants", restaurantEntities);
        modelAndView.addObject("term", searchTerm);
        int pageSize = 3;
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("pageNumber", pageNumber - 1);
        modelAndView.addObject("pages", (double) restaurantEntities.size() % pageSize == 0 ? restaurantEntities.size() / pageSize : restaurantEntities.size() / pageSize + 1);
        return modelAndView;
    }
}
