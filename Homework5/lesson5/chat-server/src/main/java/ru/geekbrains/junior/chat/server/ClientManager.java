package ru.geekbrains.junior.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ClientManager implements Runnable {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    public static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clients.add(this);
            //TODO: ...
            name = bufferedReader.readLine();
            System.out.println(name + " подключился к чату.");
            broadcastMessage("Server: " + name + " подключился к чату.");
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Удаление клиента из коллекции
        removeClient();
        try {
            // Завершаем работу буфера на чтение данных
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            // Завершаем работу буфера для записи данных
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            // Закрытие соединения с клиентским сокетом
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление клиента из коллекции
     */
    private void removeClient() {
        clients.remove(this);
        System.out.println(name + " покинул чат.");
        broadcastMessage("Server: " + name + " покинул чат.");
    }

    /**
     * Отправка сообщения всем слушателям
     *
     * @param message сообщение
     */
    private void broadcastMessage(String message) {
        for (ClientManager client : clients) {
            try {
                if (!client.equals(this) && message != null) {
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (Exception e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void sendPrivateMessage(String message, ClientManager client) {
        try {
            client.bufferedWriter.write(message);
            client.bufferedWriter.newLine();
            client.bufferedWriter.flush();
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    @Override
    public void run() {
        String messageFromClient;
        String privateMessageClientName;
        String[] privateMessageClientNameArray = new String[1];
        while (!socket.isClosed()) {
            try {
                // Чтение данных
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient.startsWith("@")) {
                    privateMessageClientName = messageFromClient.substring(1, messageFromClient.indexOf(" "));
                    privateMessageClientNameArray[0] = privateMessageClientName;
                    Optional clientToAddress = clients.stream().filter(client -> client.name.equals(privateMessageClientNameArray[0])).findFirst();
                    if (clientToAddress.isPresent()) {
                        messageFromClient = this.name + ": "+ messageFromClient;
                        sendPrivateMessage(messageFromClient, (ClientManager) clientToAddress.get());
                    }
                } else {
                    messageFromClient = this.name + ": "+ messageFromClient;
                    // Отправка данных всем слушателям
                    broadcastMessage(messageFromClient);
                }
            } catch (Exception e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                //break;
            }
        }
    }
}
