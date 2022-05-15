package com.example.hojozat;

import com.example.hojozat.entities.OrderEntity;
import com.example.hojozat.entities.ReservationEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Scheduled(fixedDelay = 10000)
    public void autoRemoveOutOfDateReservations() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hojozat");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Date date = new Date(System.currentTimeMillis());
            Query query = entityManager.createNativeQuery("SELECT * FROM reservation where date < '" + date + "';", ReservationEntity.class);
            List<ReservationEntity> list;
            list = query.getResultList();
            for (ReservationEntity reservation: list
            ) {
                reservation.getAllOrders().forEach(orderEntity -> OrderEntity.removeOrder(orderEntity));
                ReservationEntity.removeReservation(reservation);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
