package Chart;

import Core.Config;
import io.quickchart.QuickChart;

import java.time.LocalDate;

public class GaugeChart {
    public static String gaugeChart() {
        int[] botLogMessages = Config.botLogs.getBotLogMessages(LocalDate.now());
        return createGaugeChart(botLogMessages[1], botLogMessages[0]);
    }

    public static String createGaugeChart(int botMessages, int userMessages) {
        if (userMessages == 0)
            userMessages = 1;
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  \"type\": \"doughnut\"," +
                "  \"data\": {" +
                "    \"datasets\": [{" +
                "      \"label\": \"Messages\"," +
                "      data: [" + botMessages + ", " + (userMessages - botMessages) + "]," +
                "      }," +
                "      {" +
                "      \"backgroundColor\": [" +
                "        \"rgba(255, 0, 0,1)\"," +
                "        \"rgba(0, 0, 0, 0.3)\"" +
                "      ]," +
                "      \"textcolor\":[\"#000555\",\"#555555\"]," +
                "      \"borderWidth\": 0," +
                "    }] " +
                "  }," +
                "  \"options\": {" +
                "    \"rotation\": Math.PI," +
                "    \"circumference\": Math.PI," +
                "    \"cutoutPercentage\": 75," +
                "    \"plugins\": {" +
                "      \"datalabels\": { \"display\": false }," +
                "      \"doughnutlabel\": {" +
                "        \"labels\": [" +
                "          {" +
                "            \"text\": \"\\n" + ((botMessages*100)/userMessages) + "% of "+ userMessages +" messages have been sent by the bot\"," +
                "            \"color\": \"#aaa\"," +
                "            \"font\": {" +
                "              \"size\": \"14\"" +
                "            }," +
                "          }," +
                "        ]" +
                "      }" +
                "    }" +
                "  }" +
                "}");
        return chart.getShortUrl();
    }
}
