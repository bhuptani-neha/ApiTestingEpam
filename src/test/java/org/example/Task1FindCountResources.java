package org.example;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;


import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.*;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Task1FindCountResources {
    public static ExtractableResponse response;
    private int Id;

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @DataProvider(name="EndPoints")
    Object[][] EndPointData(){
        return new Object[][] {
                {"posts"},
                {"comments"},
                {"albums"},
                {"photos"},
                {"todos"},
                {"users"}
        };
    }

    @Test(dataProvider = "EndPoints")
    public void CountNumberOfPosts(String path){
        int  len = when().
                get(path).
        then().
                assertThat().statusCode(200).
                extract().body().path("id").toString().split(",").length;

        System.out.println("Resource: "+path+"     Count: "+len);
    }

    @Test
    public void GetAPIResourcePosts(){
        Response response =
                when().
                get("posts/2").
                then().
                assertThat().statusCode(200).
                body("id",equalTo(2)).
                extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void PostAPIResourcePosts(){
        //Create Json Object
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("userId","1");
        jsonRequest.put("title","TestEpamAPI");
        jsonRequest.put("body","testing testing testing");

        Response response  = given().
                header("Content-type", "application/json").
                body(jsonRequest.toJSONString()).
        when().
                post("posts").
        then().
                assertThat().statusCode(201).
                extract().response();
        Id = response.body().path("id");
        //System.out.println(response.body().asString());
        System.out.println(""+response.body().path("id"));
    }

    @Test
    public void PutAPIResourcePosts(){
        //Create Json Object
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("id","1");
        jsonRequest.put("userId","1");
        jsonRequest.put("title","sunt aut facere repellat provident occaecati excepturi optio reprehenderit ");
        jsonRequest.put("body","quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto");

        Response response  = given().
                header("Content-type", "application/json").
                body(jsonRequest.toJSONString()).
                when().
                put("posts/101").
                then().
                assertThat().statusCode(200).
                extract().response();
        //Id = response.body().path("id");
        System.out.println(response.body().asString());
        System.out.println(""+response.body().path("id"));
    }

    @Test
    public void DeleteAPIResourcePosts(){

        Response response  = given().
                header("Content-type", "application/json").
                when().
                delete("posts/2").
                then().
                assertThat().statusCode(200).
                extract().response();
        //Id = response.body().path("id");
        System.out.println(response.body().asString());
        //System.out.println(""+response.body().path("id"));
    }

}
