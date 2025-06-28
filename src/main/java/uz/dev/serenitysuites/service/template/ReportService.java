package uz.dev.serenitysuites.service.template;

import uz.dev.serenitysuites.dto.response.OccupancyReportDTO;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:39
 **/

public interface ReportService {
    OccupancyReportDTO getOccupancyReport(LocalDate startDate, LocalDate endDate);

}
