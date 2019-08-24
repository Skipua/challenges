package org.vmas.busroutechallenge.repository;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileBusRoutesRepo implements BusRoutesRepo {

  /**
   * Maps stationId to all routes that it is part of
   */
  private final Map<Integer, SortedSet<Integer>> stationsToRoutesMap;

  @Autowired
  public FileBusRoutesRepo(@Value("file:${bus.routes.data.file}") Resource busRouteData) {
    this.stationsToRoutesMap = new HashMap<>();
    parseRouteData(busRouteData);
  }

  public FileBusRoutesRepo(Map<Integer, SortedSet<Integer>> stationsToRoutesMap) {
    this.stationsToRoutesMap = stationsToRoutesMap;
  }

  @SneakyThrows
  private void parseRouteData(Resource busRouteData) {
    List<String> lines = IOUtils
        .readLines(busRouteData.getInputStream(), Charset.forName("UTF-8"));
    Integer numberOfRoutes = Integer.valueOf(lines.get(0));
    lines.stream()
        .skip(1)
        .forEach(this::rawLineToRoute);
    log.info("Parsed {} routes", numberOfRoutes);
  }

  private void rawLineToRoute(String line) {
    List<Integer> intsLine = Arrays.stream(line.split("\\s+"))
        .map(Integer::valueOf)
        .collect(Collectors.toList());

    Integer routeId = intsLine.get(0);

    intsLine.stream().skip(1)
        .forEach(statition -> {
          stationsToRoutesMap.putIfAbsent(statition, new TreeSet<>());
          stationsToRoutesMap.get(statition).add(routeId);
        });
  }

  @Override
  public Optional<Integer> findAnyRouteWithStations(int depStation, int arrStation) {
    SortedSet<Integer> routesWithDep = stationsToRoutesMap
        .getOrDefault(depStation, Collections.emptySortedSet());
    SortedSet<Integer> routesWithArr = stationsToRoutesMap
        .getOrDefault(arrStation, Collections.emptySortedSet());

    if (routesWithDep.isEmpty() || routesWithArr.isEmpty()) {
      return Optional.empty();
    } else {
      for (Integer route : routesWithDep) {
        if (routesWithArr.contains(route)) {
          return Optional.of(route);
        }
      }
      return Optional.empty();
    }
  }
}
