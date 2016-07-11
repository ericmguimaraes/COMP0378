package lab1.tools;

import java.util.*;

/**
 * Created by Ariel on 10-Jul-16.
 * Possivel corretor gramatical baseado em vocabulário não genérico
 */

public class SpellChecker {

    // nós para algoritmo A*
    static class Node {
        int finalCost = Integer.MAX_VALUE; //G+H
        int i, j;
        Node parent = null;

        Node(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }

    static PriorityQueue<Node> openNodes;
    static boolean closed[][];
    static Node [][] grid;
    private String bestString = "yes!";

    public String SpellChecker (String target, List<String> vocabulary) {
        HashMap<Long, String> hashMap = new HashMap<Long, String>();
        for (String s2 : vocabulary) {
            init(target.length(), s2.length());
            hashMap.put(Long.valueOf(AStar(target, s2)), s2); // Fazer esse cast é mais bonito do que ficar criando objeto Integer para o hashMap?
        }

        // Ordena hashMap;
        //bestString = hashMap.get(menor);
        return bestString;
    }

    private void init (int l1, int l2) {
        closed = new boolean[l1][l2];
        grid = new Node[l1][l2];

        openNodes = new PriorityQueue<>((Object o1, Object o2) -> {
            Node c1 = (Node)o1;
            Node c2 = (Node)o2;

            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
        });

        for (int i = 0; i < l1; i++)
            for (int j = 0; j < l2; j++)
                grid[i][j] = new Node(i, j);
    }

    private int cost (int[][] D, int i, int j, int aux) {
        if ((i == 0) && (j != 0))
            return j;
        else if ((j == 0) && (i != 0))
            return i;
        else {
            return Math.min(D[i-1][j] +1, Math.min(D[i][j-1] + 1, D[i-1][j-1] + aux));
        }

    }

    static void checkAndUpdateCost(Node current, Node temp, int cost){
        if(temp == null || closed[temp.i][temp.j])return;
        int t_final_cost = cost;

        boolean inOpen = openNodes.contains(temp);
        if(!inOpen || t_final_cost<temp.finalCost){
            temp.finalCost = t_final_cost;
            temp.parent = current;
            if(!inOpen)openNodes.add(temp);
        }
    }

    // Algoritmo A* encontra a menor distância entre as strings s1 e s2
    private int AStar (String s1, String s2) {

        /**
         * Método não implementado. Precisa corrigir o código do A*
         */

        /*
        openNodes.add(grid[0][0]);
        Node current;
        int D[][] = new int[s1.length()][s2.length()], distance = 0;
        D[0][0] = 0;

        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                current = openNodes.poll();
                if (current == null) break;
                closed[current.i][current.j] = true;
                int aux = (s1.charAt(i) == s2.charAt(j)) ? 0 : 2;
                Node temp;

                if ((i > 0) && (j > 0)) {
                    temp = grid[current.i][current.j];
                    D[i][j] = cost(D, i, j, aux);
                    checkAndUpdateCost(current, temp, D[i][j]);
                }
            }
        }

        Node temp = grid[s1.length()-1][s2.length()-1];
        while (temp.parent != null) {
            distance += temp.finalCost;
            temp = temp.parent;
        }

        System.out.println(distance);
        return distance;
        */
    }

}