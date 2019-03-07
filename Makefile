CS3524_HOME = "$(HOME)/Desktop/assessment"

RM_FLAGS = "-f"

rmimud: 
	javac cs3524/solutions/mud/Edge.java; \
	javac cs3524/solutions/mud/MUD.java; \
	javac cs3524/solutions/mud/MUDclient.java; \
	javac cs3524/solutions/mud/MUDserver.java; \
	javac cs3524/solutions/mud/MUDserverImpl.java; \
	javac cs3524/solutions/mud/MUDserverInterface.java
	javac cs3524/solutions/mud/Vertex.java; \

rmimudclean:
	cd cs3524/solutions/mud; \
	rm ${RM_FLAGS} *.class *~;
	cd ${CS3524_HOME}