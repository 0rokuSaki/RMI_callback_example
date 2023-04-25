package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {
    void makeMove(GameClient stub, int i, int j) throws RemoteException;
}
