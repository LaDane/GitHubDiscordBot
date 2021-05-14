package Web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.chilkatsoft.*;

public class NewToken {
    public static String renew() {
        // This example requires the Chilkat API to have been previously unlocked.
        // See Global Unlock Sample for sample code.

        CkOAuth2 oauth2 = new CkOAuth2();
        boolean success;

        // This should be the port in the localhost callback URL for your app.
        // The callback URL would look like "http://localhost:3017/" if the port number is 3017.
        oauth2.put_ListenPort(8000);

        oauth2.put_AuthorizationEndpoint("https://github.com/login/oauth/authorize");
        oauth2.put_TokenEndpoint("https://github.com/login/oauth/access_token");

        // Replace these with actual values.
        oauth2.put_ClientId("e85e11514c1704815110");
        oauth2.put_ClientSecret("4faa984adf01f991333df3b45ef0d6eb58bea1c3");
        oauth2.put_CodeChallenge(true);
        oauth2.put_CodeChallengeMethod("S256");

        // Begin the OAuth2 three-legged flow.  This returns a URL that should be loaded in a browser.
        String url = oauth2.startAuth();
        if (oauth2.get_LastMethodSuccess() != true) {
            System.out.println(oauth2.lastErrorText());
            return oauth2.lastErrorText();
        }

        // At this point, your application should load the URL in a browser.
        // For example,
        // in C#:  System.Diagnostics.Process.Start(url);
        // in Java: Desktop.getDesktop().browse(new URI(url));
        // in VBScript: Set wsh=WScript.CreateObject("WScript.Shell")
        //              wsh.Run url
        // in Xojo: ShowURL(url)  (see http://docs.xojo.com/index.php/ShowURL)
        // in Dataflex: Runprogram Background "c:\Program Files\Internet Explorer\iexplore.exe" sUrl
        // The GitHub account owner would interactively accept or deny the authorization request.

        // Add the code to load the url in a web browser here...
        // Add the code to load the url in a web browser here...
        // Add the code to load the url in a web browser here...

        // Now wait for the authorization.
        // We'll wait for a max of 30 seconds.
        int numMsWaited = 0;
        while ((numMsWaited < 30000) && (oauth2.get_AuthFlowState() < 3)) {
            oauth2.SleepMs(100);
            numMsWaited = numMsWaited+100;
        }

        // If there was no response from the browser within 30 seconds, then
        // the AuthFlowState will be equal to 1 or 2.
        // 1: Waiting for Redirect. The OAuth2 background thread is waiting to receive the redirect HTTP request from the browser.
        // 2: Waiting for Final Response. The OAuth2 background thread is waiting for the final access token response.
        // In that case, cancel the background task started in the call to StartAuth.
        if (oauth2.get_AuthFlowState() < 3) {
            oauth2.Cancel();
            System.out.println("No response from the browser!");
            return "No response from the browser!";
        }

        // Check the AuthFlowState to see if authorization was granted, denied, or if some error occurred
        // The possible AuthFlowState values are:
        // 3: Completed with Success. The OAuth2 flow has completed, the background thread exited, and the successful JSON response is available in AccessTokenResponse property.
        // 4: Completed with Access Denied. The OAuth2 flow has completed, the background thread exited, and the error JSON is available in AccessTokenResponse property.
        // 5: Failed Prior to Completion. The OAuth2 flow failed to complete, the background thread exited, and the error information is available in the FailureInfo property.
        if (oauth2.get_AuthFlowState() == 5) {
            System.out.println("OAuth2 failed to complete.");
            System.out.println(oauth2.failureInfo());
            return oauth2.failureInfo();
        }

        if (oauth2.get_AuthFlowState() == 4) {
            System.out.println("OAuth2 authorization was denied.");
            System.out.println(oauth2.accessTokenResponse());
            return oauth2.accessTokenResponse();
        }

        if (oauth2.get_AuthFlowState() != 3) {
            System.out.println("Unexpected AuthFlowState:" + oauth2.get_AuthFlowState());
            return "Unexpected AuthFlowState:" + oauth2.get_AuthFlowState();
        }

        System.out.println("OAuth2 authorization granted!");
        System.out.println("Access Token = " + oauth2.accessToken());
        return oauth2.accessToken();
    }
}
