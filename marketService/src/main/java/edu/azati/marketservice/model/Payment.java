package edu.azati.marketservice.model;

import edu.azati.marketservice.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated
    private Status currentStatus;

    private Long orderId;

    private String stripeToken;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
