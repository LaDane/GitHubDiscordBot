package Chart;

import Core.Config;
import io.quickchart.QuickChart;
import java.time.LocalDate;

public class DoughnutChart {
    public static String doughnutChart() {
        int[] botLogPoints = Config.botLogs.getBotLogPoints(LocalDate.now());
        return createDoughnutChart(botLogPoints[0], botLogPoints[1], botLogPoints[2], botLogPoints[3]);
    }

    public static String createDoughnutChart(int pointsGiven, int pointsWon, int pointsLost, int pointsSpent) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'doughnut'," +
                "  data: {" +
                "    datasets: [" +
                "      {" +
                "        data: [" + pointsGiven + ", " + pointsWon + " , " + pointsLost + " , " + pointsSpent + "]," +
                "      }," +
                "      {" +
                "        backgroundColor: [" +
                "          'rgb(255, 99, 132)'," +
                "          'rgb(255,255,0)'," +
                "          'rgb(54, 162, 235)'," +
                "          'rgb(60, 132, 210)'," +
                "        ]," +
                "        label: 'Points'," +
                "      }," +
                "    ]," +
                "    labels: ['Points gained through commits', 'Points gained through flips', 'Points lost through flips', 'Points spent in the shop']," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Point changes today: '," +
                "      fontColor: '#ffffff'" +
                "    }," +
                "    legend: {" +
                "      labels: {" +
                "        fontColor: '#ffffff'," +
                "      }" +
                "    }," +
                "   plugins: {" +
                "       datalabels: {" +
                "       color: '#fff'"+
                "       }"+
                "   }"+
                "  }," +
                "}");

        return chart.getShortUrl();
    }
}