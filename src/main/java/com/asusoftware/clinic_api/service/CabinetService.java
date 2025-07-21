package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Cabinet;
import com.asusoftware.clinic_api.model.dto.CabinetRequest;
import com.asusoftware.clinic_api.model.dto.CabinetResponse;
import com.asusoftware.clinic_api.repository.CabinetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final CabinetRepository cabinetRepository;

    public List<CabinetResponse> getCabinetsForOwner(UUID ownerId) {
        return cabinetRepository.findAllByOwnerId(ownerId).stream()
                .map(c -> new CabinetResponse(c.getId(), c.getName(), c.getAddress(), c.getPhone()))
                .toList();
    }

    public CabinetResponse createCabinet(CabinetRequest request, UUID ownerId) {
        Cabinet cabinet = new Cabinet();
        cabinet.setName(request.getName());
        cabinet.setAddress(request.getAddress());
        cabinet.setPhone(request.getPhone());
        cabinet.setOwnerId(ownerId);
        Cabinet saved = cabinetRepository.save(cabinet);
        return new CabinetResponse(saved.getId(), saved.getName(), saved.getAddress(), saved.getPhone());
    }

    public CabinetResponse getCabinetById(UUID id, UUID ownerId) {
        Cabinet cabinet = cabinetRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new CabinetResponse(cabinet.getId(), cabinet.getName(), cabinet.getAddress(), cabinet.getPhone());
    }

    public CabinetResponse updateCabinet(UUID id, CabinetRequest request, UUID ownerId) {
        Cabinet cabinet = cabinetRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        cabinet.setName(request.getName());
        cabinet.setAddress(request.getAddress());
        cabinet.setPhone(request.getPhone());
        Cabinet updated = cabinetRepository.save(cabinet);

        return new CabinetResponse(updated.getId(), updated.getName(), updated.getAddress(), updated.getPhone());
    }

    public void deleteCabinet(UUID id, UUID ownerId) {
        Cabinet cabinet = cabinetRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cabinetRepository.delete(cabinet);
    }
}

