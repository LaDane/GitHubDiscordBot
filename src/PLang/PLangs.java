package PLang;

import Core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

public class PLangs {
    private ArrayList<PLang> pLangs = new ArrayList<PLang>();

    public PLangs() {}

    public String getColorByLanguage(String language) {
        for (PLang pLang : pLangs) {
            if (pLang.getLang().matches(language)) {
                return pLang.getColor();
            }
        }
        Random rand = new Random();
        int nextInt = rand.nextInt(0xffffff + 1);
        String colorCode = String.format("#%06x", nextInt);
        return colorCode;
    }

    public void deserializePLangsSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/PLangs.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.pLangs = gson.fromJson(reader, PLangs.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
