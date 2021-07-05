package ru.geekbrains.DreamLandStore.model.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;


    @Column(name = "prod_id", insertable = false, updatable = false)
    private Long prodId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_id")
    private Product product;

}