package com.onepiece.community.provider;

import com.alibaba.fastjson.JSON;
import com.onepiece.community.dto.AccessTokenDTO;
import com.onepiece.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubPrivider {

    public String getAccessToken(AccessTokenDTO dto) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO getUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUserDTO dto = JSON.parseObject(string, GithubUserDTO.class);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
