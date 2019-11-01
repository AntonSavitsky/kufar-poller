package kufar;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import kufar.dto.AdsListDto;
import kufar.dto.AdvertisementDto;
import kufar.service.EmailService;
import kufar.service.KufarService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class KufarPoller implements RequestHandler<RuleParams, String> {
    private EmailService emailService;
    private KufarService kufarService;

    public KufarPoller() {
        kufarService = new KufarService();
        emailService = new EmailService();
    }

    public String handleRequest(RuleParams url, Context context) {

        AdsListDto adsListDto = kufarService.getAds();
        List<AdvertisementDto> newAds = adsListDto.getAds().stream()
                .filter(ad -> ad.getList_time().isAfter(Instant.now().minus(7, ChronoUnit.MINUTES)))
                .collect(Collectors.toList());

        LambdaLogger logger = context.getLogger();
        logger.log("All current ads: " + adsListDto);
        logger.log("New ads: " + newAds);

        if (!newAds.isEmpty()) {
            emailService.sendEmail("antony.sawicki@gmail.com",
                    "All ads: https://re.kufar.by/listings" +
                            "?sort=lst.d&size=30&cat=1010&typ=let&rnt=1&rms=v.or%3A1&rgn=1&ar=1 \n"
                            + newAds.toString());
        }

        return "success";
    }
}