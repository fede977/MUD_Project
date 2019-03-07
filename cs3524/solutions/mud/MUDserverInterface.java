package cs3524.solutions.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MUDserverInterface extends Remote{


    public void makeMUD(String MUDName) throws RemoteException;
    public String welcomeMessage() throws RemoteException;
    public String chooseMUD(String inputMethod) throws RemoteException;
    public String StartLocation() throws RemoteException;
    public void addItem(String location, String item) throws RemoteException;
    public String moveItem(String location, String direction, String item) throws RemoteException;
    public String Items(String location) throws RemoteException;
    public String InfoLocation(String location) throws RemoteException;
    public boolean Collect(String item, String location) throws RemoteException;
    public void addPlayer(String username) throws RemoteException;
    public void removePlayer(String username) throws RemoteException;
    public String OnlinePlayers() throws RemoteException;
    public void deleteItem(String location, String item) throws RemoteException;
    public String Inventory() throws RemoteException;
    public String Attack(String user, String enemy) throws RemoteException;
    public void createEnemy(String enemy, int health, int damage) throws RemoteException;
    public void removeEnemy(String e) throws RemoteException;
    public Integer getEnemyHealth(String enemy) throws RemoteException;
    public Integer getPlayerHealth(String name) throws RemoteException;
}