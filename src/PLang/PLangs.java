package PLang;

import PLang.*;
import java.util.ArrayList;
import java.util.Random;

public class PLangs {
    private ArrayList<PLang> pLangs = new ArrayList<PLang>();

    public PLangs() {}


    public String getColorByLanguage(String language) {
//        for (PLang pLang : pLangs) {
//            if (pLang.getLang().matches(language)) {
//                return pLang.getColor();
//            }
//        }
        Random rand = new Random();
        int randomNumber = rand.nextInt(0x10) + 0x10;
        return Integer.toHexString(randomNumber);
    }
}
