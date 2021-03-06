package com.pechnikovdg.restaurantvoting.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "restaurant_unique_name_idx"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Restaurant extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String name;

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
