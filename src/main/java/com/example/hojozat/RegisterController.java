package com.example.hojozat;

import com.example.hojozat.entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView LoginPage(HttpSession session)
    {
        if(session.getAttribute("user") != null)
        {
            return new ModelAndView("redirect:/home");
        }
        return  new ModelAndView("register");
    }

    @RequestMapping(value = "/doRegister",method = RequestMethod.POST)
    public ModelAndView createUser(HttpSession session, HttpServletRequest httpServletRequest
            , @RequestParam(name="firstName")String firstName
            , @RequestParam(name="lastName") String lastName
            ,@RequestParam(name = "email") String email
            , @RequestParam(name="password") String password
            ,@RequestParam(name = "confirmPassword") String confirmPassword
            , @RequestParam(name="phone") String phone){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int users =0;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM user WHERE email = '" + email + "';");
            users = query.getResultList().size();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setPassword(password);
        userEntity.setEmail(email);
        userEntity.setPhone(phone);
        userEntity.setIsAdmin("NO");

        if (session.getAttribute("user") !=null){
            return new ModelAndView("redirect:/home");
        }
        else {
            if (!confirmPassword.equals(password)){
                return new ModelAndView("redirect:/register");
            }
            boolean isSaved = false;

            try {
                if (users <= 0) {
                    isSaved = userEntity.addNewUser(userEntity, false);
                }
            }catch (Exception e){
                ModelAndView modelAndView = new ModelAndView("create");
                modelAndView.addObject("user", userEntity);
                return modelAndView;
            }
            if(isSaved)
            {
                try {
                    httpServletRequest.getPart("imageFile");
                    saveProfileImage(httpServletRequest.getParts(), userEntity);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                session.setAttribute("user",userEntity);
                return new ModelAndView("redirect:/home");
            }else {
                ModelAndView modelAndView = new ModelAndView("register");
                modelAndView.addObject("user", userEntity);
                return modelAndView;
            }

        }
    }

    public void saveProfileImage(Collection<Part> parts, UserEntity user)
    {
        try {
            for (Part p: parts) {
                if (p.getContentType() != null && p.getContentType().startsWith("image")){
                    InputStream imageInputStream = p.getInputStream();
                    File folder = new File(System.getProperty("user.home") + "/Hojozat/profiles" );
                    File file = new File(folder.getAbsolutePath()+"/"+user.getUserId()+"."+p.getContentType().substring(p.getContentType().indexOf('/')+1));
                    file.createNewFile();
                    try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
                        int read;
                        byte[] bytes = new byte[8192];
                        while ((read = imageInputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
