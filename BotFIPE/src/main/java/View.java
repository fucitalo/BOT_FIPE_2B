import java.io.IOException;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer{


	TelegramBot bot = TelegramBotAdapter.build("");

	//Object that receives messages
	GetUpdatesResponse updatesResponse;
	//Object that send responses
	SendResponse sendResponse;
	//Object that manage chat actions like "typing action"
	BaseResponse baseResponse;
	private String marca, modelo,ano,hist;
	private int n = -1;


	int queuesIndex=0;

	ControllerSearch controllerSearch; //Strategy Pattern -- connection View -> Controller

	boolean searchBehaviour = false;
	boolean kbUsed = false;

	private Model model;

	public View(Model model){
		this.model = model; 
	}

	public void setControllerSearch(ControllerSearch controllerSearch){ //Strategy Pattern
		this.controllerSearch = controllerSearch;
	}

	public void receiveUsersMessages() throws IOException {



		//infinity loop
		while (true){

			//taking the Queue of Messages
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(queuesIndex));

			//Queue of messages
			List<Update> updates = updatesResponse.updates();

			//taking each message in the Queue
			for (Update update : updates) {
				
				//updating queue's index
				queuesIndex = update.updateId()+1;

				if(this.searchBehaviour==true){
					this.callController(update);

				}

				if(kbUsed==false){
					
					kbUsed=true;
					sendResponse = bot.execute(new SendMessage(update.message().chat()
							.id(), "Selecione a operação desejada(Marca para o código , " 
									+ "Valor para o valor do veículo e Histórico de Consultas para"
									+ " verificar as pesquisas realizadas)").replyMarkup(new ReplyKeyboardMarkup(
									new String[] { "Marca", "Valor", "Histórico de Consultas"})));
					
					
				}
				
				if(update.message().text().toLowerCase().equals("marca")){
					sendResponse = bot
							.execute(new SendMessage(update.message().chat().id(), "↓")
									.replyMarkup(new ReplyKeyboardRemove()));
					setControllerSearch(new ControllerSearchMarca(model, this));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual a marca?"));
					this.searchBehaviour = true;
					n=5;
				}else if(update.message().text().toLowerCase().equals("valor")){
					sendResponse = bot
							.execute(new SendMessage(update.message().chat().id(), "↓")
									.replyMarkup(new ReplyKeyboardRemove()));
					setControllerSearch(new ControllerSearchModelo(model, this));
					this.searchBehaviour = true;
					n=1;
				}
				else if(update.message().text().equals("Histórico de Consultas")){
					sendResponse = bot
							.execute(new SendMessage(update.message().chat().id(), "↓")
									.replyMarkup(new ReplyKeyboardRemove()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Confirme a operação"));
					hist = update.message().text();
					setControllerSearch(new ControllerSearchHistory(model, this));		
					this.searchBehaviour = true;	
					kbUsed = false;
					n=0;
				}
				/*if(n==-1) {
					
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Digite um nome válido"));
				}*/

								
				if(n==1){
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual a marca?"));	
					n=2;
				}else if(n==2){
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual o modelo?"));
					marca = update.message().text();
					n=3;
				}else if(n==3){
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual o ano?"));
					modelo = update.message().text();
					n=4;
				}else if(n==4){
					ano = update.message().text();
					System.out.println(marca + " e " + modelo + " e " + ano);
					n=0;
					kbUsed=false;
				}				
								
			}

		}
		

	}


	public void callController(Update update) throws IOException{
		this.controllerSearch.search(update);
	}

	public void update(long chatId, String carsData){
		sendResponse = bot.execute(new SendMessage(chatId, carsData));
		this.searchBehaviour = false;
	}

	public void sendTypingMessage(Update update){
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
	}


}
