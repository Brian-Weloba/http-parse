package com.bweloba.httpparse;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
@CrossOrigin("*")
public class HttpController {

//    @Autowired
//    private Environment environment;

    //    String token = environment.getProperty("bearer.token");
    private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MzdjOTY4MDQ4NzA5MjMzZmQ5NGViNGYiLCJpYXQiOjE2NjkyODY0MzEsImV4cCI6MTY2OTM3MjgzMX0.4Z9vbRC3zoycpH-hwyQCpMsfpqkpCQ_SshZZKzAneak";

   @GetMapping("/match/live")
   public Object getLiveMatch() throws IOException {
       OkHttpClient client = new OkHttpClient().newBuilder()
               .build();

       Request request = new Request.Builder()
               .url("https://iot.fbiego.com/worldcup/")
               .get()
               .addHeader("Content-Type", "application/json")
               .build();
       Response response = client.newCall(request).execute();
       System.out.println(response.code());
       Gson gson = new Gson();
       SimpleEntity2 entity = gson.fromJson(response.body().charStream(), SimpleEntity2.class);
       System.out.println(entity);
       return entity;
   }

    @GetMapping("/match")
    public Object getMatches() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("http://api.cup2022.ir/api/v1/match")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(response.body().string(), SimpleEntity.class);

//        if (response.code() == 401) {
//            System.out.println("token ::" + this.token);
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(mediaType, "{\r\n\"email\": \"bweloba@gmail.com\",\r\n\"password\": \"Nyongesa4\"\r\n}");
//            request = new Request.Builder()
//                    .url("http://api.cup2022.ir/api/v1/user/login")
//                    .method("POST", body)
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//            response = client.newCall(request).execute();
//            gson = new Gson();
//            SimpleEntity2 entity2 = gson.fromJson(response.body().string(), SimpleEntity2.class);
//            this.token = entity2.data.get("token");
//            System.out.println("token2 ::" + this.token);
//            this.getMatches();
//            return entity;
//        }
        return entity;
    }

    @GetMapping("/match/{id}")
    public Object getMatchById(@PathVariable int id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://api.cup2022.ir/api/v1/match/" + id)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
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
                .addHeader("Authorization", "Bearer " + token)
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

    @Getter
    @Setter
    @NoArgsConstructor
    private class SimpleEntity2 {
        protected int time;
        protected HashMap<String, Object> match = new HashMap<>();

        public SimpleEntity2(int time, HashMap<String, Object> match) {
            this.time = time;
            this.match = match;

        }
    }
}
