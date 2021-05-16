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
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  \"type\": \"doughnut\"," +
                "  \"data\": {" +
                "    \"datasets\": [{" +
                "      \"label\": \"Messages\"," +
                "      data: [" + botMessages + ", " + userMessages + "]," +
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
                "            \"text\": \"\\nBot active messages\"," +
                "            \"color\": \"#aaa\"," +
                "            \"font\": {" +
                "              \"size\": \"23\"" +
                "            }," +
                "          }," +
                "          {" +
                "            \"text\": \"\\n90%\"," +
                "            \"font\": {" +
                "              \"size\": \"35\"" +
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
