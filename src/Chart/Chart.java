package Chart;
import Core.Config;
import Member.Member;
import io.quickchart.QuickChart;

import java.awt.*;

public class Chart {
    public static String createMemberBarChart(Member member, int day1Commits, int day2Commits, int day3Commits, int day4Commits, int day5Commits, int day6Commits, int day7Commits) {
        QuickChart chart = new QuickChart();

        chart.setWidth(600);
        chart.setHeight(400);
        String memberColor = member.getMemberColor();
        int red = Color.decode(memberColor).getRed();
        int green = Color.decode(memberColor).getGreen();
        int blue = Color.decode(memberColor).getBlue();

        System.out.println(red + "," + green + "," + blue);
        System.out.println((Math.min(red + 100, 255)) + "," +
                (Math.min(green + 100, 255)) + "," +
                (Math.min(blue + 100, 255)));

//        System.out.println((red+100>255 ? 255 : red+100) + "," +
//                (green+100>255 ? 255 : green+100) + "," +
//                (blue+100>255 ? 255 : blue+100));

//        return new Color(
//                Integer.valueOf(memberColor.substring( 1, 3 ), 16),
//                Integer.valueOf(memberColor.substring( 3, 5 ), 16),
//                Integer.valueOf(memberColor.substring( 5, 7 ), 16)
//        );

        //rgb((color1+100>255 ? 255 : color1+100), (color2+100>255 ? 255 : color1+100), (color3+100>255 ? 255 : color1+100))

        chart.setConfig("{" +
                "  type: 'bar'," +
                "  data: {" +
                "    labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7']," +
                "    datasets: [" +
                "      {" +
                "        label: 'Commits'," +
                "        backgroundColor: 'rgb(" + red + "," + green + "," + blue + ")'," +
                "        borderColor: 'rgb(" + (Math.min(red + 100, 255)) + "," +
                (Math.min(green + 100, 255)) + "," +
                (Math.min(blue + 100, 255)) + ")'," +
                "        borderWidth: 2," +
                "        data: [" + day1Commits + ", " + day2Commits + ", " + day3Commits + ", " + day4Commits + ", " + day5Commits + ", " + day6Commits + ", " + day7Commits + "]," +
                "      }" +
                "    ]," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Commits for " + member.getMemberGithubName() + " within the last 7 days'," +
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
//        System.out.println("Bar chart: " + chart.getShortUrl());
    }

    public static String createStackedBarChart(int day1LinesAdded, int day1LinesRemoved, int day2LinesAdded, int day2LinesRemoved,
                                               int day3LinesAdded, int day3LinesRemoved, int day4LinesAdded, int day4LinesRemoved,
                                               int day5LinesAdded, int day5LinesRemoved, int day6LinesAdded, int day6LinesRemoved,
                                               int day7LinesAdded, int day7LinesRemoved) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'bar'," +
                "  data: {" +
                "    labels: ['Day 1 ', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7']," +
                "    datasets: [" +
                "           {" +
                "        label: 'Lines added'," +
                "        backgroundColor: 'rgb(0, 102, 204)'," +
                "        data: [" + day1LinesAdded + ", " + day2LinesAdded + ", " + day3LinesAdded + ", " + day4LinesAdded + ", " + day5LinesAdded + ", " + day6LinesAdded + ", " + day7LinesAdded + "]," +
                "      }," +
                "      {" +
                "        label: 'Lines removed'," +
                "        backgroundColor: 'rgb(255, 99, 132)'," +
                "          data: [" + day1LinesRemoved + ", " + day2LinesRemoved + ", " + day3LinesRemoved + ", " + day4LinesRemoved + ", " + day5LinesRemoved + ", " + day6LinesRemoved + ", " + day7LinesRemoved + "]," +
                "      }," +
                "    ]," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "    }," +
                "    scales: {" +
                "      xAxes: [" +
                "        {" +
                "          stacked: true," +
                "        }," +
                "      ]," +
                "      yAxes: [" +
                "        {" +
                "          stacked: true," +
                "        }," +
                "      ]," +
                "    }," +
                "  }," +
                "}"
        );
        return chart.getShortUrl();
    }

    public static String createLineChartCommits(int day1Commits, int day2Commits, int day3Commits, int day4Commits, int day5Commits, int day6Commits, int day7Commits) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'line'," +
                "  data: {" +
                "    labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7']," +
                "    datasets: [" +
                "      {" +
                "        label: 'Commits'," +
                "        backgroundColor: 'rgb(255, 99, 132)'," +
                "        borderColor: 'rgb(255, 99, 132)'," +
                "        data: [" + day1Commits + ", " + day2Commits + ", " + day3Commits + ", " + day4Commits + ", " + day5Commits + ", " + day6Commits + ", " + day7Commits + "]," +
                "        fill: false," +
                "      }," +
                "    ]," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Chart.js Line Chart'," +
                "    }," +
                "  }," +
                "}"
        );
        return chart.getShortUrl();
    }


    /*
    -----------------------------------------------------------------------------------
       int[] linesAdded = {44, 20, 30, 40, 50, 60, 70};
        int[] linesRemoved = {10, 20, 3, 40, 12, 19, 52};
        int[] totalCommits = {3, 24, 6, 7, 4, 34, 2};
        int[] pointsAcquired = {23, 4, 4, 5, 11, 22, 33};
        int[] botActiveMessages = {44, 55, 66, 77, 88, 99, 1};

        createRadarChart(linesAdded, linesRemoved, totalCommits, pointsAcquired, botActiveMessages);
     */

    private static void createRadarChart(int[] linesAdded, int[] linesRemoved, int[] totalCommits, int[] pointsAcquired, int[] botActiveMessages) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{\n" +
                "  \"type\": \"radar\",\n" +
                "  \"data\": {\n" +
                "    \"labels\": [\n" +
                "      \"Monday\",\n" +
                "      \"Tuesday\",\n" +
                "      \"Wednesday\",\n" +
                "      \"Thursday\",\n" +
                "      \"Friday\",\n" +
                "      \"Saturday\",\n" +
                "      \"Sunday\"\n" +
                "    ],\n" +
                "    \"datasets\": [\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(255, 99, 132, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(255, 99, 132)\",\n" +
                "        \"data\": [\n" +
                "          " + linesAdded[0] + ",\n" +
                "          " + linesAdded[1] + ",\n" +
                "          " + linesAdded[2] + ",\n" +
                "          " + linesAdded[3] + ",\n" +
                "          " + linesAdded[4] + ",\n" +
                "          " + linesAdded[5] + ",\n" +
                "          " + linesAdded[6] + "\n" +
                "        ],\n" +
                "        \"label\": \"Lines added\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(255, 159, 64, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(255, 159, 64)\",\n" +
                "        \"data\": [\n" +
                "          " + linesRemoved[0] + ",\n" +
                "          " + linesRemoved[1] + ",\n" +
                "          " + linesRemoved[2] + ",\n" +
                "          " + linesRemoved[3] + ",\n" +
                "          " + linesRemoved[4] + ",\n" +
                "         " + linesRemoved[5] + ",\n" +
                "         " + linesRemoved[6] + "\n" +
                "        ],\n" +
                "        \"label\": \"Lines removed\",\n" +
                "        \"fill\": \"-1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(255, 205, 86, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(255, 205, 86)\",\n" +
                "        \"data\": [\n" +
                "          " + totalCommits[0] + ",\n" +
                "          " + totalCommits[1] + ",\n" +
                "          " + totalCommits[2] + ",\n" +
                "          " + totalCommits[3] + ",\n" +
                "          " + totalCommits[4] + ",\n" +
                "          " + totalCommits[5] + ",\n" +
                "          " + totalCommits[6] + "\n" +
                "        ],\n" +
                "        \"label\": \"Total commits\",\n" +
                "        \"fill\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(75, 192, 192, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(75, 192, 192)\",\n" +
                "        \"data\": [\n" +
                "         " + pointsAcquired[0] + ",\n" +
                "          " + pointsAcquired[1] + ",\n" +
                "          " + pointsAcquired[2] + ",\n" +
                "          " + pointsAcquired[3] + ",\n" +
                "          " + pointsAcquired[4] + ",\n" +
                "          " + pointsAcquired[5] + ",\n" +
                "          " + pointsAcquired[6] + "\n" +
                "        ],\n" +
                "        \"label\": \"Points acquired\",\n" +
                "        \"fill\": false\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(54, 162, 235, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(54, 162, 235)\",\n" +
                "        \"data\": [\n" +
                "          " + botActiveMessages[0] + ",\n" +
                "          " + botActiveMessages[1] + ",\n" +
                "          " + botActiveMessages[2] + ",\n" +
                "          " + botActiveMessages[3] + ",\n" +
                "          " + botActiveMessages[4] + ",\n" +
                "          " + botActiveMessages[5] + ",\n" +
                "          " + botActiveMessages[6] + "\n" +
                "        ],\n" +
                "        \"label\": \"Bot active messages\",\n" +
                "        \"fill\": \"-1\"\n" +
                "      },\n" +
                "    ]\n" +
                "  },\n" +
                "  \"options\": {\n" +
                "    \"maintainAspectRatio\": true,\n" +
                "    \"spanGaps\": false,\n" +
                "    \"elements\": {\n" +
                "      \"line\": {\n" +
                "        \"tension\": 0.000001\n" +
                "      }\n" +
                "    },\n" +
                "    \"plugins\": {\n" +
                "      \"filler\": {\n" +
                "        \"propagate\": false\n" +
                "      },\n" +
                "      \"samples-filler-analyser\": {\n" +
                "        \"target\": \"chart-analyser\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        // Print the chart image URL
        System.out.println(chart.getUrl());

    }

    private static void createDoughnutChart(int pointsAcquired, int pointsFlipped, int pointsLost) {
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setConfig("{" +
                "  type: 'doughnut'," +
                "  data: {" +
                "    datasets: [" +
                "      {" +
                "        data: [" + pointsAcquired + ", " + pointsFlipped + " , " + pointsLost + "]," +
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
                "    labels: ['Points Acquired','Points Flipped', 'Points Lost']," +
                "  }," +
                "  options: {" +
                "    title: {" +
                "      display: true," +
                "      text: 'Chart.js Doughnut Chart'," +
                "    }," +
                "  }," +
                "}");

        // Print the chart image URL
        System.out.println(chart.getUrl());

    }

    public static void createGaugeChart(int botMessages, int userMessages) {
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


    }
}
