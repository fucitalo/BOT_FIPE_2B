import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

class User {

	private final String nome;
	private final int codigo;

	public User(String nome, int codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(nome).append("," + codigo).toString();
	}
}

public class Principal {

	public static void main(String[] args) throws IOException {

		Reader reader = new InputStreamReader(new URL("https://fipe.parallelum.com.br/api/v1/carros/marcas").openStream()); //Read the json output
		//String reader = new String(Files.readAllBytes(Paths.get("https://fipe.parallelum.com.br/api/v1/carros/marcas")));
		Gson gson = new GsonBuilder().create();

		User[] users = gson.fromJson(reader, User[].class);

		Arrays.stream(users).forEach( e -> {
			System.out.println(e);
		});

	}
}
