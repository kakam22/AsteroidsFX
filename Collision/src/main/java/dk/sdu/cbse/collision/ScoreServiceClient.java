package dk.sdu.cbse.collision;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ScoreServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private boolean unavailableWarningShown;

    public ScoreServiceClient() {
        this("http://localhost:8080/score");
    }

    public ScoreServiceClient(String baseUrl) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        this.restTemplate = new RestTemplate(requestFactory);
        this.baseUrl = baseUrl;
    }

    public void addScore(int points) {
        try {
            restTemplate.postForLocation(baseUrl + "/add?points={points}", null, points);
        } catch (RestClientException e) {
            if (!unavailableWarningShown) {
                System.err.println("ScoreService is not reachable at " + baseUrl + ". Start ScoreService before the game to enable scoring.");
                unavailableWarningShown = true;
            }
        }
    }
}
