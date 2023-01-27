# Divar Java

A social marketplace where users can buy and sell new and secondhand stuff.
This platform facilitates **consumer-to-consumer** and **business-to-consumer** sales with focusing on advertising.
Which have sections to devoted real estate, vehicles, goods, community service, Industrial equipment, items wanted, jobs. by posting any placard you wish as many as you want. 

This is the final project of the Advanced Programming course of the fall semester of 2022 at SBU presented by **Dr.Vahidi**, and created by **Amirhossein Bayat** and **Hojat Ghasemi**.

![Query image](/pics/query.PNG)
###  Pure Java serverside + Cli client.
- Uses pure java with no dependencies and libraries included.
- Client and server can function with just 5MBs of heap memory.
- A not boring Cli client with colors and beautiful UI
<br><br>



## Install and run server process.
![Server image](/pics/server.PNG)
Navigate to **ServerSide** folder, compile the project using:
```sh
javac -d bin -sourcepath src/main/java src/main/java/org/finalproject/server/ServerMain.java
```
Navigate to the newly created **bin** folder and Run the Server process with
```sh
 java org.finalproject.server.ServerMain
```

## Install and run client process
![Client image](/pics/signup.PNG)
Open a new terminal, navigate to the **ClientSide** folder. compile the java code with:
```sh
javac -d bin -sourcepath src/main/java src/main/java/org/finalproject/client/ClientMain.java
```
Navigate to the newly created **bin** folder and Run the client using
```sh
 java org.finalproject.client.ClientMain
```

To limit memory usage, use -Xmx option followed by the amount of ram, for example 10m for 10MB:
```sh
 java -Xmx10m org.finalproject.client.ClientMain
```

To debug memory usage use -memory option, this will print memory usage every 5 sec.
```sh
 java org.finalproject.client.ClientMain -memory
```


## Create a placard:
to create a placard, after login/signup select option "Create placard" and fill in the fields like below:
![Placard creation image](/pics/placard.PNG)

Enjoy!
