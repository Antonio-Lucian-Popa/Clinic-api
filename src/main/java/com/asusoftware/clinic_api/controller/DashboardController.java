package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.dto.AppointmentDto;
import com.asusoftware.clinic_api.model.dto.DashboardResponse;
import com.asusoftware.clinic_api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @PreAuthorize("hasAnyRole('OWNER', 'DOCTOR')")
    @GetMapping
    public DashboardResponse getDashboard(@AuthenticationPrincipal Jwt jwt) {
        return service.getDashboard(jwt);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'DOCTOR', 'ASSISTANT')")
    @GetMapping("/recent-appointments")
    public List<AppointmentDto> getRecentAppointments(@AuthenticationPrincipal Jwt jwt) {
        return service.getRecentAppointments(jwt);
    }


}
