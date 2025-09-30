package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "packages") // tránh trùng keyword
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Integer tokenAmount;

    private Double price;
}