package edu.azati.marketservice.model;

import edu.azati.marketservice.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;


@Entity
@Getter
@Setter
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@NamedEntityGraph(
        name = "product-order-address-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "address")
        }
)
public class ProductOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean orderHasProducts = true;

    @Enumerated
    private Status currentStatus;

    private String description;

    @ManyToOne
    @NotAudited
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "created_by")
    private Long createdBy;

    @NotAudited
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductOrderProduct> products;
}
