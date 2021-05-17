package Chart;

import Core.Config;
import io.quickchart.QuickChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StackedBarChart {
    public static String stackedBarChart() {
        int[] dayLinesAdded = new int[7];
        int[] dayLinesRemoved = new int[7];
        String[] last7dates = new String[7];

        for (int i = 0; i < last7dates.length; i++) {
            last7dates[i] = LocalDate.now().minusDays(i).toString();
            int[] logData = Config.botLogs.getBotLogCommitsLines(LocalDate.parse(last7dates[i]));
            dayLinesAdded[i] = logData[1];
            dayLinesRemoved[i] = -logData[2];
            last7dates[i] = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("MMM dd"));
            }
        return createStackedBarChart(dayLinesAdded, dayLinesRemoved, last7dates);
    }

    public static String createStackedBarChart(int[] dayLinesAdded,int[] dayLinesRemoved, String[] last7dates){
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'bar'," +
                "  data: {" +
                "    labels: ['"+last7dates[6]+"', '"+last7dates[5]+"', '"+last7dates[4]+"', '"+last7dates[3]+"', '"+last7dates[2]+"', '"+last7dates[1]+"', '"+last7dates[0]+"']," +
                "    datasets: [" +
                "           {" +
                "        label: 'Lines added'," +
                "        backgroundColor: 'rgb(31, 181, 31)'," +
                "        data: ["+dayLinesAdded[6]+", "+dayLinesAdded[5]+", "+dayLinesAdded[4]+", "+dayLinesAdded[3]+", "+dayLinesAdded[2]+", "+dayLinesAdded[1]+", "+dayLinesAdded[0]+"]," +
                "      }," +
                "      {" +
                "        label: 'Lines removed'," +
                "        backgroundColor: 'rgb(184, 37, 35)'," +
                "          data: ["+dayLinesRemoved[6]+", "+dayLinesRemoved[5]+", "+dayLinesRemoved[4]+", "+dayLinesRemoved[3]+", "+dayLinesRemoved[2]+", "+dayLinesRemoved[1]+", "+dayLinesRemoved[0]+"]," +
                "      }," +
                "    ]," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Total amount of lines changed for all members by day: '," +
                "      fontColor: '#ffffff'" +
                "    }," +
                "    legend: {" +
                "      labels: {" +
                "        fontColor: '#ffffff'," +
                "      }" +
                "    }," +
                "    scales: {" +
                "      xAxes: [" +
                "        {" +
                "          stacked: true," +
                "           ticks: {" +
                "               fontColor: '#fff'" +
                "           }" +
                "        }," +
                "      ]," +
                "      yAxes: [" +
                "        {" +
                "          stacked: true," +
                "           ticks: {" +
                "               beginAtZero: true," +
                "               fontColor: '#fff'" +
                "           }," +
                "           gridLines: {" +
                "               color: '#aaa'" +
                "           }" +
                "        }," +
                "      ]," +
                "    }," +
                "  }," +
                "}"
        );
        return chart.getShortUrl();
    }
}
