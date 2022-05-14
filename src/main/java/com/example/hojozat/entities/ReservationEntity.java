package com.example.hojozat.entities;

import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import javax.print.DocFlavor;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reservation", schema = "Hojozat", catalog = "")
public class ReservationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "userId")
    private int userId;
    @Basic
    @Column(name = "restaurantId")
    private int restaurantId;
    @Basic
    @Column(name = "time")
    private String time;
    @Basic
    @Column(name = "numberOfPersons")
    private int numberOfPersons;
    @Basic
    @Column(name = "orderOnline")
    private String orderOnline;
    @Basic
    @Column(name = "date")
    private Date date;

    public static int addReservation(ReservationEntity reservationEntity) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(reservationEntity);
            entityManager.flush();
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
        return reservationEntity.getId();

    }

    public static List<ReservationEntity> getAllReservationsForUserById(int userId) {
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM reservation where userId=" + userId + ";", ReservationEntity.class);
        reservationEntities = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return reservationEntities;
    }

    public static ReservationEntity getReservationsByUserAndRestaurant(int userId, int restaurantId) {
        ReservationEntity reservationEntity = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM reservation where userId=" + userId + " and restaurantId=" + restaurantId + ";", ReservationEntity.class);
        if (query.getResultList().size() > 0) {
            reservationEntity = (ReservationEntity) query.getResultList().get(0);
        } else {
            entityManager.close();
            entityManagerFactory.close();
            return null;
        }
        entityManager.close();
        entityManagerFactory.close();
        return reservationEntity;
    }

    public static ReservationEntity getById(String id) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            ReservationEntity reservationEntity;
            Query query = entityManager.createNativeQuery("SELECT * FROM  reservation WHERE id='" + id + "';", ReservationEntity.class);
            reservationEntity = (ReservationEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return reservationEntity;
        } catch (Exception exception) {
            return null;
        }
    }

    public static boolean removeReservation(ReservationEntity reservationEntity) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(reservationEntity) ? reservationEntity : entityManager.merge(reservationEntity));
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return ((RestaurantEntity) RestaurantEntity.getRestaurantById(getRestaurantId() + "")).getName();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getOrderOnline() {
        return orderOnline;
    }

    public void setOrderOnline(String orderOnline) {
        this.orderOnline = orderOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return id == that.id && userId == that.userId && restaurantId == that.restaurantId && numberOfPersons == that.numberOfPersons && Objects.equals(time, that.time) && Objects.equals(orderOnline, that.orderOnline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, restaurantId, numberOfPersons, orderOnline);
    }

    public List<OrderEntity> getAllOrders() {
        List<OrderEntity> orderEntities = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM orders where reservationId=" + getId() + ";", OrderEntity.class);
        orderEntities = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return orderEntities;
    }

    public UserEntity getUser() {
        return UserEntity.getUserById(getUserId() + "");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static ReservationEntity getReservationByUserDateRestaurant(String userId, String restaurantId, Date date) {
        ReservationEntity reservationEntity;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("SELECT * FROM reservation  where userId=" + userId + " and restaurantId=" + restaurantId + " and date='" + date + "';", ReservationEntity.class);
            reservationEntity = (ReservationEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return reservationEntity;
        }catch (Exception exception){
            return null;
        }
        }
    }
