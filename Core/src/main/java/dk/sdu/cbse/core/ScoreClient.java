package dk.sdu.cbse.core;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ScoreClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private boolean unavailableWarningShown;

    public ScoreClient() {
        this("http://localhost:8080/score");
    }

    public ScoreClient(String baseUrl) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        this.restTemplate = new RestTemplate(requestFactory);
        this.baseUrl = baseUrl;
    }

    public int getScore() {
        Integer score = fetchScore();
        return score == null ? 0 : score;
    }

    public Integer fetchScore() {
        try {
            String response = restTemplate.getForObject(baseUrl, String.class);
            return response == null ? null : Integer.parseInt(response.trim());
        } catch (RestClientException e) {
            if (!unavailableWarningShown) {
                System.err.println("Could not read score from " + baseUrl + ": " + e.getMessage());
                unavailableWarningShown = true;
            }
            return null;
        } catch (NumberFormatException e) {
            if (!unavailableWarningShown) {
                System.err.println("Could not parse score response from " + baseUrl + ": " + e.getMessage());
                unavailableWarningShown = true;
            }
            return null;
        }
    }

    public void resetScore() {
        try {
            restTemplate.postForLocation(baseUrl + "/reset", null);
        } catch (RestClientException ignored) {
        }
    }
}
