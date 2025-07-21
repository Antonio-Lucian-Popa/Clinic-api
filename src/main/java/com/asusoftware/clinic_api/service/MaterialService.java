package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Material;
import com.asusoftware.clinic_api.model.dto.MaterialRequest;
import com.asusoftware.clinic_api.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repo;

    public Material create(MaterialRequest dto, Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        Material m = new Material();
        m.setName(dto.getName());
        m.setUnit(dto.getUnit());
        m.setTenantId(tenantId);
        return repo.save(m);
    }

    public List<Material> getAll(Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        return repo.findByTenantId(tenantId);
    }
}
