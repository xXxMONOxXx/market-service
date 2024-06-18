package edu.azati.marketservice.model.rev;

import edu.azati.marketservice.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_aud")
public class ProductRev extends Product {

    private Integer rev;
}
