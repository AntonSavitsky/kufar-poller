package kufar.unit;

import kufar.KufarPoller;
import kufar.RuleParams;
import kufar.TestContext;
import kufar.dto.AdsListDto;
import kufar.dto.AdvertisementDto;
import kufar.service.EmailService;
import kufar.service.KufarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KufarPollerTest {

    @InjectMocks
    private KufarPoller kufarPoller;
    @Mock
    private EmailService emailService;
    @Mock
    private KufarService kufarService;

    @Test
    public void testHandleRequest_NewAdsExist() {
        List<AdvertisementDto> ads = Arrays.asList(AdvertisementDto.builder()
                        .ad_id("1")
                        .list_time(Instant.now().minus(10, ChronoUnit.MINUTES))
                        .build(),
                AdvertisementDto.builder()
                        .ad_id("2")
                        .list_time(Instant.now().minus(2, ChronoUnit.MINUTES))
                        .build());
        when(kufarService.getAds()).thenReturn(AdsListDto.builder().ads(ads).build());
        doNothing().when(emailService).sendEmail(anyString(), anyString());

        kufarPoller.handleRequest(RuleParams.builder().url("1").build(), new TestContext());

        verify(emailService, times(1)).sendEmail(anyString(), anyString());
    }

    @Test
    public void testHandleRequest_NoNewAds() {
        List<AdvertisementDto> ads = Arrays.asList(AdvertisementDto.builder()
                        .ad_id("1")
                        .list_time(Instant.now().minus(10, ChronoUnit.MINUTES))
                        .build(),
                AdvertisementDto.builder()
                        .ad_id("2")
                        .list_time(Instant.now().minus(25, ChronoUnit.MINUTES))
                        .build());
        when(kufarService.getAds()).thenReturn(AdsListDto.builder().ads(ads).build());

        kufarPoller.handleRequest(RuleParams.builder().url("1").build(), new TestContext());

        verify(emailService, never()).sendEmail(anyString(), anyString());
    }
}
