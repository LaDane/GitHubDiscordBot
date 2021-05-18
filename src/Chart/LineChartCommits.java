package Chart;

import Core.Config;
import Member.Member;
import io.quickchart.QuickChart;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LineChartCommits {
    public static String lineChartCommits(){
        String title = "Total commits for all members by day:";
        String color = "#0099ff";
        int[] dayCommits = new int[7];
        String[] last7dates = new String[7];

        for (int i = 0; i < last7dates.length; i++) {
            last7dates[i] = LocalDate.now().minusDays(i).toString();
            int[] logData = Config.botLogs.getBotLogCommitsLines(LocalDate.parse(last7dates[i]));
            dayCommits[i] = logData[0];
            last7dates[i] = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("MMM dd"));
        }
        return createLineChartCommits(dayCommits,last7dates, title, color);
    }

    public static String lineChartCommits(Member member) {
        String title = "Total commits for "+ member.getMemberGithubName() +" by day: ";
        String color = member.getMemberColor();
        int[] dayCommits = new int[7];
        String[] last7dates = new String[7];

        for (int i = 0; i < last7dates.length; i++) {
            last7dates[i] = LocalDate.now().minusDays(i).toString();
            int[] logData = member.getMemberLogCommitsLines(LocalDate.parse(last7dates[i]));
            dayCommits[i] = logData[0];
            last7dates[i] = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("MMM dd"));
        }
        return createLineChartCommits(dayCommits,last7dates, title, color);
    }

    public static String createLineChartCommits(int[] dayCommits, String[] last7dates, String title, String color) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'line'," +
                "  data: {" +
                "    labels: ['"+last7dates[6]+"', '"+last7dates[5]+"', '"+last7dates[4]+"', '"+last7dates[3]+"', '"+last7dates[2]+"', '"+last7dates[1]+"', '"+last7dates[0]+"']," +
                "    datasets: [" +
                "      {" +
                "        label: 'Commits'," +
//                "        backgroundColor: '#0099ff'," +
                "        backgroundColor: '"+ color +"'," +
//                "        borderColor: '#0099ff'," +
                "        borderColor: '"+ color +"'," +
                "        data: ["+dayCommits[6]+", "+dayCommits[5]+", "+dayCommits[4]+", "+dayCommits[3]+", "+dayCommits[2]+", "+dayCommits[1]+", "+dayCommits[0]+"]," +
                "        fill: false," +
                "      }," +
                "    ]," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: '"+ title +"'," +
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
                "               fontColor: '#fff'," +
                "               precision: 0" +
                "           }" +
                "        }," +
                "      ]," +
                "      yAxes: [" +
                "        {" +
                "          stacked: true," +
                "           ticks: {" +
                "               beginAtZero: true," +
                "               fontColor: '#fff'," +
                "               precision: 0" +
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

