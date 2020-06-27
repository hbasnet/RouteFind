Himal Basnet (1001659428)
CSE 4308-003
Assignment 1 (Task 1)

Programming Language: Java

From the given routes in the text file an optimal path from source city to destination city is calculated. Graph search using Uniform Cost Search is implemented to get the optimal value for the given uninformed search problem.

There are three classes in this program one of them is public which is find_route. It contains a main function where file is read, and fringe is made using priority queue. And also, other two functions Adding_successor which create list of successors for the dequeued parent node from fringe and print_all_route which print the path from start to end.

Other two classes are inputFile which make an object of each line of the input file and cityNode which make object of each city having name, node level, cumulative cost, parent and successors.

To run on Omega:


