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
@Table(name = "services")
@NamedEntityGraph(
        name = "service-categories-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "categories")
        }
)
public class Service extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workerId;

    private String name;

    private String description;

    private Integer price;

    @NotAudited
    @ManyToMany
    @JoinTable(
            name = "service_categories",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany
    @NotAudited
    @JoinColumn(name = "service_id")
    private List<Image> images;
}
