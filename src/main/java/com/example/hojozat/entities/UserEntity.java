package com.example.hojozat.entities;

import org.apache.catalina.User;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "Hojozat", catalog = "")
public class UserEntity {
    @Id
    private int userId;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "LastName")
    private String lastName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "isAdmin")
    private String isAdmin;

    public static UserEntity getUserByEmail(String email) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            UserEntity user;
            Query query = entityManager.createNativeQuery("SELECT * FROM  user WHERE email='" + email + "';", UserEntity.class);
            user = (UserEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public static UserEntity getUserById(String id) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            UserEntity user;
            Query query = entityManager.createNativeQuery("SELECT * FROM  user WHERE userId='" + id + "';", UserEntity.class);
            user = (UserEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public static int getGreaterIdOfTable(String tableName) {
        String queryStatement = "SELECT MAX(userId) FROM " + tableName + " LIMIT 1;";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(queryStatement);
        return query.getResultList().get(0) != null ? (int) query.getResultList().get(0) : 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(password, that.password) && Objects.equals(isAdmin, that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, phone, password, isAdmin);
    }

    public static boolean addNewUser(UserEntity user, boolean update) {
        try {
            if (user.getIsAdmin() == null) {
                user.setIsAdmin("NO");
            }
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (update) {
                entityManager.merge(user);
            } else {
                entityManager.persist(user);
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

    public static List<UserEntity> getAllUsers(String term) {
        List<UserEntity> userEntities = new ArrayList<>();
        if (term.isEmpty()) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("SELECT * FROM user", UserEntity.class);
            userEntities = query.getResultList();
            entityManager.close();
            entityManagerFactory.close();
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("SELECT * FROM user WHERE email LIKE '%" + term + "%'  ORDER BY INSTR(email,'" + term + "');", UserEntity.class);
            userEntities = query.getResultList();
            entityManager.close();
            entityManagerFactory.close();
        }
        return userEntities;
    }

    public static List<UserEntity> getAllAdmins() {
        List<UserEntity> userEntities = new ArrayList<>();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM user WHERE isAdmin = 'YES';", UserEntity.class);
        userEntities = query.getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return userEntities;
    }

    public static boolean removeUser(UserEntity user) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;

    }

    public int getCartNumberOfOrders() {
        int number = 0;
        List<ReservationEntity> reservationEntities = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM reservation where userId=" + getUserId() + ";", ReservationEntity.class);
        reservationEntities = query.getResultList();
        for (ReservationEntity reservationEntity : reservationEntities
        ) {
            Query query2 = entityManager.createNativeQuery("SELECT * FROM orders where reservationId=" + reservationEntity.getId() + ";", OrderEntity.class);
            for (OrderEntity orderEntity : (List<OrderEntity>) query2.getResultList()
            ) {
                number += orderEntity.getQuantity();
            }
        }
        entityManager.close();
        entityManagerFactory.close();
        return number;
    }


}
