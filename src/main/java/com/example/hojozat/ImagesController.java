package com.example.hojozat;

import com.example.hojozat.entities.RestaurantEntity;
import com.example.hojozat.entities.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;

@Controller
public class ImagesController {

    @ResponseBody
    @RequestMapping(value = "/profileImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(HttpServletResponse response, @PathVariable("id") String id)
    {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        File folder = new File(System.getProperty("user.home") + "/Hojozat/profiles" );
        String path = System.getProperty("user.home")+"/Hojozat/profiles/defaultProfile.jpg";
        String format ="jpg";
        File[] listOfFiles = folder.listFiles();
        for (File file:listOfFiles
        ) {
            if(id.equals(file.getName().substring(0, file.getName().lastIndexOf('.') )))
            {
                path = System.getProperty("user.home") + "/Hojozat/profiles/"+file.getName();
                format = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                break;
            }
        }
        File file = new File(path);
        byte[] bytes = null;
        try {
            bytes = getImageBytes(path,format);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @ResponseBody
    @RequestMapping(value = "/restaurantImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getRestaurantImage(HttpServletResponse response, @PathVariable("id") String id)
    {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        File folder = new File(System.getProperty("user.home") + "/Hojozat/restaurants" );
        String path = System.getProperty("user.home")+"/Hojozat/restaurants/defaultProfile.jpg";
        String format ="jpg";
        File[] listOfFiles = folder.listFiles();
        for (File file:listOfFiles
        ) {
            if(file.getName().contains(".") && id.equals(file.getName().substring(0, file.getName().lastIndexOf('.') )))
            {
                path = System.getProperty("user.home") + "/Hojozat/restaurants/"+file.getName();
                format = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                break;
            }
        }
        File file = new File(path);
        byte[] bytes = null;
        try {
            bytes = getImageBytes(path,format);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @ResponseBody
    @RequestMapping(value = "/dishImage/{restaurantId}/{dishId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getDishImage(HttpServletResponse response, @PathVariable("restaurantId") String restaurantId, @PathVariable("dishId") String dishId)
    {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        File folder = new File(System.getProperty("user.home") + "/Hojozat/restaurants/"+restaurantId+"/dishes/" );
        String path = System.getProperty("user.home")+"/Hojozat/restaurants/dish.jpg";
        String format ="jpg";
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null)
        for (File file:listOfFiles) {
            if(dishId.equals(file.getName().substring(0, file.getName().lastIndexOf('.') )))
            {
                path = System.getProperty("user.home") + "/Hojozat/restaurants/"+restaurantId+"/dishes/"+file.getName();
                format = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                break;
            }
        }
        File file = new File(path);
        byte[] bytes = null;
        try {
            bytes = getImageBytes(path,format);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }



    public byte[] getImageBytes( String path, String format) throws IOException{
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, format, bos);
        byte[] data = bos.toByteArray();
        return data;
    }

    public static void saveRestaurantImage(Collection<Part> parts, RestaurantEntity restaurant)
    {
        try {
            for (Part p: parts) {
                if (p.getContentType() != null && p.getContentType().startsWith("image")){
                    InputStream imageInputStream = p.getInputStream();
                    File folder = new File(System.getProperty("user.home") + "/Hojozat/restaurants" );
                    File file = new File(folder.getAbsolutePath()+"/"+restaurant.getId()+"."+p.getContentType().substring(p.getContentType().indexOf('/')+1));
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

    public static void saveDishImage(Collection<Part> parts, RestaurantEntity restaurant, int dishId)
    {
        try {
            for (Part p: parts) {
                if (p.getContentType() != null && p.getContentType().startsWith("image")){
                    InputStream imageInputStream = p.getInputStream();
                    File folder = new File(System.getProperty("user.home") + "/Hojozat/restaurants/"+restaurant.getId()+"/dishes/" );
                    if (!folder.exists())
                    {
                        folder.mkdirs();
                    }
                    File file = new File(folder.getAbsolutePath()+"/"+dishId+"."+p.getContentType().substring(p.getContentType().indexOf('/')+1));
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
