package Web;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {

    public static String request(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build(); //.header("Authorization", "Bearer " + "")
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
    }

    public static String request(String url, String header) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", header).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
    }


    //httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", "Your Oauth token");

//    private static String parseGithubMember(String responseBody) {
//        JsonParser parser = new JsonParser();
//        JsonObject memberAPI = (JsonObject) parser.parse(responseBody);
//
//        String memberGithubName = memberAPI.get("login").getAsString();
//        String memberGithubURL = memberAPI.get("url").getAsString();
//        String memberGithubAvatarURL = memberAPI.get("avatar_url").getAsString();
//
//        return memberGithubName +"   "+ memberGithubURL +"   "+ memberGithubAvatarURL;
//    }
}
