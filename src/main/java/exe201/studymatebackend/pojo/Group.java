package exe201.studymatebackend.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupID;

    @Column(nullable = false, unique = true, length = 50)
    private String groupName;

    @Column(nullable = true)
    private String groupDescription;


    private String topic;
    private String privacy;
    private int numberOfMembers;

}
