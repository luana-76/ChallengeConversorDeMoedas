package com.coversor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner leitura = new Scanner(System.in);/* Entrada de dados */
        String decisao;/* Escolha do usuário */
        String valor;/* Valor para conversão */

        String moedaOrigem = "";
        String moedaDestino = "";

        String apiKey = "1a8bbb10ce293cf9a2d8eeda";

        do {

            /* Menu disponível */
            System.out.println("Seja bem-vindo/a ao Conversor de Moeda =)");
            System.out.println("""
                
                            1) Dólar =>> Peso argentino
                            2) Peso argentino =>> Dólar
                            3) Dólar =>> Real brasileiro
                            4) Real brasileiro =>> Dólar
                            5) Dólar =>> Peso colombiano
                            6) Peso colombiano =>> Dólar
                            7) Sair
                            --------------------------------------
                                
                            """);

            System.out.println("Escolha uma opção válida:");
            decisao = leitura.nextLine();

            /* Caso digite o valor de saída(7) */
            if (decisao.equals("7") || decisao.equalsIgnoreCase("sair")) {

                System.out.println("\nObrigado pela preferência e volte sempre!");
                break;

            }

            System.out.println("\nDigite o valor que deseja converter:");
            valor = leitura.nextLine();
            
            /* Escolhas para conversão */

            switch (decisao) {
                case "1":
                    moedaOrigem = "USD";
                    moedaDestino = "ARS";
                    break;
                case "2":
                    moedaOrigem = "ARS";
                    moedaDestino = "USD";
                    break;
                case "3":
                    moedaOrigem = "USD";
                    moedaDestino = "BRL";
                    break;
                case "4":
                    moedaOrigem = "BRL";
                    moedaDestino = "USD";
                    break;
                case "5":
                    moedaOrigem = "USD";
                    moedaDestino = "COP";
                    break;
                case "6":
                    moedaOrigem = "COP";
                    moedaDestino = "USD";
                    break;
                default:
                    System.out.println("Opção inválida!");
                    continue;
            }

            /* Url a ser chamada */
            String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + moedaOrigem + "/" + moedaDestino;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            /* Pegando a conversão da moeda */
            String body = response.body();
            String chave = "\"conversion_rate\":";
            int inicio = body.indexOf(chave) + chave.length();
            int fim = body.indexOf(",", inicio);
            if (fim == -1) fim = body.indexOf("}", inicio);
            String taxaStr = body.substring(inicio, fim).trim();

            double taxa = Double.parseDouble(taxaStr);
            double valorDigitado = Double.parseDouble(valor);
            double resultado = valorDigitado * taxa;

            /* Resultado final */
            System.out.printf("\nValor %.2f [%s] corresponde ao valor final de =>>> %.2f [%s]%n \n ", valorDigitado, moedaOrigem, resultado, moedaDestino);

            System.out.println("------------------------------------");

        } while (true);
    }
}