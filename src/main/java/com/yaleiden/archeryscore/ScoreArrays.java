package com.yaleiden.archeryscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for handling displayed score values
 *
 * @author Yale Leiden
 */

public class ScoreArrays {

    private static final String[] ScoreArray0 = {"0", "5", "10"};
    private static final String[] ScoreArray1 = {"0", "5", "8", "10"};
    private static final String[] ScoreArray2 = {"0", "5", "8", "10", "11"};
    private static final String[] ScoreArray3 = {"0", "5", "8", "10", "12"};
    private static final String[] ScoreArray4 = {"0", "5", "8", "10", "12", "14"};
    private static final String[] ScoreArray5 = {"0", "1", "2", "3", "4", "5"};
    private static final String[] ScoreArray6 = {"0", "3", "4", "5"};
    private static final String[] ScoreArray7 = {"0", "3", "5", "6", "8", "10"};
    private static final String[] ScoreArray8 = {"0", "4", "6", "8", "10", "12", "14", "16", "18", "20"};
    private static final String[] ScoreArray9 = {"0", "4", "8", "10", "14", "16", "20"};
    private static final String[] ScoreArray10 = {"0", "4", "8", "10", "14", "16", "20", "24"};
    private static final String[] ScoreArray11 = {"0", "5", "8", "11", "14", "17", "20"};
    private static final String[] ScoreArray12 = {"0", "10", "11", "12", "14", "15", "16", "18", "19", "20"};
    private static final String[] ScoreArray13 = {"0", "10", "12", "14", "16", "18", "20"};
    private static final String[] ScoreArray14 = {"0", "5", "10", "15", "20"};
    private static final String[] ScoreArray15 = {"-5", "0", "8", "10"};
    private static final String[] ScoreArray16 = {"0", "5", "7", "9", "10", "14", "16"};
    private static final String[] ScoreArray17 = {"-1", "3", "5"};

    private static final String[] ScoreArrayText = {
            "5-10",
            "5-8-10",
            "5-8-10-11",
            "5-8-10-12",
            "5-8-10-12-14",
            "1-2-3-4-5",
            "3-4-5",
            "3-5-6-8-10",
            "4-6-8-10-12-14-16-18-20",
            "4-8-10-14-16-20",
            "4-8-10-14-16-20-24",
            "5-8-11-14-17-20",
            "10-11-12-14-15-16-18-19-20",
            "10-12-14-16-18-20",
            "5-10-15-20",
            "-5-0-8-10",
            "5-7-9-10-14-16",
            "-1-3-5"
    };

    private String[] ScoreArray;
    private String BullScore;
    private String user_scoring;

    /**
     * Method to return user readable selected scoring array
     *
     * @return String Array
     */
    String[] getScoringText() {
        return ScoreArrayText;
    }

    /**
     * Method that returns a selected score array
     *
     * @param us Integer representing spinner item position of selected scoring system
     * @return String Array for score spinner
     */
    String[] getScoring(int us) {
        // get correct score array based on prefs

        if (us == 0) {
            ScoreArray = ScoreArray0;
        }
        if (us == 1) {
            ScoreArray = ScoreArray1;
        }
        if (us == 2) {
            ScoreArray = ScoreArray2;
        }
        if (us == 3) {
            ScoreArray = ScoreArray3;
        }
        if (us == 4) {
            ScoreArray = ScoreArray4;
        }
        if (us == 5) {
            ScoreArray = ScoreArray5;
        }
        if (us == 6) {
            ScoreArray = ScoreArray6;
        }
        if (us == 7) {
            ScoreArray = ScoreArray7;
        }
        if (us == 8) {
            ScoreArray = ScoreArray8;
        }
        if (us == 9) {
            ScoreArray = ScoreArray9;
        }
        if (us == 10) {
            ScoreArray = ScoreArray10;
        }
        if (us == 11) {
            ScoreArray = ScoreArray11;
        }
        if (us == 12) {
            ScoreArray = ScoreArray12;
        }
        if (us == 13) {
            ScoreArray = ScoreArray13;
        }
        if (us == 14) {
            ScoreArray = ScoreArray14;
        }
        if (us == 15) {
            ScoreArray = ScoreArray15;
        }
        if (us == 16) {
            ScoreArray = ScoreArray16;
        }
        if (us == 17) {
            ScoreArray = ScoreArray17;
        }

        ScoreArray = populateScoreArray(ScoreArray);

        return ScoreArray;

    }

