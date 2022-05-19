package com.example.hojozat;

import com.example.hojozat.entities.RestaurantEntity;
import com.example.hojozat.entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @RequestMapping(value = "/admin")
    public ModelAndView adminPage(HttpSession session) {
        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            return new ModelAndView("adminPage");
        } else {
            return new ModelAndView("redirect:/home");
        }

    }

    @RequestMapping(value = "/users")
    public ModelAndView usersPage(HttpSession session) {
        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            List<UserEntity> users = UserEntity.getAllUsers("");
            session.setAttribute("users", users);
            return new ModelAndView("users");
        } else {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/removeUser/{userId}")
    public ModelAndView removeUser(HttpSession session, @PathVariable("userId") String userId) {

        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            UserEntity.removeUser(UserEntity.getUserById(userId));
            return new ModelAndView("redirect:/users");
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/removeRestaurant/{restaurantId}")
    public ModelAndView removeRestaurant(HttpSession session, @PathVariable("restaurantId") String restaurantId) {

        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            RestaurantEntity.removeRestaurant(RestaurantEntity.getRestaurantById(restaurantId));
            return new ModelAndView("redirect:/restaurants");
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/removeAdministrationRole")
    public ModelAndView removeAdministrationRole(HttpSession session, @RequestParam(name = "email") String email) {
        updateAdmin(session, email, "R");
        return new ModelAndView("redirect:addAdmin");
    }

    @RequestMapping(value = "/restaurants")
    public ModelAndView restaurantsPage(HttpSession session, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber) {
        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            List<RestaurantEntity> restaurantEntities = RestaurantEntity.getAllRestaurants("");
            ModelAndView modelAndView = new ModelAndView("restaurants");
            modelAndView.addObject("restaurants", restaurantEntities);
            int pageSize = 3;
            modelAndView.addObject("pageSize", pageSize);
            modelAndView.addObject("pageNumber",  pageNumber - 1);
            modelAndView.addObject("pages", (double) restaurantEntities.size() % pageSize == 0 ? restaurantEntities.size() / pageSize : restaurantEntities.size() / pageSize + 1);
            return modelAndView;
        }else {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/addRestaurant", method = RequestMethod.GET)
    public ModelAndView addRestaurant(HttpSession session) {

        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
        return new ModelAndView("addRestaurant");
        }else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping(value = "/addRestaurant", method = RequestMethod.POST)
    public ModelAndView addRestaurantPost(HttpSession session, HttpServletRequest request
            , @RequestParam("restaurantName") String restaurantName
            , @RequestParam("restaurantEmail") String restaurantEmail
            , @RequestParam("restaurantPhoneNumber") String restaurantPhoneNumber
            , @RequestParam("restaurantLocation") String restaurantLocation
            , @RequestParam(value = "familiesSection", defaultValue = "false") boolean familiesSection
            , @RequestParam("numberOfTables") int numberOfTables
            , @RequestParam("about") String about
            , @RequestParam("fromTime") String fromTime
            , @RequestParam("toTime") String toTime
            , @RequestParam("password") String password)
    {

        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            ModelAndView modelAndView = new ModelAndView("redirect:/addRestaurant");
            int restaurants = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            try {
                Time from = new Time(sdf.parse(fromTime).getTime());
                Time to = new Time(sdf.parse(toTime).getTime());
                if (from.after(to)){
                    modelAndView.addObject("error", "the From time should be before the To time.");
                    return modelAndView;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                Query query = entityManager.createNativeQuery("SELECT * FROM restaurant WHERE email = '" + restaurantEmail + "';");
                restaurants = query.getResultList().size();
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                entityManager.close();
                entityManagerFactory.close();
            }
            RestaurantEntity restaurant = new RestaurantEntity();
            restaurant.setName(restaurantName);
            restaurant.setEmail(restaurantEmail);
            restaurant.setPhone(restaurantPhoneNumber);
            restaurant.setLocation(restaurantLocation);
            restaurant.setFamiliesSection(familiesSection?"YES":"NO");
            restaurant.setTablesNumber(numberOfTables);
            restaurant.setPassword(password);
            restaurant.setAbout(about);
            restaurant.setReservedTables(0);
            long ms;
            try {
                ms = sdf.parse(fromTime).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            restaurant.setServingFromTime(new Time(ms));
            try {
                ms = sdf.parse(toTime).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            restaurant.setServingToTime(new Time(ms));
            try {
                if (restaurants == 0) {
                    int newId = RestaurantEntity.addNewRestaurant(restaurant, false);
                    if (newId != -1) {
                        ImagesController.saveRestaurantImage(request.getParts(), restaurant);
                        modelAndView = new ModelAndView("redirect:/restaurant/" + newId);
                        modelAndView.addObject("restaurant", restaurant);
                        return modelAndView;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ModelAndView("redirect:/addRestaurant");
        } else {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
    public ModelAndView addAdmin(HttpSession session) {

        return new ModelAndView("addAdmin");
    }

    @RequestMapping(value = "/showAdmins", method = RequestMethod.GET)
    public ModelAndView showAdmins(HttpSession session) {

        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            List<UserEntity> admins = UserEntity.getAllAdmins();
            session.setAttribute("users", admins);
            return new ModelAndView("showAdmins");
        } else {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public ModelAndView addAdminPost(HttpSession session, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
                                     @RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                                     @RequestParam(name = "confirmPassword") String confirmPassword, @RequestParam(name = "phone") String phone,
                                     @RequestParam(name = "makeItAdmin", defaultValue = "false") boolean isAdmin) {
        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            if (!confirmPassword.equals(password)){
                return new ModelAndView("redirect:/addAdmin");
            }
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            int users = 0;
            try {
                Query query = entityManager.createNativeQuery("SELECT * FROM user WHERE email = '" + email + "';");
                users = query.getResultList().size();
            }catch (Exception exception){
                exception.printStackTrace();
            }finally {
                entityManager.close();
                entityManagerFactory.close();
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setPassword(password);
            userEntity.setEmail(email);
            userEntity.setPhone(phone);
            boolean success = false;
            if (isAdmin) {
                userEntity.setIsAdmin("YES");
            } else {
                userEntity.setIsAdmin("NO");
            }
            try {
                if (users == 0) {
                    success = userEntity.addNewUser(userEntity, false);
                    if (success) {
                        return new ModelAndView("redirect:/profile/" + userEntity.getUserId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ModelAndView("addAdmin");
        } else {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/addAdminRole", method = RequestMethod.GET)
    public ModelAndView addAdminPost(HttpSession session, @RequestParam(name = "email") String email) {
        updateAdmin(session, email, "A");
        return new ModelAndView("redirect:addAdmin");
    }

    @RequestMapping(value = "/userSearch", method = RequestMethod.GET)
    public @ResponseBody List<String> userSearch(HttpSession session, @RequestParam("term") String term) {
        List<String> retVal = new ArrayList<>();
        List<UserEntity> users = UserEntity.getAllUsers(term);
        String usersList = "";
        for (UserEntity userEntity : users
        ) {
            String isADminDisable = userEntity.getIsAdmin().equals("YES") ? "admin" : "normal user";
            String isADminLable = userEntity.getIsAdmin().equals("YES") ? "Remove Admin Role" : "Make him Admin";
            String action = userEntity.getIsAdmin().equals("YES") ? "/removeAdministrationRole?" : "/addAdminRole?";
            String user = null;
            try {
                user = "<li class=\"user-item\"><div>\n" +
                        "    <div class=\"left-alignment\">" + userEntity.getFirstName() + " " + userEntity.getLastName() + "(" + userEntity.getEmail() + ")</div>\n" +
                        "    <div class=\"right-alignment\"><a href=\"" + action + "email=" + URLEncoder.encode(userEntity.getEmail(), StandardCharsets.UTF_8.toString()) + "\"class=\"" + isADminDisable + "\">" + isADminLable + "</a></div>\n" +
                        "</li></div>";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            usersList = usersList + user;
        }

        retVal.add("<ul class=\"users-list\">" + usersList + "</ul>");
        return retVal;
    }

    private void updateAdmin(HttpSession session, String email, String removeOrAddRole) {
        if (session.getAttribute("user") != null && ((UserEntity) session.getAttribute("user")).getIsAdmin().equals("YES")) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try {
                Query query = entityManager.createNativeQuery("SELECT * FROM user WHERE email = '" + email + "';", UserEntity.class);
                UserEntity userEntity = (UserEntity) query.getResultList().get(0);
                userEntity.addNewUser(userEntity, true);
                if (removeOrAddRole.equals("R")) {
                    userEntity.setIsAdmin("NO");
                } else if (removeOrAddRole.equals("A")) {
                    userEntity.setIsAdmin("YES");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }

}
