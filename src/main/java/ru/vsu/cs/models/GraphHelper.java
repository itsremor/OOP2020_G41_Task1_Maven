package ru.vsu.cs.models;

public class GraphHelper {
    boolean[][] matrix = new boolean[33][33];

    public void addEdge(int i, int j){
        matrix[i][j] = true;
        matrix[j][i] = true;
    }

    public int[] getArrayOfEdges(int index){
        int counter = 0;
        for (int i = 0; i < 33; i++) {
            if (matrix[index][i]) counter++;
        }
        int[] edges = new int[counter];
        counter = 0;

        for (int i = 0; i < 33; i++) {
            if(matrix[index][i]){
                edges[counter] = i;
                counter++;
            }
        }

        return edges;
    }
}
