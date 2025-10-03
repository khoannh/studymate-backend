package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionID;

    @Column(unique = true, nullable = false)
    private String actionName;

    @Column(nullable = false)
    private int actionCoin;

    public Action(String actionName, int actionCoin) {
        this.actionName = actionName;
        this.actionCoin = actionCoin;
    }
}
