package com.example.hojozat.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dishes", schema = "Hojozat", catalog = "")
public class DishesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "restaurantId")
    private int restaurantId;

    public Double getPrice() {
        return price;
    }

    @Basic
    @Column(name = "price")
    private Double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishesEntity that = (DishesEntity) o;
        return id == that.id && restaurantId == that.restaurantId && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, restaurantId, price);
    }

    public static DishesEntity getDishById(int id)
    {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            DishesEntity dishesEntity;
            Query query = entityManager.createNativeQuery("SELECT * FROM  dishes WHERE id='" + id + "';", DishesEntity.class);
            dishesEntity = (DishesEntity) query.getResultList().get(0);
            entityManager.close();
            entityManagerFactory.close();
            return dishesEntity;
        } catch (Exception exception) {
            return null;
        }
    }

    public static boolean removeDish(DishesEntity dish) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(dish) ? dish : entityManager.merge(dish));
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;

    }

    public int getNumberOfOrderedDishes(int restaurantId, int userId, int dishId)
    {
        ReservationEntity reservationEntity = ReservationEntity.getReservationsByUserAndRestaurant(userId, restaurantId);
        OrderEntity orderEntity = OrderEntity.getByDishAndReservationID(dishId, reservationEntity.getId());
        if (orderEntity != null) return orderEntity.getQuantity();
        return  0;
    }
}
