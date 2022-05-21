package com.example.hojozat;

import com.example.hojozat.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RestaurantController {

    @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET)
    public ModelAndView restaurantPage(HttpSession session, @PathVariable("restaurantId") String id, @RequestParam(value = "timeError", defaultValue = "") String timeError) {
        RestaurantEntity restaurant = RestaurantEntity.getRestaurantById(id);
        ModelAndView modelAndView = new ModelAndView("restaurant");
        modelAndView.addObject("dishesList", restaurant.getAllDishesForRestaurant());
        modelAndView.addObject("servingHoursList", restaurant.getServingHours());
        modelAndView.addObject("timeError", timeError);
        session.setAttribute("requestedRestaurant", restaurant);
        return modelAndView;
    }

    @RequestMapping(value = "/myRestaurant", method = RequestMethod.GET)
    public ModelAndView myRestaurantPage(HttpSession session) {
        if (session.getAttribute("restaurantUser") != null) {
            RestaurantEntity restaurant = (RestaurantEntity) session.getAttribute("restaurantUser");
            ModelAndView modelAndView = new ModelAndView("myRestaurant");
            modelAndView.addObject("dishesList", restaurant.getAllDishesForRestaurant());
            modelAndView.addObject("myRestaurant", restaurant);
            return modelAndView;
        }
        return new ModelAndView("redirect:/search");
    }

    @RequestMapping(value = "/updateRestaurantInfo", method = RequestMethod.GET)
    public ModelAndView updateRestaurantInfo(HttpSession session
            , @RequestParam(value = "fromTime") int fromTime
            , @RequestParam(value = "toTime") int toTime
            , @RequestParam(value = "numberOfTables", defaultValue = "0") int numberOfTables
            , @RequestParam(value = "familiesSection", defaultValue = "NO") boolean familiesSection) {
        if (session.getAttribute("restaurantUser") != null) {
            RestaurantEntity restaurant = (RestaurantEntity) session.getAttribute("restaurantUser");
            ModelAndView modelAndView = new ModelAndView("myRestaurant");
            try {
                if (fromTime > toTime) {
                    modelAndView.setViewName("redirect:/myRestaurant");
                    return modelAndView;
                }
                restaurant.setServingFromTime(Time.valueOf(fromTime + ":00:00"));
                restaurant.setServingToTime(Time.valueOf(toTime + ":00:00"));
                restaurant.setTablesNumber(numberOfTables);
                restaurant.setFamiliesSection(familiesSection ? "YES" : "NO");
                restaurant.update();
                session.setAttribute("restaurantUser", restaurant);
                modelAndView.addObject("myRestaurant", restaurant);
            } catch (Exception exception) {
                modelAndView.addObject("error", "Error");
            }
            modelAndView.addObject("dishesList", restaurant.getAllDishes());
            return modelAndView;
        }
        return new ModelAndView("redirect:/search");
    }

    @RequestMapping(value = "/editDish/{dishId}", method = RequestMethod.GET)
    public ModelAndView editDish(@PathVariable("dishId") String dishId) {
        return new ModelAndView("redirect:/showDish/" + dishId);
    }

    @RequestMapping(value = "/showDish/{dishId}", method = RequestMethod.GET)
    public ModelAndView showDish(@PathVariable("dishId") String dishId, HttpSession session) {
        DishesEntity dishesEntity = DishesEntity.getDishById(Integer.parseInt(dishId));
        ModelAndView modelAndView = new ModelAndView("dish");
        if (session.getAttribute("restaurantUser") != null) {
            modelAndView.addObject("myRestaurant", session.getAttribute("restaurantUser"));
        }
        modelAndView.addObject("dish", dishesEntity);
        return modelAndView;
    }

    @RequestMapping(value = "/addDish", method = RequestMethod.GET)
    public ModelAndView addDish(HttpSession session) {

        return new ModelAndView("addDish");
    }

    @RequestMapping(value = "/addDish", method = RequestMethod.POST)
    public ModelAndView addDishPost(HttpSession session, HttpServletRequest httpServletRequest
            , @RequestParam(value = "dishName") String name
            , @RequestParam(value = "description") String description
            , @RequestParam(value = "price") String price) {
        if (session.getAttribute("restaurantUser") != null) {
            RestaurantEntity restaurant = (RestaurantEntity) session.getAttribute("restaurantUser");
            DishesEntity dishesEntity = new DishesEntity();
            dishesEntity.setName(name);
            dishesEntity.setDescription(description);
            dishesEntity.setPrice(Double.parseDouble(price));
            dishesEntity.setRestaurantId(restaurant.getId());
            int dishId = restaurant.addNewDish(dishesEntity, false);
            if (dishId != -1) {
                try {
                    ImagesController.saveDishImage(httpServletRequest.getParts(), restaurant, dishId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ModelAndView("redirect:/showDish/" + dishId);
            } else {
                return new ModelAndView("redirect:/addDish");
            }

        } else {
            return new ModelAndView("redirect:/restaurantLogin");
        }
    }


    @RequestMapping(value = "/changeNumberOfTables", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, String>> changeNumberOfTables(HttpSession session, @RequestParam("newValue") String newValue, @RequestParam("restaurantId") String restaurantId) {
        List<Map<String, String>> retVal = new ArrayList<>();
        Map<String, String> res = new HashMap<>();
        if (newValue.equals("5")) {
            res.put("message", "<h5 class=\"success\">success</h5>");
            res.put("reservedTables", "<span>1</span>");
            res.put("availableTables", "<span>2</span>");
            retVal.add(res);
        } else {
            res.put("message", "<h5 class=\"failed\">Failed</h5>");
            res.put("reservedTables", "<span>3</span>");
            res.put("availableTables", "<span>4</span>");
            retVal.add(res);
        }
        return retVal;
    }

    @RequestMapping("/reserve")
    public ModelAndView doReserve(HttpSession session
            , @RequestParam(value = "fromTime") String fromTime
            , @RequestParam(value = "numberOfPersons") String numberOfPersons
            , @RequestParam(value = "restaurantId") String restaurantId
            , @RequestParam(value = "orderFoodOnline", defaultValue = "false") boolean orderFoodOnline) {
        ModelAndView modelAndView = new ModelAndView("redirect:/restaurant/" + restaurantId);
        if (session.getAttribute("user") != null) {
            if (ReservationEntity.getReservationByUserDateRestaurant(((UserEntity) session.getAttribute("user")).getUserId() + ""
                    , restaurantId
                    , new Date(System.currentTimeMillis())) == null) {
                ReservationEntity reservationEntity = new ReservationEntity();
                reservationEntity.setRestaurantId(Integer.parseInt(restaurantId));
                reservationEntity.setTime(fromTime);
                reservationEntity.setDate(new Date(System.currentTimeMillis()));
                reservationEntity.setNumberOfPersons(Integer.parseInt(numberOfPersons));
                reservationEntity.setOrderOnline(orderFoodOnline ? "YES" : "NO");
                reservationEntity.setUserId(((UserEntity) session.getAttribute("user")).getUserId());
                if (RestaurantEntity.getRestaurantById(restaurantId).availableForNewReservation(fromTime)) {
                    ReservationEntity.addReservation(reservationEntity);
                } else {
                    modelAndView.setViewName("redirect:/restaurant/" + restaurantId + "?timeError=No available tables at this time.");
                }
                return modelAndView;
            }else {
                modelAndView.setViewName("redirect:/restaurant/" + restaurantId + "?timeError=you already have a reservation today in this Restaurant");
                return modelAndView;
            }
        } else {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/removeDish/{dishId}", method = RequestMethod.GET)
    public ModelAndView removeDish(HttpSession session, @PathVariable("dishId") String dishId) {
        if (session.getAttribute("restaurantUser") != null
                && ((RestaurantEntity) session.getAttribute("restaurantUser")).getId() == DishesEntity.getDishById(Integer.parseInt(dishId)).getRestaurantId()) {
            DishesEntity.removeDish(DishesEntity.getDishById(Integer.parseInt(dishId)));
            return new ModelAndView("redirect:/myRestaurant");
        }
        return new ModelAndView("redirect:/showDish/" + dishId);
    }

    @RequestMapping(value = "/reservations")
    public ModelAndView showOrders(HttpSession session) {
        if (session.getAttribute("restaurantUser") != null) {
            RestaurantEntity restaurant = (RestaurantEntity) session.getAttribute("restaurantUser");
            ModelAndView modelAndView = new ModelAndView("reservations");
            modelAndView.addObject("reservations", restaurant.getReservations());
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/restaurantLogin");
        }
    }

    @RequestMapping(value = "/cancelReservation/{reservationId}")
    public ModelAndView cancelReservation(HttpSession session, @PathVariable(value = "reservationId") String reservationId) {
        if (session.getAttribute("user") != null && ReservationEntity.getById(reservationId).getUserId() == ((UserEntity) session.getAttribute("user")).getUserId()) {
            ReservationEntity reservationEntity = ReservationEntity.getById(reservationId);
            for (OrderEntity orderEntity : reservationEntity.getAllOrders()
            ) {
                OrderEntity.removeOrder(orderEntity);
            }
            ReservationEntity.removeReservation(reservationEntity);
            return new ModelAndView("redirect:/cart");
        } else {
            return new ModelAndView("redirect:/restaurantLogin");
        }
    }
}
