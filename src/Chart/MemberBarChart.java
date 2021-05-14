package Chart;

import Member.Member;
import Web.API;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.quickchart.QuickChart;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MemberBarChart {
    public static String memberBarChart(Member member) {
        String githubName = member.getMemberGithubName();
        String request = API.request("https://api.github.com/search/commits?q=author:"+
                        githubName +"&sort=author-date&order=desc&page=1",
                "application/vnd.github.cloak-preview");
        JsonObject api = (JsonObject) new JsonParser().parse(request);

        int[] dayCommits = new int[7];
        String[] last7dates = new String[7];

        for (int i = 0; i < last7dates.length; i++) {
            last7dates[i] = LocalDate.now().minusDays(i).toString();
            for (int j = 0; j < api.get("items").getAsJsonArray().size(); j++) {

                JsonObject object = api.get("items").getAsJsonArray().get(j).getAsJsonObject();
                String dateTime = object.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString();
                String date = dateTime.split("T")[0];

                if (LocalDate.parse(date).isBefore(LocalDate.parse(last7dates[i])))
                    break;
                if (date.equals(last7dates[i])) {
                    dayCommits[i] += 1;
                }
            }
            last7dates[i] = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("MMM dd"));
        }
        return createMemberBarChart(member, dayCommits, last7dates);
    }

    public static String createMemberBarChart(Member member, int[] dayCommits, String[] last7dates) {
        String memberColor = member.getMemberColor();
        int red = Color.decode(memberColor).getRed();
        int green = Color.decode(memberColor).getGreen();
        int blue = Color.decode(memberColor).getBlue();

        //.format(DateTimeFormatter.ofPattern("dd MMM")

        QuickChart chart = new QuickChart();
        chart.setWidth(600);
        chart.setHeight(400);
        chart.setConfig("{" +
                "  type: 'bar'," +
                "  data: {" +
                "    labels: ['"+last7dates[6]+"', '"+last7dates[5]+"', '"+last7dates[4]+"', '"+last7dates[3]+"', '"+last7dates[2]+"', '"+last7dates[1]+"', '"+last7dates[0]+"']," +
                "    datasets: [" +
                "      {" +
                "        label: 'Commits'," +
                "        backgroundColor: 'rgb(" + red + "," + green + "," + blue + ")'," +
                "        borderColor: 'rgb(" + (Math.min(red + 100, 255)) + "," +
                                               (Math.min(green + 100, 255)) + "," +
                                               (Math.min(blue + 100, 255)) + ")'," +
                "        borderWidth: 2," +
                "        data: ["+dayCommits[6]+", "+dayCommits[5]+", "+dayCommits[4]+", "+dayCommits[3]+", "+dayCommits[2]+", "+dayCommits[1]+", "+dayCommits[0]+"]," +
                "      }" +
                "    ]," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Commits for "+member.getMemberGithubName()+" within the last 7 days'," +
                "      fontColor: '#ffffff'" +
                "    }," +
                "    legend: {" +
                "      labels: {" +
                "        fontColor: '#ffffff'," +
                "      }" +
                "    }," +
                "  scales: {" +
                "    xAxes: [{" +
                "      ticks: {" +
                "        fontColor: '#fff'" +
                "      }" +
                "    }]," +
                "    yAxes: [{" +
                "      ticks: {" +
                "        beginAtZero: true," +
                "        fontColor: '#fff'" +
                "      }," +
                "      gridLines: {" +
                "        color: '#aaa'" +
                "      }" +
                "    }]" +
                "  }," +
                "  plugins: {" +
                "      datalabels: {" +
                "        anchor: 'center'," +
                "        align: 'center'," +
                "        color: '#ffffff'," +
                "        font: {" +
                "          weight: 'normal'," +
                "        }," +
                "      }," +
                "    }," +
                "  }," +
                "}"
        );
        return chart.getShortUrl();
    }
}
