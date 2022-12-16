package com.bweloba.httpparse;

import com.bweloba.httpparse.models.Token;
import com.bweloba.httpparse.models.TokenService;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
// @RequestMapping("/test")
@CrossOrigin("*")
public class HttpController {

    @Autowired
    TokenService tokenService;

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
        // tokenService.updateToken("initialize");
        String token = tokenService.getTokenById(1).getToken();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout

        OkHttpClient client = builder.build();

        Request request = new Request.Builder()
                .url("http://api.cup2022.ir/api/v1/match")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.code());
        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(response.body().string(), SimpleEntity.class);

        if (response.code() == 401) {
            System.out.println("token ::" + token);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\r\n\"email\": \"bweloba@gmail.com\",\r\n\"password\": \"Nyongesa4\"\r\n}");
            request = new Request.Builder()
                    .url("http://api.cup2022.ir/api/v1/user/login")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            response = client.newCall(request).execute();
            gson = new Gson();
            SimpleEntity3 entity3 = gson.fromJson(response.body().string(),
                    SimpleEntity3.class);
            // HttpController.token = (String) entity3.data.get("token");
            System.out.println("token2 ::" + (String) entity3.data.get("token"));
            tokenService.updateToken((String) entity3.data.get("token"));
            this.getMatches();
            return entity;
        }
        return entity;
    }

    @GetMapping("/match/{id}")
    public Object getMatchById(@PathVariable int id) throws IOException {
        String token = tokenService.getTokenById(1).getToken();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
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
        String token = tokenService.getTokenById(1).getToken();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
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

    @Getter
    @Setter
    @NoArgsConstructor
    private class SimpleEntity3 {
        protected String status;
        protected HashMap<String, Object> data;

        public SimpleEntity3(String status, HashMap<String, Object> data) {
            this.status = status;
            this.data = data;

        }
    }
}
