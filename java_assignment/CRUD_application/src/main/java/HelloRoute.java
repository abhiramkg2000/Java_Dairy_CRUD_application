//public class HelloRoute {
    /*
    server.createContext("/api/hello", (exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String noNameText = "World";
            String name = params.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);
            String respText = String.format("Hello %s!", name);
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }));*/
    /*
    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
    }*/
//}
