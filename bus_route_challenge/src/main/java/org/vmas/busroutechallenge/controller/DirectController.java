package org.vmas.busroutechallenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vmas.busroutechallenge.controller.dto.DirectBusRouteDto;
import org.vmas.busroutechallenge.service.DirectRouteChecker;

@RestController
@RequiredArgsConstructor
public class DirectController {

  private final DirectRouteChecker directRouteChecker;

  @GetMapping("/api/direct")
  public DirectBusRouteDto directApi(
      @RequestParam(name = "dep_sid") int departureStationId,
      @RequestParam(name = "arr_sid") int arrivalStationId) {
    boolean hasDirectRoute = directRouteChecker
        .hasDirectRoute(departureStationId, arrivalStationId);
    return DirectBusRouteDto.builder()
        .departureStation(departureStationId)
        .arrivalStation(arrivalStationId)
        .directConnection(hasDirectRoute)
        .build();
  }
}
