import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.google.gson.*;

public class Main {

	static String cod, codmodelo, codano;
	private static Model model;
	private static ArrayList<Carro> lista = new ArrayList<>();
	static Carro carro;
	private static ArrayList<Anos> listaanos = new ArrayList<>();
	static Anos ano;
	private static String valor;

	public static void main(String[] args) throws IOException, InterruptedException {
		//pesquisa();
		model = Model.getInstance();
		initializeModel(model);
		View view = new View(model);
		model.registerObserver(view); //connection Model -> View
		view.receiveUsersMessages();


	}

	public static void initializeModel(Model model){
		model.addStudent(new Carro("joao", "111"));
	}


	public static String pesquisa(String modelo) throws IOException {

		//Reader reader = new InputStreamReader(new URL("https://fipe.parallelum.com.br/api/v1/carros/marcas").openStream()); //Read the json output
		Reader reader = new InputStreamReader(new URL("http://fipe.parallelum.com.br/api/v1/carros/marcas").openStream()); //Read the json output

		Gson gson = new GsonBuilder().create();

		Carro[] cars = gson.fromJson(reader, Carro[].class);

		Arrays.stream(cars).forEach( e -> {
			if(e.getNome().equals(modelo)){
				cod = e.getCodigo();

			}
		});
		return cod;
	}

	public static String pesquisaModelo(String marca, String modelo, String ano) throws IOException {
	
		
		//Marcas
		Reader reader = new InputStreamReader(new URL("http://fipe.parallelum.com.br/api/v1/carros/marcas").openStream()); //Read the json output
		Gson gson = new GsonBuilder().create();
		Carro[] cars = gson.fromJson(reader, Carro[].class);
		Arrays.stream(cars).forEach( e -> {
			if(e.getNome().equals(marca)){
				cod = e.getCodigo();

			}
		});

		System.out.println(cod);
		
		//Modelos
		reader = new InputStreamReader(new URL("http://fipe.parallelum.com.br/api/v1/carros/marcas/" + cod + "/modelos").openStream()); //Read the json output

		JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("modelos");

		Carro[] arrName = new Gson().fromJson(jsonArray, Carro[].class);

		List<Carro> lstName = new ArrayList<>();
		lstName = Arrays.asList(arrName);

		for (Carro str : lstName) {
			if(str.getNome().equals(modelo)){
				codmodelo = str.getCodigo();
			}
		}   

		//Ano
		Reader reader1 = new InputStreamReader(new URL("http://fipe.parallelum.com.br/api/v1/carros/marcas/" + cod + "/modelos/" + codmodelo + "/anos").openStream()); //Read the json output

		Gson gson1 = new GsonBuilder().create();

		Carro[] models = gson1.fromJson(reader1, Carro[].class);

		Arrays.stream(models).forEach( e -> {
			if(e.getCodigo().substring(0,4).equals(ano)){
				codano = e.getCodigo();
				System.out.println(codano);
			}
		});

		//Valor
		Reader reader2 = new InputStreamReader(new URL("http://fipe.parallelum.com.br/api/v1/carros/marcas/" + cod + "/modelos/" + codmodelo + "/anos/" + codano).openStream()); //Read the json output

        Gson gson2 = new Gson();
        Object json = gson2.fromJson(reader2, Object.class);       
        String[] valorcarro;
        valorcarro=json.toString().split(",");
       
        System.out.println(valorcarro[0].substring(7) + ",00");
        
           
		return valorcarro[0].substring(7) + ",00";	
		
		

	}


}


