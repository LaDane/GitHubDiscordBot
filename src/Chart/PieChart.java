package Chart;

import Core.Config;
import Member.Member;
import Member.RepoStats;
import io.quickchart.QuickChart;

import java.awt.*;
import java.util.ArrayList;

public class PieChart {
    public static String pieChart() {
        ArrayList<RepoStats> totalRepoStats = new ArrayList<>();

        // Fill totalRepoStats with
        for (Member member : Config.members.getMembers()) {
            for (RepoStats repoStats : member.getMemberRepoStats()) {
                String repoLanguage = repoStats.getRepoLanguage();
                int repoLanguageAmount = repoStats.getRepoLanguageAmount();

                boolean languageExistsInTotalRepoStats = false;
                for (RepoStats rs : totalRepoStats) {
                    if (rs.getRepoLanguage().matches(repoLanguage)) {
                        rs.setRepoLanguageAmount(rs.getRepoLanguageAmount() + repoLanguageAmount);
                        languageExistsInTotalRepoStats = true;
                    }
                }
                if (!languageExistsInTotalRepoStats) {
                    RepoStats newRepoStats = new RepoStats(repoLanguage, repoLanguageAmount);
                    totalRepoStats.add(newRepoStats);
                }
            }
        }

        String[] languages = new String[totalRepoStats.size()];
        int[] languageAmount = new int[totalRepoStats.size()];
        String[] languageHEX = new String[totalRepoStats.size()];
        for (int i = 0; i < totalRepoStats.size(); i++) {
            languages[i] = totalRepoStats.get(i).getRepoLanguage();
            languageAmount[i] = totalRepoStats.get(i).getRepoLanguageAmount();
            languageHEX[i] = Config.pLangs.getColorByLanguage(languages[i]);
//            System.out.println("Language: "+ languages[i] +"\ttAmount: "+ languageAmount[i] +"\t\tHEX: "+ languageHEX[i]);
        }
        if (languages.length == 0)
            return createPieChart(new String[]{"No data"}, new int[]{1}, new String[]{"#0099ff"});

        return createPieChart(languages, languageAmount, languageHEX);
    }


    public static String createPieChart(String[] languages, int[] languageAmount, String[] languageHEX) {
        StringBuilder labels = new StringBuilder();
        for (String l : languages) {
            labels.append("'").append(l).append("'").append(",");
        }
        String labelsString = labels.substring(0, labels.length()-1);

        StringBuilder data = new StringBuilder();
        for (int i : languageAmount) {
            data.append(""+i).append(",");
        }
        String dataString = data.substring(0, data.length()-1);

        StringBuilder color = new StringBuilder();
        for (String c : languageHEX) {
            color.append("'rgb(")
                    .append(Color.decode(c).getRed())
                    .append(", ")
                    .append(Color.decode(c).getGreen())
                    .append(", ")
                    .append(Color.decode(c).getBlue())
                    .append(")',");
        }
        String colorString = color.toString();

        QuickChart chart = new QuickChart();
        chart.setWidth(600);
        chart.setHeight(400);
        chart.setConfig("{" +
                "  type: 'pie'," +
                "  data: {" +
                "    datasets: [" +
                "      {" +
//                "        data: [84, 28, 57, 19, 97]," +
                "        data: ["+ dataString +"]," +

                "        backgroundColor: [" +
//                "          'rgb(255, 99, 132)'," +
                             colorString +
                "        ]," +
                "      }," +
                "    ]," +
//                "    labels: ['Red', 'Orange', 'Yellow', 'Green', 'Blue']," +
                "    labels: ["+ labelsString +"]," +
                "    " +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Number of times programming languages have been used in members repositories: '," +
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
                "  " +
                "}"
        );
        return chart.getShortUrl();
    }
}
