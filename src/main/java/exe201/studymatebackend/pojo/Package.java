package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    private Integer price;

    @OneToMany(mappedBy = "apackage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PaymentTransaction> packageTransactionList;

    public Package(Integer id, String name, String description, Integer tokenAmount, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tokenAmount = tokenAmount;
        this.price = price;
    }

}