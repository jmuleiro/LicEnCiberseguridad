import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Endpoint root
        server.createContext("/", exchange -> {
            String response = "Hello World!";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        // Endpoint frases, devuelve una frase al azar de frases.json
        server.createContext("/frases", exchange -> {
            try{
                String content = new String(Files.readAllBytes(Paths.get("frases.json")));
                JSONObject json = new JSONObject(content);
                String phrase = json.getJSONArray("frases").getString(new Random().nextInt(json.getJSONArray("frases").length()));
                exchange.sendResponseHeaders(200, phrase.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(phrase.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Endpoint de suma, recibe dos números como argumentos, los suma y devuelve el resultado.
        //   Args: num1 y num2
        server.createContext("/suma", exchange -> {
            try {
                // Parse query parameters from the URI query string
                String query = exchange.getRequestURI().getQuery();
                java.util.Map<String, String> params = new java.util.HashMap<>();
                for (String param : query.split("&")) {
                    String[] kv = param.split("=");
                    params.put(kv[0], kv[1]);
                }
                int n1 = Integer.parseInt(params.get("num1"));
                int n2 = Integer.parseInt(params.get("num2"));
                // Add them
                int sum = n1 + n2;
                // Send the result
                exchange.sendResponseHeaders(200, String.valueOf(sum).length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(String.valueOf(sum).getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Ejecutar el servidor
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }
}