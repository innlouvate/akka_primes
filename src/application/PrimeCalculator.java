package application;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class PrimeCalculator {

    public static void main(String[] args) {
        PrimeCalculator primeCalculator = new PrimeCalculator();
        primeCalculator.calculate(1, 10000);
    }

    public void calculate(int start, int end) {
        int range[] = {start, end};
        ActorSystem actorSystem = ActorSystem.create("PrimeCalculator");
        ActorRef primeMaster = actorSystem.actorOf(Props.create(PrimeMaster.class, 10));
        primeMaster.tell(range, primeMaster);
    }

}
