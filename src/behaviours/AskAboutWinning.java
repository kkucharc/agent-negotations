package behaviours;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

import java.util.Date;

import agents.BiddingAgent;

public class AskAboutWinning  extends WakerBehaviour {
	private BiddingAgent a;

	public AskAboutWinning(Agent a, Date wakeupDate) {

		super(a, wakeupDate);
		this.a = (BiddingAgent) a;
	}

	protected void onWake() {
		a.sendOfferAskToMorris();
	}
}
