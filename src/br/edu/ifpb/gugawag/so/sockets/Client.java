package br.edu.ifpb.gugawag.so.sockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.crypto.Data;

public class Client {

        private static List<String> opcoes = new ArrayList<>(){
        {
            add("Listar Arquivos");
            add("Renomear arquivo");
            add("Criar arquivo");
            add("Excluir arquivo");
            add("Sair");
        }
    };

    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost", 1030);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        System.out.println("Cliente conectado\n");

        String opcao = menu();
        int opcaoInt = Integer.parseInt(opcao.split(" ")[0]);

        while(opcaoInt < opcoes.size()){
            dos.writeUTF(opcao);

            if (opcaoInt == 1){
                System.out.println(dis.readUTF());
            }

            opcao = menu();
            opcaoInt = Integer.parseInt(opcao.split(" ")[0]);
        }

        System.out.println("Cliente desconectado");
    }

    public static String menu() {
        System.out.println("\n==== Network File System ====");
        opcoes.forEach((opcao) -> {
            System.out.println((opcoes.indexOf(opcao)+1) + " - " + opcao);
        });

        Scanner entrada = new Scanner(System.in);
        return entrada.nextLine();
    }
}