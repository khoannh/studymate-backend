package exe201.studymatebackend.dto.request.packages;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePackageRequest {
    private String name;
    private String description;
    private Integer tokenAmount;
    private Double price;
}
