package edu.azati.marketservice.model.rev;

import edu.azati.marketservice.model.Service;
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
@Table(name = "service_aud")
public class ServiceRev extends Service {

    private Integer rev;
}
