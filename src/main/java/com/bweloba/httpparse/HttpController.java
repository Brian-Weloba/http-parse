package com.bweloba.httpparse;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HttpController {

    @GetMapping("/match")
    public Object getMatches() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://api.cup2022.ir/api/v1/match/")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MzdjOTY4MDQ4NzA5MjMzZmQ5NGViNGYiLCJpYXQiOjE2NjkxMDk0OTQsImV4cCI6MTY2OTE5NTg5NH0.X9l_m2ykjSqH__UBU-EkpTEP9oE9nDnGAwjOqzZPc8E")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().toString());
        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(response.body().string(), SimpleEntity.class);
        return entity;
    }

    @GetMapping("/match/{id}")
    public Object getMatchById(@PathVariable int id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://api.cup2022.ir/api/v1/match/"+id)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MzdjOTY4MDQ4NzA5MjMzZmQ5NGViNGYiLCJpYXQiOjE2NjkxMDk0OTQsImV4cCI6MTY2OTE5NTg5NH0.X9l_m2ykjSqH__UBU-EkpTEP9oE9nDnGAwjOqzZPc8E")
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(response.body().string(), SimpleEntity.class);
        return entity;
    }

    @GetMapping("/standings")
    public Object getStandings() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://api.cup2022.ir/api/v1/standings")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MzdjOTY4MDQ4NzA5MjMzZmQ5NGViNGYiLCJpYXQiOjE2NjkxMDk0OTQsImV4cCI6MTY2OTE5NTg5NH0.X9l_m2ykjSqH__UBU-EkpTEP9oE9nDnGAwjOqzZPc8E")
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(response.body().string(), SimpleEntity.class);
        return entity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class SimpleEntity {
        protected String status;
        protected Object data;

        public SimpleEntity(String status, Object data) {
            this.status = status;
            this.data = data;

        }
    }
}
