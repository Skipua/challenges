package org.vmas.busroutechallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vmas.busroutechallenge.repository.BusRoutesRepo;

@Component
@RequiredArgsConstructor
public class DirectRouteCheckerImpl implements DirectRouteChecker {

  private final BusRoutesRepo busRoutesRepo;

  @Override
  public boolean hasDirectRoute(int departureStationId, int arrivalStationId) {
    return busRoutesRepo.findAnyRouteWithStations(departureStationId, arrivalStationId)
        .isPresent();
  }
}
