package kufar.service;

import kufar.dto.AdsListDto;
import lombok.AllArgsConstructor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
public class KufarService {

    private RestTemplate restTemplate;

    private static final String URL = "https://re.kufar.by/api/search/ads-search/v1/engine/v1/search/raw";

    private static final UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(URL)
            .queryParam("sort", "lst.d")
            .queryParam("size", 200)
            .queryParam("cat", 1010)
            .queryParam("typ", "let")
            .queryParam("rnt", 1)
            .queryParam("rms", "v.or:1")
            .queryParam("rgn", 1)
            .queryParam("ar", 1);

    public KufarService() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
        this.restTemplate = new RestTemplate(clientHttpRequestFactory);
    }

    public AdsListDto getAds() {
        return restTemplate
                .getForObject(builder.toUriString(),
                        AdsListDto.class);
    }
}
