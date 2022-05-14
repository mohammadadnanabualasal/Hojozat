package com.example.hojozat.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "restaurant", schema = "Hojozat", catalog = "")
public class RestaurantEntity {
    @Id
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

    public static boolean addNewRestaurant(RestaurantEntity restaurantEntity, boolean update) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (update) {
                entityManager.merge(restaurantEntity);
            } else {
                entityManager.persist(restaurantEntity);
            }
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;

    }

    public static RestaurantEntity getRestaurantByEmail(String email) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            RestaurantEntity restaurant;
            Query query = entityManager.createNativeQuery("SELECT * FROM  restaurant WHERE email='" + email + "';", RestaurantEntity.class);
            restaurant = (RestaurantEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return restaurant;
        } catch (Exception exception) {
            return null;
        }
    }

    public static RestaurantEntity getRestaurantById(String id) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            RestaurantEntity restaurant;
            Query query = entityManager.createNativeQuery("SELECT * FROM  restaurant WHERE id='" + id + "';", RestaurantEntity.class);
            restaurant = (RestaurantEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return restaurant;
        } catch (Exception exception) {
            return null;
        }
    }

    public static List<RestaurantEntity> getAllRestaurants(String term) {
        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        if (term.isEmpty()) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("SELECT * FROM restaurant", RestaurantEntity.class);
            restaurantEntities = query.getResultList();
            entityManager.close();
            entityManagerFactory.close();
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("SELECT * FROM restaurant WHERE name LIKE '%" + term + "%' ORDER BY INSTR(name,'" + term + "');", RestaurantEntity.class);
            restaurantEntities = query.getResultList();
            entityManager.close();
            entityManagerFactory.close();
        }
        return restaurantEntities;
    }

    public static boolean removeRestaurant(RestaurantEntity restaurant) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(restaurant) ? restaurant : entityManager.merge(restaurant));
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
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
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (update) {
                entityManager.merge(dishesEntity);
            } else {
                entityManager.persist(dishesEntity);
            }
            entityManager.flush();
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
        return dishesEntity.getId();

    }

    public static List<DishesEntity> getAllDishes() {
        List<DishesEntity> dishesEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM dishes;", DishesEntity.class);
        dishesEntities = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return dishesEntities;
    }

    public List<DishesEntity> getAllDishesForRestaurant() {
        List<DishesEntity> dishesEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM dishes where restaurantId=" + getId() + ";", DishesEntity.class);
        dishesEntities = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return dishesEntities;
    }

    public void update() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(this);
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public List<ReservationEntity> getReservations()
    {
        List<ReservationEntity> reservation = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM reservation where restaurantId=" + getId() + ";", ReservationEntity.class);
        reservation = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return reservation;

    }

    public List<ReservationEntity> getReservationsForTime(String time)
    {
        List<ReservationEntity> reservation = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM reservation where restaurantId=" + getId()
                + " and time='"+time+"';", ReservationEntity.class);
        reservation = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return reservation;

    }


    public boolean availableForNewReservation(String time){
        if (getReservationsForTime(time).size() >= getTablesNumber()){
            return false;
        }else {
            return true;
        }
    }
}
