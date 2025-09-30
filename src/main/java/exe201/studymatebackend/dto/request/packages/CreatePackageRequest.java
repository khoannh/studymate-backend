package exe201.studymatebackend.dto.request.packages;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePackageRequest {
    private String name;
    private String description;
    private Integer tokenAmount;
    private Double price;
}
