# PROJECT 2

We have completed all the two parts of the project..Below is the file structure of the project.

We choose number of Nodes as N=3 (A,B,C)


**NOTE:** <br>

* Using JAVA RMI, we are trying to implement

## Part1 Berkeley Algorithm Implementation
## NodeA
### Supporting Operations
* Send Message to Node B and NodeC
* Synchronize the All nodes

### Starting NodeA
1.Open Terminal <br>
2.Go to Downloaded Project Directory

Perform the below operation to start the server

```bash
	1.cd cd N-NodeDistributedSystem/src
	2.javac *.java
	3.java NodeA
```
### NodeA Start Output

![Screenshot](NodeA.png)

## NodeB
### Supporting Operations
* Send Message to Node B and NodeC
* Synchronize the All nodes

### Starting NodeB
1.Open Terminal <br>
2.Go to Downloaded Project Directory

Perform the below operation to start the server

```bash
	1.cd cd N-NodeDistributedSystem/src
	2.javac *.java
	3.java NodeB
```
### NodeB Start Output

![Screenshot](NodeB.png)

## NodeC
### Supporting Operations
* Send Message to Node B and NodeC
* Synchronize the All nodes

### Starting NodeC
1.Open Terminal <br>
2.Go to Downloaded Project Directory

Perform the below operation to start the server

```bash
    1.cd cd N-NodeDistributedSystem/src
	2.javac *.java
	3.java NodeC
```
### NodeC Start Output

![Screenshot](NodeC.png)

## Synchronizing the Clocks
1.Go to Node A tab in terminal<br>
2.Choose the option 2

### NodeA Sync Output

![Screenshot](NodeASynchronize.png)

### NodeB Sync Output

![Screenshot](NodeBSynchronize.png)

### NodeC Sync Output

![Screenshot](NodeCSynchronize.png)


## Part2 Vector Implementation
## VectorNodeA
### Supporting Rule
* When a message is sent or received timer counter will be increased by 1
* When Internal event happens timer counter will be increased by 1

### Starting VectorNodeA
1.Open Terminal <br>
2.Go to Downloaded Project Directory

Perform the below operation to start the server

```bash
	1.cd cd N-NodeDistributedSystem/src
	2.javac *.java
	3.java VectorNodeA
```
**NOTE:** The Berkeley servers Should be up while running vector
### VectorNodeA Start Output
Initially we are synching the vector values from the Berkeley
![Screenshot](VectorNodeA.png)

## VectorNodeB
### Supporting Rule
* When a message is sent or received timer counter will be increased by 1
* When Internal event happens timer counter will be increased by 1

### Starting VectorNodeB
1.Open Terminal <br>
2.Go to Downloaded Project Directory

Perform the below operation to start the server

```bash
	1.cd cd N-NodeDistributedSystem/src
	2.javac *.java
	3.java VectorNodeB
```
**NOTE:** The Berkeley servers Should be up while running vector
### VectorNodeB Start Output
Initially we are synching the vector values from the Berkeley
![Screenshot](VectorNodeB.png)

## VectorNodeC
### Supporting Rule
* When a message is sent or received timer counter will be increased by 1
* When Internal event happens timer counter will be increased by 1

### Starting VectorNodeC
1.Open Terminal <br>
2.Go to Downloaded Project Directory

Perform the below operation to start the server

```bash
	1.cd cd N-NodeDistributedSystem/src
	2.javac *.java
	3.java VectorNodeC
```
**NOTE:** The Berkeley servers Should be up while running vector
### VectorNodeC Start Output
Initially we are synching the vector values from the Berkeley
![Screenshot](VectorNodeC.png)

## Sending Message from VectorNodeA to VectorNodeB
1.Go To Terminal of VectorNodeA <br>
2.Enter B

## VectorNodeA Output
![Screenshot](A2B.png)


## Sending Message from VectorNodeB to VectorNodeC
1.Go To Terminal of VectorNodeB <br>
2.Enter C

## VectorNodeB Output
![Screenshot](B2C.png)
