package start;

import jade.Boot3;

import java.util.List;

import agents.ClientAgent;

import dao.ClientDAO;
import dao.DAOFactory;
import dao.domain.Client;

public class Start {

	private static final int MORRIS_AMOUNT = 1; //system aukcyjny
	
	public static void main(String args[]) {
		
		/*inizjalizacja bazy danych*/
		DAOFactory.init();
		
		/*wczytanie pliku konfiguracyjnego*/
		ClientAgent.loadPreferences("testConfiguration.txt");
		
		List<Client> clientsInfos = ClientDAO.getAll();
		
		int all = 1 + MORRIS_AMOUNT + clientsInfos.size();
		String[] param = new String[all];

		param[0] = "-gui";
		
		for (int i = 1; i < MORRIS_AMOUNT + 1; ++i) {
			param[i] = "MorrisColumn"+ i +":agents.MorrisColumn";
		}
		
		int i = MORRIS_AMOUNT + 1;
		for (Client c : clientsInfos) {
			param[i] = "Client"+c.getId() +":agents.ClientAgent";
			i++;
		}
		//utworzenie licznik�w wygranych licytacji/przegranych-agentowi nie uda�o si� wygra� �adnej aukjci
		

		new Boot3(param);
		
	}

	
}
