/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.concurrent.Future;

/**
 *
 * @author ranjeet
 */
public class AsyncHTTPReq {

    public static Future<HttpResponse<JsonNode>> getAsync(String url, String jsonBody) {
        Future<HttpResponse<JsonNode>> future = Unirest.post(url)
                .header("accept", "application/json")
                .body(jsonBody)
                .asJsonAsync(new Callback<JsonNode>() {

                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    @Override
                    public void completed(HttpResponse<JsonNode> response) {
                        JsonNode body = response.getBody();
                        System.out.println(body.getObject().get("result"));
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });
        return future;

    }

}
