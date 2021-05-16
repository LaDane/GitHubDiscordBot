package Chart;

import Core.Config;
import io.quickchart.QuickChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RadarChart {
    public static String radarChart() {
        int[] dayLinesAdded = new int[7];
        int[] dayCommits = new int[7];
        int[] dayLinesRemoved = new int[7];
        String[] last7dates = new String[7];

        for (int i = 0; i < last7dates.length; i++) {
            last7dates[i] = LocalDate.now().minusDays(i).toString();
            int[] logData = Config.botLogs.getBotLogCommitsLines(LocalDate.parse(last7dates[i]));
            dayCommits[i] = logData[0];
            dayLinesAdded[i] = logData[1];
            dayLinesRemoved[i] = logData[2];
            last7dates[i] = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("MMM dd"));
        }
        return createRadarChart(dayLinesAdded, dayLinesRemoved,dayCommits,dayCommits,dayCommits,last7dates);
    }

    public static String createRadarChart(int[] linesAdded, int[] linesRemoved, int[] totalCommits, int[] pointsAcquired, int[] botActiveMessages,String[] last7dates){
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "    labels: ['"+last7dates[6]+"', '"+last7dates[5]+"', '"+last7dates[4]+"', '"+last7dates[3]+"', '"+last7dates[2]+"', '"+last7dates[1]+"', '"+last7dates[0]+"']," +
                "    \"datasets\": [" +
                "      {" +
                "        \"backgroundColor\": \"rgba(255, 99, 132, 0.5)\"," +
                "        \"borderColor\": \"rgb(255, 99, 132)\"," +
                "        \"data\": [" +
                "          "+linesAdded[0]+"," +
                "          "+linesAdded[1]+"," +
                "          "+linesAdded[2]+"," +
                "          "+linesAdded[3]+"," +
                "          "+linesAdded[4]+"," +
                "          "+linesAdded[5]+"," +
                "          "+linesAdded[6]+"" +
                "        ]," +
                "        \"label\": \"Lines added\"" +
                "      }," +
                "      {" +
                "        \"backgroundColor\": \"rgba(255, 159, 64, 0.5)\"," +
                "        \"borderColor\": \"rgb(255, 159, 64)\"," +
                "        \"data\": [" +
                "          "+linesRemoved[0]+"," +
                "          "+linesRemoved[1]+"," +
                "          "+linesRemoved[2]+"," +
                "          "+linesRemoved[3]+"," +
                "          "+linesRemoved[4]+"," +
                "         "+linesRemoved[5]+"," +
                "         "+linesRemoved[6]+"" +
                "        ]," +
                "        \"label\": \"Lines removed\"," +
                "        \"fill\": \"-1\"" +
                "      }," +
                "      {" +
                "        \"backgroundColor\": \"rgba(255, 205, 86, 0.5)\"," +
                "        \"borderColor\": \"rgb(255, 205, 86)\"," +
                "        \"data\": [" +
                "          "+totalCommits[0]+"," +
                "          "+totalCommits[1]+"," +
                "          "+totalCommits[2]+"," +
                "          "+totalCommits[3]+"," +
                "          "+totalCommits[4]+"," +
                "          "+totalCommits[5]+"," +
                "          "+totalCommits[6]+"" +
                "        ]," +
                "        \"label\": \"Total commits\"," +
                "        \"fill\": 1" +
                "      }," +
                "      {" +
                "        \"backgroundColor\": \"rgba(75, 192, 192, 0.5)\"," +
                "        \"borderColor\": \"rgb(75, 192, 192)\"," +
                "        \"data\": [" +
                "         "+pointsAcquired[0]+"," +
                "          "+pointsAcquired[1]+"," +
                "          "+pointsAcquired[2]+"," +
                "          "+pointsAcquired[3]+"," +
                "          "+pointsAcquired[4]+"," +
                "          "+pointsAcquired[5]+"," +
                "          "+pointsAcquired[6]+"" +
                "        ]," +
                "        \"label\": \"Points acquired\"," +
                "        \"fill\": false" +
                "      }," +
                "      {" +
                "        \"backgroundColor\": \"rgba(54, 162, 235, 0.5)\"," +
                "        \"borderColor\": \"rgb(54, 162, 235)\"," +
                "        \"data\": [" +
                "          "+botActiveMessages[0]+"," +
                "          "+botActiveMessages[1]+"," +
                "          "+botActiveMessages[2]+"," +
                "          "+botActiveMessages[3]+"," +
                "          "+botActiveMessages[4]+"," +
                "          "+botActiveMessages[5]+"," +
                "          "+botActiveMessages[6]+"" +
                "        ]," +
                "        \"label\": \"Bot active messages\"," +
                "        \"fill\": \"-1\"" +
                "      }," +
                "    ]" +
                "  }," +
                "  \"options\": {" +
                "    \"maintainAspectRatio\": true," +
                "    \"spanGaps\": false," +
                "    \"elements\": {" +
                "      \"line\": {" +
                "        \"tension\": 0.000001" +
                "      }" +
                "    }," +
                "    \"plugins\": {" +
                "      \"filler\": {" +
                "        \"propagate\": false" +
                "      }," +
                "      \"samples-filler-analyser\": {" +
                "        \"target\": \"chart-analyser\"" +
                "      }" +
                "    }" +
                "  }" +
                "}");
        return chart.getUrl();
    }


}
