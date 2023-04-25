package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameClient extends Remote {
    void updateGameBoard(int[][] gameBoard) throws RemoteException;
}
