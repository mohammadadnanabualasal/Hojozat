package com.example.hojozat.entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "restaurant", schema = "Hojozat", catalog = "")
public class RestaurantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "location")
    private String location;
    @Basic
    @Column(name = "familiesSection")
    private String familiesSection;
    @Basic
    @Column(name = "tablesNumber")
    private int tablesNumber;

    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "about")
    private String about;
    @Basic
    @Column(name = "reservedTables")
    private int reservedTables;
    @Basic
    @Column(name = "servingFromTime")
    private Time servingFromTime;
    @Basic
    @Column(name = "servingToTime")
    private Time servingToTime;

    public static int addNewRestaurant(RestaurantEntity restaurantEntity, boolean update) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (update) {
                entityManager.merge(restaurantEntity);
            } else {
                entityManager.persist(restaurantEntity);
            }
            entityManager.flush();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return restaurantEntity.getId();

    }

    public static RestaurantEntity getRestaurantByEmail(String email) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        RestaurantEntity restaurant;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM  restaurant WHERE email='" + email + "';", RestaurantEntity.class);
            restaurant = (RestaurantEntity) query.getResultList().get(0);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return restaurant;
    }

    public static RestaurantEntity getRestaurantById(String id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        RestaurantEntity restaurant;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM  restaurant WHERE id='" + id + "';", RestaurantEntity.class);
            restaurant = (RestaurantEntity) query.getResultList().get(0);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return restaurant;
    }

    public static List<RestaurantEntity> getAllRestaurants(String term) {
        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            if (term.isEmpty()) {
                Query query = entityManager.createNativeQuery("SELECT * FROM restaurant", RestaurantEntity.class);
                restaurantEntities = query.getResultList();
            } else {
                Query query = entityManager.createNativeQuery("SELECT * FROM restaurant WHERE name LIKE '%" + term + "%' ORDER BY INSTR(name,'" + term + "');", RestaurantEntity.class);
                restaurantEntities = query.getResultList();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return restaurantEntities;
    }

    public static boolean removeRestaurant(RestaurantEntity restaurant) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(restaurant) ? restaurant : entityManager.merge(restaurant));
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return true;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFamiliesSection() {
        return familiesSection;
    }

    public void setFamiliesSection(String familiesSection) {
        this.familiesSection = familiesSection;
    }

    public int getTablesNumber() {
        return tablesNumber;
    }

    public void setTablesNumber(int tablesNumber) {
        this.tablesNumber = tablesNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantEntity that = (RestaurantEntity) o;
        return id == that.id && tablesNumber == that.tablesNumber && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(familiesSection, that.familiesSection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone, name, location, familiesSection, tablesNumber);
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getReservedTables() {
        return reservedTables;
    }

    public void setReservedTables(int reservedTables) {
        this.reservedTables = reservedTables;
    }

    public Time getServingFromTime() {
        return servingFromTime;
    }

    public void setServingFromTime(Time servingFromTime) {
        this.servingFromTime = servingFromTime;
    }

    public Time getServingToTime() {
        return servingToTime;
    }

    public void setServingToTime(Time servingToTime) {
        this.servingToTime = servingToTime;
    }

    public static int addNewDish(DishesEntity dishesEntity, boolean update) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (update) {
                entityManager.merge(dishesEntity);
            } else {
                entityManager.persist(dishesEntity);
            }
            entityManager.flush();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return dishesEntity.getId();

    }

    public static List<DishesEntity> getAllDishes() {
        List<DishesEntity> dishesEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM dishes;", DishesEntity.class);
            dishesEntities = query.getResultList();
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return dishesEntities;
    }

    public List<DishesEntity> getAllDishesForRestaurant() {
        List<DishesEntity> dishesEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM dishes where restaurantId=" + getId() + ";", DishesEntity.class);
            dishesEntities = query.getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return dishesEntities;
    }

    public void update() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(this);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public List<ReservationEntity> getReservations()
    {
        List<ReservationEntity> reservation = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM reservation where restaurantId=" + getId() + ";", ReservationEntity.class);
            reservation = query.getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return reservation;

    }

    public List<ReservationEntity> getReservationsForTime(String time)
    {
        List<ReservationEntity> reservation = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM reservation where restaurantId=" + getId()
                    + " and time='" + time + "';", ReservationEntity.class);
            reservation = query.getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return reservation;

    }


    public boolean availableForNewReservation(String time){
        if (getReservationsForTime(time).size() >= getTablesNumber()){
            return false;
        }else {
            return true;
        }
    }

    public int[] getServingHours() {
        LocalTime start = getServingFromTime().toLocalTime().plusHours(getServingFromTime().toLocalTime().getMinute() ==0
                || getServingFromTime().toLocalTime().getHour() >= getServingToTime().toLocalTime().getHour()?0:1).truncatedTo(ChronoUnit.HOURS);
        LocalTime end = getServingToTime().toLocalTime().minusHours(0).truncatedTo(ChronoUnit.HOURS);
        if (end.getHour() <= start.getHour()){
            return new int[0];
        }
        int[] array = new int[end.getHour() - start.getHour()];
        int i =0;
        for (int j = start.getHour(); j < end.getHour(); j++) {
            array[i] = j;
            i++;
        }
        return array;
    }
}
