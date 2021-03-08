package com.challenge.demo;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import com.challenge.demo.restproject.entity.Product;
import com.challenge.demo.restproject.service.UserService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestProjectIntegrationTest extends BaseIntegrationTest {

    @Autowired
    UserService userService;



    @Test
    public void should_search_according_product_names() {
        //Given
        String keyword = "movie Regular films 20";

        //When
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity("/api/products/search/" + keyword,
                                                                              Product[].class);
        List<Product> accounts = asList(responseEntity.getBody());

        //Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //And
        accounts.forEach(acc->{assertEquals("movie Regular films 20",acc.getName()); });
    }

    @Test
    public void should_filter_by_product_genre() {
        //Given
        String genre = "Adventures";

        //When
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity("/api/products/distinctproduct/" + genre,
                                                                              Product[].class);
        List<Product> accounts = asList(responseEntity.getBody());

        //Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //And
        accounts.forEach(acc->{assertEquals("Adventures",acc.getGenre()); });
    }


    @Test
    public void user_should_exist(){

        Assert.assertNotNull(userService.userByName("Admin").getPassword());
    }

    @Test
    public void encode_password(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "12345";
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println();
        System.out.println("Password is         : " + password);
        System.out.println("Encoded Password is : " + encodedPassword);
    }




}
