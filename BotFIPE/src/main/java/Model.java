import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{

	private List<Observer> observers = new LinkedList<Observer>();

	private List<Carro> carros = new LinkedList<Carro>();

	private List<Item> listaitem = new LinkedList<Item>();
	
	Item item = new Item();

	private static Model uniqueInstance;

	private int n=0;

	private String marca, modelo, ano;

	ObjectContainer cars = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/cars.db4o");

	private Model(){}

	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}

	public void registerObserver(Observer observer){
		observers.add(observer);
	}

	public void notifyObservers(long chatId, String carsData){
		for(Observer observer:observers){
			observer.update(chatId, carsData);
		}
	}

	public void addStudent(Carro carro){
		this.carros.add(carro);
	}

	public void searchMarca(Update update) throws IOException{
		String carData = null;

		for(Carro carro: carros){		
			carData = Main.pesquisa(update.message().text());
			System.out.println(carData);		
		}

		if(!carData.equals("-1")){
			this.notifyObservers(update.message().chat().id(), carData);
		} else {
			this.notifyObservers(update.message().chat().id(), "Marca não encontrada");
		}

	}

	public void searchModelo(Update update) throws IOException{
		String allData = null;
		n++;
		if(n==1){
			marca = update.message().text();
		}else if(n==2){
			modelo = update.message().text();
		}else if(n==3){
			ano = update.message().text();

			//System.out.println(marca+"x" + modelo+"x" + ano);
			for(Carro carro: carros){	
				n=0;
				allData = Main.pesquisaModelo(marca, modelo, ano);
			}
			
			item.setAno(ano);
			item.setMarca(marca);
			item.setModelo(modelo);
			item.setValor(allData);
			
			listaitem.add(item);

			if(allData != null){
				this.notifyObservers(update.message().chat().id(), allData);
			} else {
				this.notifyObservers(update.message().chat().id(), "Veículo não encontrado");
			}	
		}
	}
	
	public void history(Update update){
		for(Item lista:listaitem){
			cars.store(lista);
		}
		
		cars.commit();

		Query query = cars.query();
		query.constrain(Item.class);
		List<Item> allCars = query.execute();

		for (Item  it : allCars) {
			System.out.println(it);
			
				this.notifyObservers(update.message().chat().id(), it.toString());
			
		}
		
		
	}

}
