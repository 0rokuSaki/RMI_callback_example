package server;

import shared.GameClient;
import shared.GameServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class GameServerImpl implements GameServer {
    static final int PORT = 54321;           // Server's host port

    @Override
    public void makeMove(GameClient stub, int i, int j) throws RemoteException {
        System.out.println("Handling client: " + stub);
        int[][] updatedGameBoard = new int[5][5];
        updatedGameBoard[i][j] = 1;
        stub.updateGameBoard(updatedGameBoard);
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        LocateRegistry.createRegistry(PORT);       // Create RMI Registry
        GameServer server = new GameServerImpl();  // Instantiate GameServer object
        GameServer stub =
                (GameServer) UnicastRemoteObject.exportObject(server, 0);  // Export object
        LocateRegistry.getRegistry(PORT).bind("GameServer", stub);   // Bind stub
        System.out.println("Game server is ready");
    }
}
