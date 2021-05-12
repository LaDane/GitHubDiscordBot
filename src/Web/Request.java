package Web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.URLConnection;

public class Request {
    public static JsonObject request(String url, String header) {

        InputStream inputStream;
        Gson gson = new Gson();
        try {
            URLConnection connection = new URL(url).openConnection();
            if (header != null) connection.setRequestProperty("Accept", header);
            InputStream response = connection.getInputStream();

            String res = convertInputStreamToString(response);
            // Convert JSON to JsonElement
            JsonElement jsonElement = gson.fromJson(res, JsonElement.class);
            // Convert JsonElement to JsonObject (.get() possible)
            return jsonElement.getAsJsonObject();

        } catch (IOException e) {
            //e.printStackTrace();
        }
        return null;
    }

    private static String convertInputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int length;
        while ((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
}
