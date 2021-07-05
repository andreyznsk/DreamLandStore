package ru.geekbrains.DreamLandStore.model.entry;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "orders")
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

    public Order() {
        this.orderDetailsList = new ArrayList<>();
    }

    public Order(Long orderId, Long customerId, String deliveryAddress, boolean isOrdered, boolean isPaid, boolean isDelivered, LocalDate orderDate, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.isOrdered = isOrdered;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderDetailsList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return isOrdered == order.isOrdered &&
                isPaid == order.isPaid &&
                isDelivered == order.isDelivered &&
                Objects.equals(orderId, order.orderId) &&
                Objects.equals(customerId, order.customerId) &&
                Objects.equals(deliveryAddress, order.deliveryAddress) &&
                Objects.equals(orderDate, order.orderDate) &&
                totalPrice.compareTo(order.totalPrice) < 0.0001 &&
                Objects.equals(orderDetailsList, order.orderDetailsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, deliveryAddress, isOrdered, isPaid, isDelivered, orderDate, totalPrice, orderDetailsList);
    }
}