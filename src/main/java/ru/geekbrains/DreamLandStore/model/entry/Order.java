package ru.geekbrains.DreamLandStore.model.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "is_ordered")
    private boolean isOrdered;

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column(name = "is_delivered")
    private boolean isDelivered;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(cascade=CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetailsList;
}