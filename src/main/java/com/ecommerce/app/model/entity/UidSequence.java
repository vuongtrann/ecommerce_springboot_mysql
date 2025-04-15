package com.ecommerce.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "uid_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UidSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long currentUid;
}
