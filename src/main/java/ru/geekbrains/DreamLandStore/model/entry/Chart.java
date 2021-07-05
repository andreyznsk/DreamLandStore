package ru.geekbrains.DreamLandStore.model.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "chart")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
