package com.veger.spring.rest;

import com.veger.spring.rest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private String cookies;
    private final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});

        this.cookies = responseEntity.getHeaders().getFirst("Set-Cookie");

        List<User> allUsers = responseEntity.getBody();
        return allUsers;
    }

    public void saveUser(User user) {
        httpHeaders.add("Cookie", cookies);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
        System.out.println("new user was added to DB");
    }

    public void updateUser(User user) {
        long id = user.getId();
        httpHeaders.add("Cookie", cookies);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);


        restTemplate.put(URL, user);
        System.out.println("user with id " + id + " was update");
    }

    public void deleteUser(User user, int id) {
        httpHeaders.add("Cookie", cookies);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, requestEntity, String.class);
        System.out.println("user with id " + id + " was delete");
    }
}
