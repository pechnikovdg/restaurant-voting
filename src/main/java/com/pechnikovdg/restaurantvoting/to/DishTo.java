package com.pechnikovdg.restaurantvoting.to;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DishTo extends BaseTo {

    @NotBlank
    @Size(max = 100)
    private String description;

    @Range(min = 10, max = 100000)
    @NotNull
    private Integer price;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer restaurantId;
}
