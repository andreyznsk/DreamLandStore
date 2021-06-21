package ru.geekbrains.DreamLandStore.model.entry;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "chart")
@Data
public class Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "prod_id")
    private Long prodId;

    @ManyToOne
    @JoinColumn(name = "prod_id", insertable = false, updatable = false)
    Product product;
}
