package com.pechnikovdg.restaurantvoting.to;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class VoteTo extends BaseTo {

    @NotNull
    private Integer restaurantId;
}
