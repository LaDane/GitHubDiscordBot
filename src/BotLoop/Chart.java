package BotLoop;
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

        System.out.println( red + "," + green + "," + blue);
        System.out.println((red+100>255 ? 255 : red+100) + "," +
                (green+100>255 ? 255 : green+100) + "," +
                (blue+100>255 ? 255 : blue+100));
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
                "        borderColor: 'rgb(" + (red+100>255 ? 255 : red+100) + "," +
                                               (green+100>255 ? 255 : green+100) + "," +
                                               (blue+100>255 ? 255 : blue+100) + ")'," +
                "        borderWidth: 2," +
                "        data: ["+day1Commits+", "+day2Commits+", "+day3Commits+", "+day4Commits+", "+day5Commits+", "+day6Commits+", "+day7Commits+"]," +
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
//        System.out.println("Bar chart: " + chart.getShortUrl());
    }
    public static String createStackedBarChart(int day1LinesAdded, int day1LinesRemoved, int day2LinesAdded, int day2LinesRemoved,
                                              int day3LinesAdded, int day3LinesRemoved, int day4LinesAdded, int day4LinesRemoved,
                                              int day5LinesAdded, int day5LinesRemoved, int day6LinesAdded, int day6LinesRemoved,
                                              int day7LinesAdded, int day7LinesRemoved){
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
                "        data: ["+day1LinesAdded+", "+day2LinesAdded+", "+day3LinesAdded+", "+day4LinesAdded+", "+day5LinesAdded+", "+day6LinesAdded+", "+day7LinesAdded+"]," +
                "      }," +
                "      {" +
                "        label: 'Lines removed'," +
                "        backgroundColor: 'rgb(255, 99, 132)'," +
                "          data: ["+day1LinesRemoved+", "+day2LinesRemoved+", "+day3LinesRemoved+", "+day4LinesRemoved+", "+day5LinesRemoved+", "+day6LinesRemoved+", "+day7LinesRemoved+"]," +
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
                "        data: ["+day1Commits+", "+day2Commits+", "+day3Commits+", "+day4Commits+", "+day5Commits+", "+day6Commits+", "+day7Commits+"]," +
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


    private static void createRadarChart(int linesAdded,int lines2Added,int lines3Added,int lines4Added,int lines5Added,int lines6Added,int lines7Added,
                                         int linesRemoved, int lines2Removed, int lines3Removed, int lines4Removed, int lines5Removed, int lines6Removed, int lines7Removed,
                                         int totalCommits, int total2Commits, int total3Commits, int total4Commits, int total5Commits, int total6Commits, int total7Commits,
                                         int pointsAcquired, int points2Acquired, int points3Acquired, int points4Acquired, int points5Acquired, int points6Acquired, int points7Acquired,
                                         int botActiveMessages, int bot2ActiveMessages, int bot3ActiveMessages, int bot4ActiveMessages, int bot5ActiveMessages, int bot6ActiveMessages, int bot7ActiveMessages) {
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
                "          " + linesAdded + ",\n" +
                "          " + lines2Added + ",\n" +
                "          " + lines3Added + ",\n" +
                "          " + lines4Added + ",\n" +
                "          " + lines5Added + ",\n" +
                "          " + lines6Added + ",\n" +
                "          " + lines7Added + "\n" +
                "        ],\n" +
                "        \"label\": \"Lines added\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(255, 159, 64, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(255, 159, 64)\",\n" +
                "        \"data\": [\n" +
                "          " + linesRemoved + ",\n" +
                "          " + lines2Removed + ",\n" +
                "          " + lines3Removed + ",\n" +
                "          " + lines4Removed + ",\n" +
                "          " + lines5Removed + ",\n" +
                "         " + lines6Removed + ",\n" +
                "         " + lines7Removed + "\n" +
                "        ],\n" +
                "        \"label\": \"Lines removed\",\n" +
                "        \"fill\": \"-1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(255, 205, 86, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(255, 205, 86)\",\n" +
                "        \"data\": [\n" +
                "          " + totalCommits + ",\n" +
                "          " + total2Commits + ",\n" +
                "          " + total3Commits + ",\n" +
                "          " + total4Commits + ",\n" +
                "          " + total5Commits + ",\n" +
                "          " + total6Commits + ",\n" +
                "          " + total7Commits + "\n" +
                "        ],\n" +
                "        \"label\": \"Total commits\",\n" +
                "        \"fill\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(75, 192, 192, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(75, 192, 192)\",\n" +
                "        \"data\": [\n" +
                "         " + pointsAcquired + ",\n" +
                "          " + points2Acquired + ",\n" +
                "          " + points3Acquired + ",\n" +
                "          " + points4Acquired + ",\n" +
                "          " + points5Acquired + ",\n" +
                "          " + points6Acquired + ",\n" +
                "          " + points7Acquired + "\n" +
                "        ],\n" +
                "        \"label\": \"Points acquired\",\n" +
                "        \"fill\": false\n" +
                "      },\n" +
                "      {\n" +
                "        \"backgroundColor\": \"rgba(54, 162, 235, 0.5)\",\n" +
                "        \"borderColor\": \"rgb(54, 162, 235)\",\n" +
                "        \"data\": [\n" +
                "          " + botActiveMessages + ",\n" +
                "          " + bot2ActiveMessages + ",\n" +
                "          " + bot3ActiveMessages + ",\n" +
                "         " + bot4ActiveMessages + ",\n" +
                "          " + bot5ActiveMessages + ",\n" +
                "          " + bot6ActiveMessages + ",\n" +
                "          " + bot7ActiveMessages + "\n" +
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

    }
       // return chart.getUrl();


    }
