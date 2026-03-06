package com.fichel.conversordemoedas;

import com.google.gson.Gson;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ConversorDeMoedasApplication {

    public static void main(String[] args) {


        var scanner = new java.util.Scanner(System.in);
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        System.out.println("---------------------------------");
        System.out.println(" ");
        var opcao = 1;

        while (opcao == 1){

            try {

                System.out.println("Digite a moeda de origem (ex: USD): ");
                String URL = "https://v6.exchangerate-api.com/v6/2352a52993fb5a2622c64cf5/latest/" + scanner.nextLine();

                HttpClient httpClient = HttpClient.newHttpClient();


                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(URL))
                        .build();

                String response = httpClient.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString()).body();
                Gson gson = new Gson();
                ObjetoConv obj = gson.fromJson(response, ObjetoConv.class);

                System.out.println("Digite a moeda de destino (ex: EUR): ");
                String moedaDestino = scanner.nextLine();
                System.out.println("Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();

                double valorConvertido = obj.converterPara(moedaDestino, valor);
                System.out.printf("%.2f %s equivalem a %.2f %s%n", valor, obj.getBaseCode(), valorConvertido, moedaDestino);
                System.out.println("Deseja realizar outra conversão? (1 - Sim,  0 - Não)");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha após nextInt()

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Erro ao acessar a API de câmbio", e);
            }

        }

    }
}
