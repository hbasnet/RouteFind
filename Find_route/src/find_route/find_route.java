//Himal Basnet
//1001659428
package find_route;

import java.util.*;
import java.io.*;

public class find_route {

    public static void Adding_successor(cityNode origin_city, ArrayList<inputFile> route_exists) {
        ArrayList<cityNode> successors = new ArrayList<cityNode>();//arraylist to add children nodes

        for (int i = 0; i < route_exists.size(); i++) {
            if (route_exists.get(i).get_start_city().equals(origin_city.get_city_name()))//if city in left side then add all on its right
            {
                cityNode success = new cityNode(route_exists.get(i).get_destination_city(), origin_city.get_node_level() + 1,
                        origin_city.get_cumulative_cost() + route_exists.get(i).get_path_distance(), origin_city);
                successors.add(success);
            } else if (route_exists.get(i).get_destination_city().equals(origin_city.get_city_name())) {
                cityNode success = new cityNode(route_exists.get(i).get_start_city(), origin_city.get_node_level() + 1,
                        origin_city.get_cumulative_cost() + route_exists.get(i).get_path_distance(), origin_city);
                successors.add(success);
            }
        }
        origin_city.set_successor(successors);
    }

    public static void print_all_route(cityNode goal_node, ArrayList<inputFile> route_exists) {
        System.out.println("distance: " + goal_node.get_cumulative_cost() + " km");
        System.out.println("route:");
        float distance_between = 0;
        while (goal_node.get_parent() != null) {
            for (int i = 0; i < route_exists.size(); i++) {
                if ((goal_node.get_city_name()).equals(route_exists.get(i).get_start_city()) || (goal_node.get_city_name()).equals(route_exists.get(i).get_destination_city())) //if the goal_node is saved as the first city in the file or second city in the file
                {
                    if ((goal_node.get_parent().get_city_name()).equals(route_exists.get(i).get_destination_city()) || (goal_node.get_parent().get_city_name()).equals(route_exists.get(i).get_start_city())) {
                        distance_between = route_exists.get(i).get_path_distance();
                    }
                }
            }
            System.out.println(goal_node.get_parent().get_city_name() + " to " + goal_node.get_city_name()
                    + " , " + distance_between + " km");
            goal_node = goal_node.get_parent();
        }
    }

    public static void main(String[] args) {
        //first thing is to get all the info from command i.e filename start city and goal city
        //save them in some variables
        String file = args[0]; //read the file name from user at runtime
        String initial_destination = args[1]; //current city
        String final_destination = args[2]; //final city 

        int nodes_expanded = 0, nodes_generated = 0, max_nodes_in_memory = 0, fringe_size = 0;

        cityNode start_city = new cityNode(initial_destination, 0, 0, null);

        ArrayList<inputFile> route_exists = new ArrayList<inputFile>();//array list to store the inputFile type object read from file

        try {
            Scanner sc = new Scanner(new File(file));
            while (sc.hasNext()) {
                String str = sc.nextLine();
                if (str.equals("END OF INPUT")) {
                    break;
                }

                String[] splitted = str.split(" ");
                inputFile info = new inputFile();
                info.start_city = splitted[0];
                info.destination_city = splitted[1];
                info.path_distance = Float.parseFloat(splitted[2]);

                route_exists.add(info); //inputFile type object is store in route_exists array
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PriorityQueue<cityNode> fringe = new PriorityQueue<cityNode>(1000, new Comparator<cityNode>() //only dequeue the lowest cost
        {
            @Override
            public int compare(cityNode node1, cityNode node2) {
                if (node1.get_cumulative_cost() > node2.get_cumulative_cost()) {
                    return 1;
                }
                if (node1.get_cumulative_cost() == node1.get_cumulative_cost()) {
                    return 0;
                }
                return -1;
            }
        }
        );

        fringe.add(start_city);

        ArrayList<String> visited_city_nodes = new ArrayList<String>(); //only name of city as state which are already expanded

        while (!fringe.isEmpty()) {

            cityNode dequeued_city = fringe.poll();
            nodes_expanded++;

            if (dequeued_city.get_city_name().equals(final_destination)) {
                System.out.println("nodes expanded: " + (nodes_expanded+1));//added 1 for goal state
                System.out.println("nodes generated: " + nodes_generated);
                System.out.println("max nodes in memory: " + (max_nodes_in_memory-1));
                print_all_route(dequeued_city, route_exists);
                break;
            }

            if (!visited_city_nodes.contains(dequeued_city.get_city_name())) {
                visited_city_nodes.add(dequeued_city.get_city_name());

                Adding_successor(dequeued_city, route_exists);

                for (cityNode priority_city_successor : dequeued_city.get_successor()) {
                    fringe.add(priority_city_successor);
                    fringe_size = fringe.size();
                    if (max_nodes_in_memory < fringe_size) {
                        max_nodes_in_memory = fringe_size;
                    }
                    nodes_generated++;
                }
            }

            if (fringe.isEmpty()) {
                System.out.println("nodes expanded: " + nodes_expanded);
                System.out.println("nodes generated: " + nodes_generated);
                System.out.println("max nodes in memory: " + max_nodes_in_memory);
                System.out.println("distance : infinty");//if route node doesn't exist
                System.out.println("route: \n none");
            }
        }

    }

//class to make object of distance between two city from input file
    private static class inputFile {

        String start_city;
        String destination_city;
        float path_distance;

        String get_start_city() {
            return start_city;
        }

        String get_destination_city() {
            return destination_city;
        }

        float get_path_distance() {
            return path_distance;
        }
    }

    //class to make object of node/city
    private static class cityNode {

        String city_name;
        int node_level;
        float cumulative_cost;
        cityNode parent;
        ArrayList<cityNode> successor = new ArrayList<cityNode>();

        public cityNode(String city_name, int node_level, float cumulative_cost, cityNode parent) {
            this.city_name = city_name;
            this.node_level = node_level;
            this.cumulative_cost = cumulative_cost;
            this.parent = parent;
        }

        public cityNode(String city_name, int node_level, float cumulative_cost, cityNode parent, ArrayList<cityNode> successor) {
            this.city_name = city_name;
            this.node_level = node_level;
            this.cumulative_cost = cumulative_cost;
            this.parent = parent;
            this.successor = successor;
        }

        void set_successor(ArrayList<cityNode> successor) {
            this.successor = successor;
        }

        String get_city_name() {
            return city_name;
        }

        int get_node_level() {
            return node_level;
        }

        float get_cumulative_cost() {
            return cumulative_cost;
        }

        cityNode get_parent() {
            return parent;
        }

        ArrayList<cityNode> get_successor() {
            return this.successor;
        }

    }

}
