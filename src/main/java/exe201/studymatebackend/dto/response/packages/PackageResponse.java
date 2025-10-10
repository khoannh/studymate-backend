package exe201.studymatebackend.dto.response.packages;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer tokenAmount;
    private Double price;
}
