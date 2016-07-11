package lab1.tools;

/**
 * Created by ericm on 11-Jul-16.
 */
public class MinimumEditDistance {

    public int minDistanceOfWords(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }

    public void minDistance(String textWithErrors, String corpus) {
        textWithErrors = textWithErrors.replace("(","")
                .replace(")","")
                .replace("!","")
                .replace(".","")
                .replace(",","")
                .replace("?","")
                .replace(":","")
                .replace(";","")
                .replace("\"","");

        corpus = corpus.replace("(","")
                .replace(")","")
                .replace("!","")
                .replace(".","")
                .replace(",","")
                .replace("?","")
                .replace(":","")
                .replace(";","")
                .replace("\"","");

        String[] wordsText1 = textWithErrors.split("\\s+");
        String[] wordsText2 = corpus.split("\\s+");

        System.out.println("****************************************");
        System.out.println("Calculando Distancia Minima");

        for(int i=0;i<wordsText1.length;i++){
            System.out.print(wordsText1[i]+": ");
            int min = Integer.MAX_VALUE;
            String word = "";
            for(int j=0;j<wordsText2.length;j++){
                int dist = minDistanceOfWords(wordsText1[i],wordsText2[j]);
                if(dist<min){
                    min = dist;
                    word = wordsText2[j];
                }
            }
            System.out.print("Palavra mais proxima: "+word+" Distancia: "+min);
            System.out.println("");
        }
    }

}
