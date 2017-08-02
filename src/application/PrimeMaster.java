package application;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;

public class PrimeMaster extends UntypedActor {

    private Router workerRouter;
    private int numberOfWorkers;
    private int numberOfResults;

    ArrayList<Routee> routees = new ArrayList<Routee>();

    public PrimeMaster(int numWorkers) {
        numWorkers = numWorkers;

        for(int i=0; i<numberOfWorkers; i++) {
            ActorRef r = getContext().actorOf(Props.create(PrimeWorker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        workerRouter = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof int[]) {
            int msg[] = (int[])message;
            int start = msg[0];
            int end = msg[1];
            int numberOfNumbers = end - start;
            int segmentLength = numberOfNumbers / numberOfWorkers;

            for(int i=0; i<numberOfWorkers; i++) {
                int startNumber = start + (i * segmentLength);
                int endNumber = startNumber + segmentLength - 1;
                if(i == numberOfWorkers -1) {
                    endNumber = end;
                }
                int send[] = { startNumber, endNumber };
                workerRouter.route(send, getSelf());
            }
        }
    }
}
