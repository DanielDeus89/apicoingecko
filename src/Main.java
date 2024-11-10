import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter a cryptocurrency (e.g., bitcoin, ethereum): ");
        String currency = in.nextLine();

        String currencyModified = currency.replaceAll(" ", "-");

        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + currencyModified + "&vs_currencies=usd";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();

        JsonObject json = gson.fromJson(response.body(), JsonObject.class);

        if (json.has(currencyModified) && json.getAsJsonObject(currencyModified).has("usd")) {
            double priceInUsd = json.getAsJsonObject(currencyModified).get("usd").getAsDouble();
            System.out.println("The price of " + currencyModified.replace("-", " ") + " in USD is $" + String.format("%.8f", priceInUsd));
        } else {
            System.out.println("Currency not found");
        }
    }
}
