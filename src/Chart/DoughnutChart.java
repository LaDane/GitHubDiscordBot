package Chart;

import Core.Config;
import io.quickchart.QuickChart;

import java.time.LocalDate;

public class DoughnutChart {
    public static String doughnutChart() {
        int[] botLogPoints = Config.botLogs.getBotLogPoints(LocalDate.now());
        return createDoughnutChart(botLogPoints[0], botLogPoints[1], botLogPoints[2]);
    }

    public static String createDoughnutChart(int pointsGiven, int pointsWon, int pointsLost) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'doughnut'," +
                "  data: {" +
                "    datasets: [" +
                "      {" +
                "        data: [" + pointsGiven + ", " + pointsWon + " , " + pointsLost + "]," +
                "      }," +
                "      {" +
                "        backgroundColor: [" +
                "          'rgb(255, 99, 132)'," +
                "          'rgb(255,255,0)'," +
                "          'rgb(54, 162, 235)'," +
                "        ]," +
                "        label: 'Points'," +
                "      }," +
                "    ]," +
                "    labels: ['Points Acquired','Points Won', 'Points Lost']," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Chart.js Doughnut Chart'," +
                "    }," +
                "  }," +
                "}");

        return chart.getShortUrl();
    }
}
