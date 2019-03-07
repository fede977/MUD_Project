package cs3524.solutions.mud;

import java.rmi.*;
import java.net.InetAddress;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import cs3524.solutions.mud.MUDserverInterface;

import java.io.*;


public class MUDclient{

    static MUDserverInterface server;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static String location;
    private static String enemyLocation;
    private static String username;
    private static String MUDName;
    private static String enemy = "";
    public static void main(String args[]) throws Exception{
        if(args.length < 2){
            System.err.println("Specify <host> <portName>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try{

            String url =  "rmi://"+host+":" + port + "/MUDserver";
            server = (MUDserverInterface)Naming.lookup(url);

            System.out.print("Client connected!");
            StartClient();
        }catch(java.io.IOException e){
            System.err.println("Input error");
            System.err.println(e.getMessage());
        }
    }

    static void StartClient() throws Exception{
        System.out.println(server.welcomeMessage());

        MUDName = input.readLine();

        try{
            if(server.chooseMUD(MUDName).equals("False")){
                System.out.println("Enter existing MUD");
                StartClient();
            }

            System.out.println(server.chooseMUD(MUDName));

            username = input.readLine();

            server.addPlayer(username);
            server.createEnemy("Goblin", 3, 6);
            enemy = "Goblin";
            PlayMUD();
        }catch(Exception e){
            System.err.println("Server Down");
        }
    }

    static void PlayMUD() throws Exception{

        Scanner scan = new Scanner(System.in);
        String movement = "";

        location = server.StartLocation();
        List<String> mv = new ArrayList<String>();
        mv.add("north");
        mv.add("south");
        mv.add("east");
        mv.add("west");
        Random rand = new Random();

        int i = rand.nextInt(3);
        enemyLocation = server.StartLocation();
        server.addItem(enemyLocation, enemy);
        server.addItem(location, username);
        System.out.println("MUDGAME:");
        System.out.println("Starting Location: " + location);
        System.out.println("Type \"info\" for help\n");

        while(true){

            System.out.print("\nenter command > ");
            movement = scan.nextLine();
            if(movement.equalsIgnoreCase("north") || movement.equalsIgnoreCase("south") || movement.equalsIgnoreCase("east") || movement.equalsIgnoreCase("west")){
                location = server.moveItem(location, movement.toLowerCase(), username);
                enemyLocation = server.moveItem(enemyLocation, mv.get(i), enemy);

                System.out.println("moved " + movement + " Location is: " + location);
            }

            if(movement.equalsIgnoreCase("info")){
                System.out.println("type the four cardinal points (north, east, etc..) to move up, down, left and right respectively \n");
                System.out.println("type \"location\" to have information about your current location\n");
                System.out.println("type \"collect\" to take the items at your location \n");
                System.out.println("type \"inventory\" to check the items in your inventory \n");
                System.out.println("type \"attack\" to attack an enemy \n");
                System.out.println("type \"createMUD\" to create a new MUD\n");
                System.out.println("type \"changeMUD\" to go to a different MUD\n");
                System.out.println("type \"players\" to see a list of online players \n");
                System.out.println("type \"exit\" to quit the game");

            }

            if(movement.equalsIgnoreCase("inventory")){
                System.out.println("Object in inventory: ");
                System.out.println(server.Inventory());
            }

            if(movement.equalsIgnoreCase("location")){
                System.out.println(server.InfoLocation(location));
            }

            if(movement.equalsIgnoreCase("collect")){
                System.out.println(server.Items(location));
                System.out.print("Choose the item you want to collect > ");
                String item = scan.nextLine();
                if(item.equals(enemy)){
                    System.out.println("Cannot collect an enemy");
                }else{
                    
                    if(server.Collect(item, location)){
                        System.out.println("Item: " + item + " succesfully collected");
                    }else{
                        System.out.println(item + " cannot be collected check spelling");
                    }

                }
            }


            if(movement.equalsIgnoreCase("attack")){
                System.out.print("\nChoose the enemy to attack(case sensitive) > ");
                String e = scan.nextLine();
                if(server.Items(location).contains(e)){
                    System.out.println(server.Attack(username, e));
                    if(server.getEnemyHealth(e).equals(0)){
                        server.deleteItem(location, e);
                        server.removeEnemy(e);
                    }else if(server.getPlayerHealth(username).equals(0)){
                        System.out.println("Game Over!");
                        server.removePlayer(username);
                        server.deleteItem(location, username);
                        scan.close();
                        System.exit(0);
                    }
                }else{
                    System.out.println("No such enemy exists");
                }
            }

            if(movement.equalsIgnoreCase("createMUD")){
                System.out.print("Enter a new name > ");
                MUDName = scan.nextLine();
                server.makeMUD(MUDName);
            }

            if(movement.equalsIgnoreCase("changeMUD")){
                server.deleteItem(location, username);
                server.removePlayer(username);
                StartClient();
            }

            if(movement.equalsIgnoreCase("players")){
                System.out.println("Users online:");
                System.out.println(server.OnlinePlayers());
            }

            if(movement.equalsIgnoreCase("exit")){
                System.out.println("Closing...");
                server.deleteItem(location, username);
                server.removePlayer(username);
                scan.close();
                System.exit(0);
            }

        }
    }
}

