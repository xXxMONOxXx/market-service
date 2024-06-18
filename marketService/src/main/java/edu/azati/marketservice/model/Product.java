package edu.azati.marketservice.model;

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
@Table(name = "products")
@NamedEntityGraph(
        name = "product-categories-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "categories")
        }
)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seller_id")
    private Long sellerId;

    private String name;

    private Integer quantity;

    private String description;

    private Integer price;

    @NotAudited
    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<ProductPriceHistory> priceHistory;

    @OneToMany
    @NotAudited
    @JoinColumn(name = "product_id")
    private List<Image> images;

    private String productDetails;
}
