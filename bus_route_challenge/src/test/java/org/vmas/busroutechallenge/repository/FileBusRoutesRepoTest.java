package org.vmas.busroutechallenge.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class FileBusRoutesRepoTest {
  private static final int MAX_ROUTES_LIMIT = 100_000;

  private FileBusRoutesRepo repo;
  private FileBusRoutesRepo repo2;

  @Before
  public void setUp() throws Exception {
    repo = new FileBusRoutesRepo(new ClassPathResource("route1"));
    repo2 = createRepoWithTwoStationsInAllRoutes();
  }

  @Test
  public void testStationsConnections() {
    assertTrue(repo.findAnyRouteWithStations(153, 17).isPresent());
    assertFalse(repo.findAnyRouteWithStations(153, 1).isPresent());
  }

  @Test(timeout = 50)
  public void stationExistsButHasNoCommonRoute() {
    assertFalse(repo2.findAnyRouteWithStations(10, 20).isPresent());
  }

  private FileBusRoutesRepo createRepoWithTwoStationsInAllRoutes() {
    Map<Integer, SortedSet<Integer>> map = new HashMap<>();
    TreeSet<Integer> set10 = new TreeSet<>();
    TreeSet<Integer> set20 = new TreeSet<>();

    for (int i = 0; i < MAX_ROUTES_LIMIT; i++) {
      set10.add(i);
      set20.add(MAX_ROUTES_LIMIT + i);
    }
    map.put(10, set10);
    map.put(20, set20);
    return new FileBusRoutesRepo(map);
  }
}
