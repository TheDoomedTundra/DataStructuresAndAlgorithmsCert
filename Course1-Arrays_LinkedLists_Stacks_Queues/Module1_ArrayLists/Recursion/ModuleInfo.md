### <p style="font-size:40px"> Recursion </p>

**[1. Recursion](#1-recursion)**



<br>

# 1. Recursion
Correct recursive method must have the following attributes:
1. Base case or termination condition that stops all branches eventually
2. Recursive case that calls to itself
3. Each recursive call must move towards the base case in some way

## 1.1. Identifying the Subproblem
Recursion is good when you can naturally break up a problem into subproblems.

## 1.2. Tips for Writing Recursive Functions
1. Write the base case first
2. Avoid overlapping base cases if possible (arms-length recursion)
3. Clearly separate code into base and recursive cases

Always have three questions in mind when writing al algorithm:
1. Is the algorithm correct?
2. What is the efficiency of the algorithm?
3. Is the efficiency of the analysis tight, and can we do better?