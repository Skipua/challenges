package org.vmas.busroutechallenge.service;

public interface DirectRouteChecker {
  boolean hasDirectRoute(int departureStationId, int arrivalStationId);
}
