package client;

import shared.GameClient;
import shared.GameServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GameClientImpl implements GameClient {
    static final String HOST_ADDR = "localhost";
    static final int HOST_PORT = 54321;

    private int[][] gameBoard;

    public GameClientImpl() {
        gameBoard = new int[5][5];
    }

    @Override
    public void updateGameBoard(int[][] gameBoard) throws RemoteException {
        for (int i = 0; i < gameBoard.length; ++i) {
            for (int j = 0; j < gameBoard[0].length; ++j) {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    public int[][] getGameBoard() {
        return gameBoard.clone();
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException, InterruptedException {
        /* Locate server's registry */
        Registry registry = LocateRegistry.getRegistry(HOST_ADDR, HOST_PORT);

        /* Obtain a reference to GameServer */
        GameServer serverStub = (GameServer) registry.lookup("GameServer");

        /* Export the client so the server can invoke its methods */
        GameClient client = new GameClientImpl();                               // Instantiate GameClient object
        GameClient clientStub =
                (GameClient) UnicastRemoteObject.exportObject(client, 0);  // Export GameClient object
        registry.bind("GameClient", clientStub);                          // Bind it to server's registry

        /* GameClient makes a move */
        System.out.println("Client is making a move");
        serverStub.makeMove(clientStub, 2, 3);  // Make a move
        TimeUnit.SECONDS.sleep(1);           // Wait for server to update the game board
        System.out.println("Updated game board:");  // Print the game board to the console
        for (int[] row : ((GameClientImpl) client).getGameBoard()) {
            System.out.println(Arrays.toString(row));
        }
        System.exit(0);
    }
}
