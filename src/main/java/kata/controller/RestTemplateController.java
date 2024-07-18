package kata.controller;

import kata.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateController {

    private final String restURL = "http://94.198.50.185:7081/api/users";
    public StringBuilder joinAnswer = new StringBuilder();

    private String session_id;

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public HttpHeaders getSession_id(HttpHeaders headers) {
        ResponseEntity<String> response = restTemplate.getForEntity(restURL, String.class);
        session_id = response.getHeaders().getFirst("set-cookie");
        headers.set("Cookie", session_id);
        return headers;
    }

    public void saveUser(HttpEntity<User> entity) {
        String response = restTemplate.postForObject(restURL, entity, String.class);
        joinAnswer.append(response);
    }

    public void updateUser(HttpEntity<User> entity) {
        ResponseEntity<String> response = restTemplate.exchange(restURL, HttpMethod.PUT, entity, String.class);
        joinAnswer.append(response.getBody());
    }


public void deleteUser(HttpEntity<User> entity) {
    User user = entity.getBody();
    Long userId = user.getId(); // получаем id пользователя из entity

    ResponseEntity<String> response = restTemplate.exchange(restURL + "/" + userId, HttpMethod.DELETE, entity, String.class);
    joinAnswer.append(response.getBody());
}



}
