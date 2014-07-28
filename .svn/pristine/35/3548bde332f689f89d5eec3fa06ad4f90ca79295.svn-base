package behaviours;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

import java.util.Date;

import agents.BiddingAgent;

public class BidOnTime extends WakerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4126435249025821820L;
	private BiddingAgent a;

	public BidOnTime(Agent a, Date wakeupDate) {

		super(a, wakeupDate);
		this.a = (BiddingAgent) a;
	}

	protected void onWake() {
		a.sendBidMsgToMorris();
	}
}
