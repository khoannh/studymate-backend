package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.packages.CreatePackageRequest;
import exe201.studymatebackend.dto.request.packages.UpdatePackageRequest;
import exe201.studymatebackend.dto.response.packages.PackageResponse;

import java.util.List;

public interface PackageService {
    PackageResponse create(CreatePackageRequest request);
    List<PackageResponse> getAll();
    PackageResponse getById(Integer id);
    PackageResponse update(Integer id, UpdatePackageRequest request);
    void delete(Integer id);
}