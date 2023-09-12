package br.edu.ifpb.gugawag.so.sockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Server {
    private static String FILES_DIRECTORY = System.getProperty("user.dir") + "/files";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1030);
        System.out.println("Server iniciado\n");

        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado");

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while (true) {
            String entrada = dis.readUTF();

            int opcao = Integer.parseInt(entrada.split(" ")[0]);

            switch (opcao) {
                case 1 -> {
                    List<String> arquivos = readdir();
                    StringBuilder listagem = new StringBuilder();

                    for (String arquivo : arquivos) {
                        listagem.append(arquivo).append(" ");
                    }

                    dos.writeUTF(listagem.toString());
                }
                case 2 -> rename(entrada.split(" ")[1], entrada.split(" ")[2]);
                case 3 -> create(entrada.split(" ")[1]);
                case 4 -> remove(entrada.split(" ")[1]);
            }
        }
    }

    private static List<String> readdir() throws IOException {
        System.out.println("abc");
        Stream<Path> files = Files.list(Paths.get(FILES_DIRECTORY));
        List<String> arquivos = new ArrayList<>();
        for (Path file : files.toList()) {
            arquivos.add(file.getFileName().toString());
        }
        return arquivos;
    }

    private static void rename(String nomeAnterior, String nomeNovo) throws IOException {
        Path file = Paths.get(FILES_DIRECTORY + "/" + nomeAnterior);
        Files.move(file, file.resolveSibling(nomeNovo));
    }

    private static void create(String nomeArquivo) throws IOException {
        Path file = Paths.get(FILES_DIRECTORY + "/" + nomeArquivo);
        Files.createFile(file);
    }

    private static void remove(String nomeArquivo) throws IOException {
        Path file = Paths.get(FILES_DIRECTORY + "/" + nomeArquivo);
        Files.delete(file);
    }
}
