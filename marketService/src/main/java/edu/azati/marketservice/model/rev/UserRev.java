package edu.azati.marketservice.model.rev;

import edu.azati.marketservice.model.User;
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
@Table(name = "users_aud")
public class UserRev extends User {
    
    private Integer rev;
}