    /**
     * Method that returns a selected score array
     *
     * @param us Integer representing spinner item position of selected scoring system
     * @return String Array for score spinner
     */
    String[] getScoringWithoutCustom(int us) {
        // get correct score array based on prefs

        if (us == 0) {
            ScoreArray = ScoreArray0;
        }
        if (us == 1) {
            ScoreArray = ScoreArray1;
        }
        if (us == 2) {
            ScoreArray = ScoreArray2;
        }
        if (us == 3) {
            ScoreArray = ScoreArray3;
        }
        if (us == 4) {
            ScoreArray = ScoreArray4;
        }
        if (us == 5) {
            ScoreArray = ScoreArray5;
        }
        if (us == 6) {
            ScoreArray = ScoreArray6;
        }
        if (us == 7) {
            ScoreArray = ScoreArray7;
        }
        if (us == 8) {
            ScoreArray = ScoreArray8;
        }
        if (us == 9) {
            ScoreArray = ScoreArray9;
        }
        if (us == 10) {
            ScoreArray = ScoreArray10;
        }
        if (us == 11) {
            ScoreArray = ScoreArray11;
        }
        if (us == 12) {
            ScoreArray = ScoreArray12;
        }
        if (us == 13) {
            ScoreArray = ScoreArray13;
        }
        if (us == 14) {
            ScoreArray = ScoreArray14;
        }
        if (us == 15) {
            ScoreArray = ScoreArray15;
        }
        if (us == 17) {
            ScoreArray = ScoreArray17;
        }


        //ScoreArray = populateScoreArray(ScoreArray);

        return ScoreArray;

    }

    /**
     * Method that returns user readable string of selected score array
     *
     * @param int representing spinner item position of selected scoring system
     * @return String returns user readable scoring
     */
    String getScoreText(int us) {

        if (us == 0) {
            user_scoring = "5-10";
        } else if (us == 1) {
            user_scoring = "5-8-10";
        } else if (us == 2) {
            user_scoring = "5-8-10-11";
        } else if (us == 3) {
            user_scoring = "5-8-10-12";
        } else if (us == 4) {
            user_scoring = "5-8-10-12-14";
        } else if (us == 5) {
            user_scoring = "1-2-3-4-5";
        } else if (us == 6) {
            user_scoring = "3-4-5";
        } else if (us == 7) {
            user_scoring = "3-5-6-8-10";
        } else if (us == 8) {
            user_scoring = "4-6-8-10-12-14-16-18-20";
        } else if (us == 9) {
            user_scoring = "4-8-10-14-16-20";
        } else if (us == 10) {
            user_scoring = "4-8-10-14-16-20-24";
        } else if (us == 11) {
            user_scoring = "5-8-11-14-17-20";
        } else if (us == 12) {
            user_scoring = "10-11-12-14-15-16-18-19-20";
        } else if (us == 13) {
            user_scoring = "10-12-14-16-18-20";
        } else if (us == 14) {
            user_scoring = "5-10-15-20";
        } else if (us == 15) {
            user_scoring = "-5-0-8-10";
        } else if (us == 16) {
            user_scoring = "5-7-9-10-14-16";
        }else if (us == 17) {
            user_scoring = "-1-3-5";
        }
        return user_scoring;
    }

    /**
     * Method that returns a string of max value in selected score array
     *
     * @param us Integer representing spinner item position of selected scoring system
     * @return String value of highest score in ScoreArray
     */
    String getBullScore(int us) {
        // get correct score array based on prefs

        if (us == 0) {
            BullScore = "10";
        }
        if (us == 1) {
            BullScore = "10";
        }
        if (us == 2) {
            BullScore = "11";
        }
        if (us == 3) {
            BullScore = "12";
        }
        if (us == 4) {
            BullScore = "14";
        }
        if (us == 5) {
            BullScore = "5";
        }
        if (us == 6) {
            BullScore = "5";
        }
        if (us == 7) {
            BullScore = "10";
        }
        if (us == 8) {
            BullScore = "20";
        }
        if (us == 9) {
            BullScore = "20";
        }
        if (us == 10) {
            BullScore = "24";
        }
        if (us == 11) {
            BullScore = "20";
        }
        if (us == 12) {
            BullScore = "20";
        }
        if (us == 13) {
            BullScore = "20";
        }
        if (us == 14) {
            BullScore = "20";
        }
        if (us == 15) {
            BullScore = "10";
        }
        if (us == 16) {
            BullScore = "16";
        }
        if (us == 17) {
            BullScore = "5";
        }
        return BullScore;

    }

    /**
     * Method that returns index of scoring format
     *
     * @param String user readable scoring system
     * @return int position of user readable String in ScoreArrayText
     */
    int getScoreInt(String userScoring) {
        int scoreInt;

        scoreInt = Arrays.asList(ScoreArrayText).indexOf(userScoring);

        return scoreInt;
    }

    private String[] populateScoreArray(String[] inputArray) {

        int size = inputArray.length;
        String[] returnArray = new String[size + 1];
        List<String> list = new ArrayList<String>();

        list.addAll(Arrays.asList(inputArray).subList(0, size));
        list.add("Custom");

        list.toArray(returnArray);
        return returnArray;
    }

}