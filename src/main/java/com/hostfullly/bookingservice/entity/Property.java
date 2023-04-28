package com.hostfullly.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "property")
@Getter
@Setter
@ToString
public class Property extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private Integer capacity;

    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}