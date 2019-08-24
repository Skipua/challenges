package org.vmas.busroutechallenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectBusRouteDto {
  @JsonProperty("dep_sid")
  private int departureStation;
  @JsonProperty("arr_sid")
  private int arrivalStation;
  @JsonProperty("direct_bus_route")
  private boolean directConnection;
}
