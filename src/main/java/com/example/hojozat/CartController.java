package com.example.hojozat;

import com.example.hojozat.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    @RequestMapping(value = "/cart")
    public ModelAndView showCart(HttpSession session)
    {
        if (session.getAttribute("user") != null){
            ModelAndView modelAndView = new ModelAndView("cart");
            modelAndView.addObject("user", session.getAttribute("user"));
            List<ReservationEntity> reservations = ReservationEntity.getAllReservationsForUserById(((UserEntity)session.getAttribute("user")).getUserId());
            modelAndView.addObject("reservations", reservations);
            return modelAndView;
        }else {
            return new ModelAndView("redirect:login");
        }
    }

    @RequestMapping(value = "/addDishToCart/{dishId}")
    public ModelAndView addToCart(HttpSession session, @PathVariable("dishId") String dishId, @RequestParam("numberOfDishes") int numberOfDishes)
    {

        if (session.getAttribute("user") != null)
        {
            DishesEntity dishesEntity = DishesEntity.getDishById(Integer.parseInt(dishId));
            RestaurantEntity restaurant = RestaurantEntity.getRestaurantById(dishesEntity.getRestaurantId()+"");
            UserEntity userEntity = (UserEntity) session.getAttribute("user");
            if (ReservationEntity.getReservationsByUserAndRestaurant(userEntity.getUserId(), restaurant.getId()) != null)
            {
                ReservationEntity reservationEntity = ReservationEntity.getReservationsByUserAndRestaurant(userEntity.getUserId(), restaurant.getId());
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setDishId(Integer.parseInt(dishId));
                orderEntity.setReservationId(reservationEntity.getId());
                orderEntity.setQuantity(numberOfDishes);
                if (OrderEntity.getByDishAndReservationID(dishesEntity.getId(), reservationEntity.getId()) != null){
                    OrderEntity newOrder = OrderEntity.getByDishAndReservationID(dishesEntity.getId(), reservationEntity.getId());
                    if (numberOfDishes == 0){
                        OrderEntity.removeOrder(newOrder);
                        return new ModelAndView("redirect:/restaurant/"+restaurant.getId());
                    }
                    newOrder.setQuantity(numberOfDishes);
                    OrderEntity.addOrder(newOrder, true);
                }else if (numberOfDishes != 0){
                    OrderEntity.addOrder(orderEntity, false);
                }

            }else {

            }
            return new ModelAndView("redirect:/restaurant/"+restaurant.getId());
        }else {
            return new ModelAndView("redirect:/login");
        }
    }
}
