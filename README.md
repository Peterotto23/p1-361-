****************
* P1/Deterministic Finite Automata
* CS 361
* 2.20.2026
* Michael Murphy, Peter Krahn
**************** 


OVERVIEW:


 This program implements a simulator class for DFA structures. 
 Importing this class allows the creation of states and their transitions, and is cabable of determining if some input will be accepted by the DFA.




INCLUDED FILES:


 List the files required for the project with a brief
 explanation of why each is included.


 e.g.
 * DFA.java - source file
 * DFAState.java - source file
 * DFAInterface.java - interface file
 * FAInterface.java - interface file
 * State.java - abstract class file
 * DFATest.java - unit testing file
 * README - this file




COMPILING AND RUNNING:


 Give the command for compiling the program, the command
 for running the program, and any usage instructions the
 user needs.
 
 These are command-line instructions for a system like onyx.
 They have nothing to do with Eclipse or any other IDE. They
 must be specific - assume the user has Java installed, but
 has no idea how to compile or run a Java program from the
 command-line.
 
 e.g.
 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac Class1.java


 Run the compiled class file with the command:
 $ java Class1

How to compile and run on powershell: 
javac -d bin p1Files\fa\*.java p1Files\fa\dfa\*.java
Compiles our core .class files.
javac -d bin -cp "bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" p1Files\test\dfa\DFATest.java
Compiles our DFATest.class
java -cp "bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore test.dfa.DFATest
Runs our tests you should see an output that says: OK (19 tests). 
The bin directory will be included with empty folders so, that you may properly create these .class files.


 Console output will give the results after the program finishes.




PROGRAM DESIGN AND IMPORTANT CONCEPTS:

DFA.java keeps a Linked Hash Map of all DFAStates contained within the DFA. DFAStates works by keeping a Hash Map of pairs of transitions and symbols, where when given some symbol, it will return what the next state should be. DFAStates is completely agnostic to the requirements of the DFA itself. For example, all error checking to see if adding a transition would be illegal is checked within DFA.java, not in DFAStates. DFAStates only checks to see if a given transition could possibly result in an NFA.
The main event of the DFA class is in the accepts() function. This function splits the input up into an array of characters, and then calls for the next state over each character in this array. If at any point the "next state" is null (AKA there was no mapping for this transition), then the DFA "halts" or just returns false. If we've made it to the end of the string, and the DFA is in an accept/final state, then we return true.


TESTING:


 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?




DISCUSSION:
 
The only significant problems encountered during development were in writing DFAState.java. It didn't take too long to figure out how we wanted to store the transitions in terms of the data structure, but rather in how we would use that data structure. K, V pairs were initially set up that the key was the result state, and the value was some symbol. This caused a headache of logic writing before realizing that storing them backwords cut down LOC by an embarrassing amount.

 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 
 

SOURCES:
 Javadoc site for documention on Set and Map interface implentations.

 Rubber Ducky Method

 AI USAGE:
  Github Copilot used for class javadoc summaries.

----------------------------------------------------------------------------


All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.