package com.example.orderdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.orderdemo.entity.base.BaseEntity;
import com.example.orderdemo.entity.enums.OrderSimpleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.ORDINAL)

    private OrderSimpleStatus status;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails;

    private LocalDateTime createdAt;
    public Order() {
        this.setCreatedAt(LocalDateTime.now());
    }

    @PostPersist
    public void updateSlug() {
        System.out.println("Before save");
        System.out.println(id);
    }

    @PostUpdate
    public void afterSave() {
        System.out.println("After save");
        System.out.println(id);
    }

    public void addTotalPrice(OrderDetail orderDetail) {
        if (this.totalPrice == null) {
            this.totalPrice = new BigDecimal(0);
        }
        BigDecimal quantityInBigDecimal = new BigDecimal(orderDetail.getQuantity());
        this.totalPrice = this.totalPrice.add(
                orderDetail.getUnitPrice().multiply(quantityInBigDecimal));
    }
}
