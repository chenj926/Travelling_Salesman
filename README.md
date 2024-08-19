# Traveling Salesman Problem (TSP) Solver Project

## Overview

This project implements various greedy algorithms to solve the **Traveling Salesman Problem (TSP)**, which aims to find the shortest possible route that visits each city exactly once and returns to the origin city. The input consists of latitude and longitude coordinates for a set of cities, and the program provides an approximate solution using different algorithms.

The project is written in Java and includes several solvers and utilities for graph manipulation, input handling, and solution comparison.

## Algorithms Implemented

1. **Nearest Neighbor (NN)**: 
   - Selects the closest unvisited city as the next step.
   
2. **Nearest Neighbor with Fixed Length (NN-FL)**:
   - A modified nearest neighbor that focuses on a fixed length path or limited lookahead.
   
3. **Nearest Insertion (NI)**:
   - Adds the nearest city to the existing tour at a location where it will cause the least increase in the total distance.

These algorithms provide different trade-offs in terms of computation time, accuracy of the route, and success rate. The results are compared and analyzed based on cost (total path length), computation time, and success rate.

## Java Files

### 1. `Pro5_chenj926.java`
This is the **main driver** of the program. It is responsible for:
   - Running the different solvers (NN, NN-FL, and NI).
   - Reading city coordinates from user input or predefined data.
   - Displaying results such as total path cost, computation time, and success rate for each algorithm.
   - Determining and printing the winner based on a comparison of all three algorithms.

### 2. `Graph.java`
This file handles the **graph structure** of the cities:
   - Stores the distance matrix between cities using their latitude and longitude.
   - Provides methods to calculate the Euclidean distance between two cities.
   - Acts as the core data structure that all solvers use to represent the problem.

### 3. `TSPSolver.java`
The **base class** for all TSP solvers. It manages:
   - The solution path, cost, and computation time.
   - Tracks whether solutions have been found.
   - Acts as an interface for different algorithms, and stores the route and elapsed time for each solver.

### 4. `NNSolver.java`
Implements the **Nearest Neighbor (NN)** algorithm:
   - Starts at a random city and iteratively selects the closest unvisited city to form the tour.
   - Returns a solution with relatively quick computation time, but may not always provide the shortest path.

### 5. `NNFLSolver.java`
Implements the **Nearest Neighbor with Fixed Length (NN-FL)** algorithm:
   - Similar to NN, but it limits the number of cities considered during each step, improving the accuracy over the standard NN.

### 6. `NISolver.java`
Implements the **Nearest Insertion (NI)** algorithm:
   - Starts with a partial tour and iteratively inserts the nearest unvisited city at the location where it causes the least increase in the overall path length.
   - This tends to produce better solutions than NN or NN-FL but takes more time to compute.

### 7. `Node.java`
Defines the structure of each **node (city)** in the TSP:
   - Stores latitude, longitude, and city labels.
   - Provides basic utility methods related to the position and distance calculations between cities.

### 8. `Display.java`
This class is responsible for **output formatting** and visualization of the TSP route:
   - It prints the graphical representation of the route, the distances between cities, and the final results.
   - It helps in displaying the solution to the user in a user-friendly manner.

### 9. `getInput.java`
Handles **input reading** and parsing:
   - Reads input either from a file or through user input, which includes the coordinates (latitude and longitude) of cities.
   - Parses the input and formats it for use by the graph and solvers.

## How the Algorithm Works

1. **Input Handling**: 
   - The program takes latitude and longitude coordinates of cities as input. These are fed into the `Graph` class to construct the distance matrix.

2. **Solver Execution**:
   - Based on the user's choice, the program invokes one of the three solvers (NN, NN-FL, or NI). Each solver computes an approximate solution to the TSP by finding the shortest possible route using their respective greedy approach.

3. **Result Comparison**:
   - After running all solvers, the program compares the results in terms of total cost, computation time, and success rate.
   - The algorithm that performs best in all three metrics is declared the winner.

4. **Output Display**:
   - The final solution, including the route and metrics, is displayed to the user. The `Display` class helps in presenting the results clearly.

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/chenj926/Travelling_Salesman.git
