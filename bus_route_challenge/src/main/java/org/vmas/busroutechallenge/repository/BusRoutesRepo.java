package org.vmas.busroutechallenge.repository;

import java.util.Optional;

public interface BusRoutesRepo {

  Optional<Integer> findAnyRouteWithStations(int depStation, int arrStation);
}
