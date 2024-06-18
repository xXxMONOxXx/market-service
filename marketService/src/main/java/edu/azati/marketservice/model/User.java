package edu.azati.marketservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String username;

    private Boolean isBlocked = false;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserDetails userDetails;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private List<ProductOrder> productOrders;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private List<ServiceOrder> serviceOrders;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private List<Address> addresses;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private List<ProductReview> reviews;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime verifiedAt;
}
