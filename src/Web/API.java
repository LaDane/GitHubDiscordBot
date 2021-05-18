package Web;

import Core.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class API {

    public static String request(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", Config.bot.getApiToken()).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
    }

    public static String request(String url, String header) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", header).header("Authorization", Config.bot.getApiToken()).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
    }
}
