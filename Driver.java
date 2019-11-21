/*
 Mohamed Mohamed
 CS 310-2
 821344570
 11/19/19
 */


import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Driver {

    public static void main (String[] args) {




        Graph graph = new Graph();

        try {
            if (args.length != 1) {
                System.err.println("This program only needs one argument, try again");
            }

            FileInputStream inputFile = new FileInputStream(args[0]);
            FileWriter outFile = new FileWriter ("return.txt");
            PrintWriter pw = new PrintWriter(outFile);


            Scanner scan = new Scanner(inputFile);


            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] vertex = line.split(",");

                if (vertex.length == 3) {
                    // if there are three values after the split
                    if (!graph.contains(vertex[0]) ) {
                        graph.addVertex(vertex[0]);
                    }
                    if (!graph.contains(vertex[1]) ) {
                        graph.addVertex(vertex[1]);
                    }

                    graph.addEdge(vertex[0],vertex[1],Integer.parseInt(vertex[2]));
                }

                if (vertex.length == 1) {
                    // if there's only one input
                    if (graph.contains(vertex[0])) {
                        continue;
                    } else {
                        graph.addVertex(vertex[0]);
                    }

                }
            }


            try {
                pw.println("Number of vertices in the graph: "  + graph.numberOfVertices());
                pw.println("The edge with the heaviest weight is: " + graph.heaviestEdge());
                pw.println("The vertices with the self edges are: ");

                if (graph.selfEdges.size() == 0) {
                    pw.println("Sorry, there are no vertices with self edges");
                } else {
                    for (int i = 0; i <  graph.selfEdges.size(); i++) {
                        pw.println(graph.selfEdges.get(i));
                    }
                }

                pw.println("The vertices with zero outbound edges are:");

                if (graph.zeroOutboundEdges().size() == 0) {
                    pw.println("Sorry, there are no edges that meet this requirement");
                } else {
                    for (int i = 0; i < graph.zeroOutboundEdges().size(); i++) {
                        pw.println(graph.zeroOutboundEdges().get(i));
                    }
                }


                pw.println("The vertices with zero inbound edges are: ");
                if (graph.zeroInboundEdges().size() == 0) {
                    pw.println("Sorry, there are no edges that meet this requirement");
                } else {
                    for (int i = 0; i < graph.zeroInboundEdges().size(); i++) {
                        pw.println(graph.zeroInboundEdges().get(i));
                    }
                }

            } finally {
                pw.flush();
            }





        } catch (FileNotFoundException e) {
            System.err.println("Make sure to use the entire file path for each of the arguments");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
