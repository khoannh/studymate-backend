package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.packages.CreatePackageRequest;
import exe201.studymatebackend.dto.request.packages.UpdatePackageRequest;
import exe201.studymatebackend.dto.response.packages.PackageResponse;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Package;
import exe201.studymatebackend.repository.PackageRepository;
import exe201.studymatebackend.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    private PackageResponse mapToResponse(Package pkg) {
        return PackageResponse.builder()
                .id(pkg.getId())
                .name(pkg.getName())
                .description(pkg.getDescription())
                .tokenAmount(pkg.getTokenAmount())
                .price(pkg.getPrice())
                .build();
    }

    @Override
    public PackageResponse create(CreatePackageRequest request) {
        Package pkg = Package.builder()
                .name(request.getName())
                .description(request.getDescription())
                .tokenAmount(request.getTokenAmount())
                .price(request.getPrice())
                .build();
        return mapToResponse(packageRepository.save(pkg));
    }

    @Override
    public List<PackageResponse> getAll() {
        return packageRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PackageResponse getById(Integer id) {
        Package pkg = packageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_ID_NOT_FOUND));
        return mapToResponse(pkg);
    }

    @Override
    public PackageResponse update(Integer id, UpdatePackageRequest request) {
        Package pkg = packageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_ID_NOT_FOUND));

        pkg.setName(request.getName());
        pkg.setDescription(request.getDescription());
        pkg.setTokenAmount(request.getTokenAmount());
        pkg.setPrice(request.getPrice());

        return mapToResponse(packageRepository.save(pkg));
    }

    @Override
    public void delete(Integer id) {
        if (!packageRepository.existsById(id)) {
            throw new AppException(ErrorCode.PACKAGE_ID_NOT_FOUND);
        }
        packageRepository.deleteById(id);
    }
}