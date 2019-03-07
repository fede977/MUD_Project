package cs3524.solutions.mud;

import java.rmi.*;
import java.util.*;

import cs3524.solutions.mud.MUD;

public class MUDserverImpl implements MUDserverInterface{

    private MUD mud;
    private int limit = 5;
    private int count = 0;

    private Map<String, MUD> Ms = new HashMap<String, MUD>();

    public MUDserverImpl() throws RemoteException{}

    public void makeMUD(String MUDName) throws RemoteException{

        try{
            if(count == limit){
                System.out.println("Toom many active MUDs");
            }else{
                Ms.put(MUDName, new MUD("mymud.edg","mymud.msg","mymud.thg"));
                System.out.println("MUD "+ MUDName+" created");
                count += 1;
            }
        }catch(Exception e){
            System.err.println("error creating mud: " + e.getMessage());
        }

    }

    public String welcomeMessage() throws RemoteException{
        
        String out = ("\nAvailable MUDS: \n");

        for(Map.Entry<String, MUD> entry : Ms.entrySet()){

            String key = entry.getKey();
            out += (key + "\n");
        }

        out += ("\nSelect MUD > ");

        return out;
    }

    public String chooseMUD(String userInput) throws RemoteException{

        if(Ms.containsKey(userInput)){
            mud = Ms.get(userInput);
            return "Welcome to: " + userInput + "\n" + "Enter Username: ";
        }else{
            return "False";
        }
    }

    public String StartLocation() throws RemoteException{
        return mud.startLocation();
    }

    public void addItem(String location, String item){
        mud.addThing(location, item);
    }

    public String moveItem(String location, String direction, String item){
        return mud.moveThing(location, direction, item);
    }

    public String Items(String location){
        return mud.Items(location);
    }

    public void deleteItem(String location, String item){
        mud.delThing(location,item);
    }

    public String InfoLocation(String location){
        return mud.locationInfo(location);
    }

    public boolean Collect(String item, String location){
        return mud.collect(item,location);
    }

    public void addPlayer(String username){
        mud.addUser(username);
    }

    public void removePlayer(String username){
        mud.removeUser(username);
    }

    public String OnlinePlayers() {
        return mud.OnlineUsers();
    }

    public String Inventory(){
        return mud.Inventory();
    }

    public void createEnemy(String enemy, int health, int damage){
        mud.createEnemy(enemy, health, damage);
    }

    public void removeEnemy(String enemy){
        mud.removeEnemy(enemy);
    }

    public String Attack(String user, String enemy){
        return mud.Attack(user, enemy);
    }

    public Integer getEnemyHealth(String enemy){
        return mud.getEnemyHealth(enemy);
    }

    public Integer getPlayerHealth(String name){
        return mud.getPlayerHealth(name);
    }
}
