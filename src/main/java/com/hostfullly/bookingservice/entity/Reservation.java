package com.hostfullly.bookingservice.entity;

import com.hostfullly.bookingservice.enums.BookingStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Reservation extends BaseEntity {

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;

    @Override
    public void onCreate() {
        this.status = this.user == null ? BookingStatusEnum.BLOCKED : BookingStatusEnum.CREATED;
        this.setCreatedAt(new Date());
    }
}
