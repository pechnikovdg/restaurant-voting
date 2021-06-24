package com.pechnikovdg.restaurantvoting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dish",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "dish_date", "description", "price"}, name = "dish_unique_restaurant_date_description_price_idx"),
        indexes = @Index(columnList = "dish_date, restaurant_id", name = "dish_date_restaurant_idx"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@NamedEntityGraph(name = "Dish.restaurant",
        attributeNodes = @NamedAttributeNode("restaurant")
)
public class Dish extends BaseEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 10000)
    private int price;

    @Column(name = "dish_date")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    public Dish(Integer id, String description, int price, LocalDate date, Restaurant restaurant) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }
}
