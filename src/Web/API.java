package Web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {

    public static String request(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
    }

    public static String request(String url, String header) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", header).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
    }


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
