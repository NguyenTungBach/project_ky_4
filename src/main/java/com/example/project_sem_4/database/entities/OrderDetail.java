package com.example.project_sem_4.database.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order_details")
public class OrderDetail {
    @EmbeddedId
    private PK pk;
    private double unit_price;
    @Data
    @Embeddable
    public static class PK implements Serializable {
        private int order_id;
        private int service_id;

        public PK(int order_id, int service_id) {
            this.order_id = order_id;
            this.service_id = service_id;
        }

        public PK() {

        }
    }
}
