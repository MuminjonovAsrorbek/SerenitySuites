package uz.dev.serenitysuites.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.serenitysuites.dto.response.OccupancyReportDTO;
import uz.dev.serenitysuites.service.template.ReportService;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:37
 **/

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/occupancy")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public OccupancyReportDTO getOccupancy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return reportService.getOccupancyReport(startDate, endDate);

    }

}
