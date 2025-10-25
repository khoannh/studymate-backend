package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "packages") // tránh trùng keyword
@Getter
@Setter
@NoArgsConstructor

@Builder
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Integer tokenAmount;

    private Double price;
    public Package(Integer id, String name, String description, Integer tokenAmount, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tokenAmount = tokenAmount;
        this.price = price;
    }

}