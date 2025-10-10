package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.packages.CreatePackageRequest;
import exe201.studymatebackend.dto.request.packages.UpdatePackageRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.packages.PackageResponse;
import exe201.studymatebackend.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping
    public ApiResponse<PackageResponse> create(@RequestBody CreatePackageRequest request) {
        return ApiResponse.<PackageResponse>builder()
                .result(packageService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PackageResponse>> getAll() {
        return ApiResponse.<List<PackageResponse>>builder()
                .result(packageService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PackageResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<PackageResponse>builder()
                .result(packageService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PackageResponse> update(@PathVariable Integer id,
                                               @RequestBody UpdatePackageRequest request) {
        return ApiResponse.<PackageResponse>builder()
                .result(packageService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Integer id) {
        packageService.delete(id);
        return ApiResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }
}
