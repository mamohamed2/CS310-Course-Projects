/*
 Mohamed Mohamed
 CS 310-2
 821344570
 11/19/19
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Graph {



    int max = 0;
    String heaviestWeightofAnEdgeSource = "";
    String heaviestWeightofAnEdgeDest = "";

    // these strings and the int are used to find the heaviest weight and store
    // the source and dest to a file

    ArrayList<String> selfEdges = new ArrayList<>();
    // used to store the self edges in the graph

    Map<String, Map<String,Integer>> graph = new HashMap<>();


    public boolean addEdge(String source, String dest, int cost) {
        if (max < cost) {
            // since I'm adding an edge to the graph, might as well find the heaviest
            // edge right?
            max = cost;
            heaviestWeightofAnEdgeSource = source;
            heaviestWeightofAnEdgeDest = dest;
        }


        if (source.equals(dest)) {
            //checks to see if source and dest are the same
            selfEdges.add(source);
        }

        if (!graph.containsKey(source) || !graph.containsKey(dest)) {
            // if graph already contains either of the source or destination file, then we
            // can not do anything
            return false;
        }

        graph.get(source).put(dest,cost);
        // adds the two vertices and the cost to the graph

        return true;
    }

    public boolean removeEdges(String source, String destination) {
        if (!contains(source) && !graph.containsKey(destination)) {
            return false;
        }

        return true;
    }

    public void addVertex(String name) {
        // method used to add the vertex to thee graph
        if (graph.containsKey(name)) {
            return;
        } else {
            graph.put(name,new HashMap<String,Integer>());
        }


    }

    public boolean removeVertex(String name) {
        // removes the vertex from the graph
        if (contains(name) == false) {
            return false;
        } else {
            graph.remove(name);
        }
        return true;
    }

    public boolean contains(String name) {
        //vertex method

        if (graph.containsKey(name)) {
            return true;
        }
        return false;
    }

    public boolean contains(String source, String dest) {

        // checks to see if the edge actually exists within the map

        if (graph.containsKey(source)) {
            for (Map<String,Integer> map : graph.values()) {
                if (map.containsKey(dest)) {
                    // if the source and destination exists, we return true, if not, then false
                    return true;
                }
            }

        }
        return false;
    }

    public int numberOfVertices() {

        // readjust the number of vertices
        return graph.keySet().size();
    }

    public String heaviestEdge() {
        // returns the heaviest strings and the max value that was gotten from
        // the addEdge function
        return heaviestWeightofAnEdgeSource + ", " + heaviestWeightofAnEdgeDest + ", " + max;
    }


    public ArrayList<String> zeroOutboundEdges() {
        ArrayList<String> zeroOutboundEdges = new ArrayList<>();

        // Logic: go through the graph and see if there are any vertices that aren't
        // any that are in the destination part of the map

        for (String s1 : graph.keySet()) {
            boolean hasNoOutbound = true;
            // boolean used to see if it doesn't have any outbound edges
            for (String s2: graph.keySet()) {
                HashMap<String,Integer> map = (HashMap<String, Integer>) graph.get(s1);
                if (map.containsKey(s2)) {
                    // if there are any inbound vertices, then we break this for-each loop
                    hasNoOutbound = false;
                    break;
                }
            }
            if (hasNoOutbound) {
                // if there are any inbound vertices, then we break this for-each loop
                zeroOutboundEdges.add(s1);
            }
        }

        return zeroOutboundEdges;
    }

    public ArrayList<String> zeroInboundEdges() {
        // Same logic applied to the outbound edges here, however they are in
        // reverse

        ArrayList<String> zeroInboundEdges = new ArrayList<>();

        for (String s1 : graph.keySet()) {
            boolean hasNoInbound = true;
            // boolean used to see if it doesn't have any inbound edges
            for (String s2: graph.keySet()) {
                HashMap<String,Integer> map = (HashMap<String, Integer>) graph.get(s2);
                if (map.containsKey(s1)) {
                    // if there are any outbound vertices, then we break this for-each loop
                    hasNoInbound = false;
                    break;
                }
            }

            if (hasNoInbound) {
                // if this boolean remains true, then it is added within the ArrayList
                zeroInboundEdges.add(s1);
            }
        }
        return zeroInboundEdges;
    }

}
