package com.example.hojozat.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "Hojozat", catalog = "")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "dishId")
    private int dishId;
    @Basic
    @Column(name = "reservationId")
    private int reservationId;
    @Basic
    @Column(name = "quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id && dishId == that.dishId && reservationId == that.reservationId && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishId, reservationId, quantity);
    }

    public DishesEntity getDish() {
        return DishesEntity.getDishById(getDishId());
    }

    public static int addOrder(OrderEntity orderEntity, boolean update) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (update){
                entityManager.merge(orderEntity);
            }else {
                entityManager.persist(orderEntity);
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
        return orderEntity.getId();

    }

    public static OrderEntity getByDishAndReservationID(int dishId, int reservationId)
    {
        OrderEntity orderEntity = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM  orders WHERE dishId=" + dishId + " and reservationId="+reservationId+";", OrderEntity.class);
            orderEntity = (OrderEntity) query.getResultList().get(0);
        } catch (Exception exception) {
            return null;
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return orderEntity;
    }

    public static boolean removeOrder(OrderEntity orderEntity)
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(orderEntity) ? orderEntity : entityManager.merge(orderEntity));
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
}
