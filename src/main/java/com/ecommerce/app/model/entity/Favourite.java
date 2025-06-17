package com.ecommerce.app.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "favourites")
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Favourite  {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

}
