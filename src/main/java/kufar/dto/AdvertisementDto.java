package kufar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDto {

  private String ad_id;
  private String ad_link;
  private String list_id;
  private Instant list_time;
}
