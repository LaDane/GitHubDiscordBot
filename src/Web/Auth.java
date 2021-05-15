package Web;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import Core.Config;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Auth {

    private static final String NETWORK_NAME = "GitHub";
    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

    private Auth() {
/*
    public static void authRequest(String url) {
        final String clientId = Config.app.getClientID();
        final String clientSecret = Config.app.getClientSecret();
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .callback("call back here")
                .build(GitHubApi.instance());

        final String authorizationUrl = service.getAuthorizationUrl(secretState);
        OAuthRequest request = new OAuthRequest(Verb.GET, authorizationUrl);
        System.out.println(request);



        // final String code = authorizationUrl.substring(authorizationUrl.indexOf("code=") + 20);         // trying to get code from redirect url
        // System.out.println("authCode = "+ code);

        OAuth2AccessToken accessToken = null;
        try {accessToken = service.getAccessToken(code);}
        catch (Exception e) {e.printStackTrace();}

        final OAuthRequest request = new OAuthRequest(Verb.GET, url);
        if (accessToken != null) {
            service.signRequest(accessToken, request);

            try (Response response = service.execute(request)) {
                System.out.println("Request found:\n");
                System.out.println(response.getCode());
                System.out.println(response.getBody());
            } catch (Exception e) {e.printStackTrace();}
        }
 */
    }



/*
    @SuppressWarnings("PMD.SystemPrintln")
    public static void main(String... args) throws IOException, InterruptedException, ExecutionException {
        // Replace these with your client id and secret

        final String clientId = "id here";
        final String clientSecret = "secret here";
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .callback("callback here")
                .build(GitHubApi.instance());
        final Scanner in = new Scanner(System.in, "UTF-8");

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl(secretState);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        final String code = in.nextLine();
        System.out.println();

        System.out.println("And paste the state from server here. We have set 'secretState'='" + secretState + "'.");
        System.out.print(">>");
        final String value = in.nextLine();
        if (secretState.equals(value)) {
            System.out.println("State value does match!");
        } else {
            System.out.println("Ooops, state value does not match!");
            System.out.println("Expected = " + secretState);
            System.out.println("Got      = " + value);
            System.out.println();
        }

        System.out.println("Trading the Authorization Code for an Access Token...");
        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        try (Response response = service.execute(request)) {
            System.out.println("Got it! Lets see what we found...");
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());
        }
        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");
    }
 */
}