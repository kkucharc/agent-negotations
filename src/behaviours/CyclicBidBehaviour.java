package behaviours;

import agents.BiddingAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import data.Preference;

public class CyclicBidBehaviour extends TickerBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5157763227346821231L;
	private Preference pref;
	private BiddingAgent a;
	
	
	public CyclicBidBehaviour(Agent a, Preference p){
		
		super(a, p.getTimeBid());
		this.pref = p;
		this.a = (BiddingAgent)a;
	}
	

	protected void onTick() {
		a.sendAskMsgToMorris();
	}


}
