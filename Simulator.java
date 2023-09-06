import java.util.List;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double,Double>> inputTimes;

    Simulator(int numOfServers, int qmax, ImList<Pair<Double,Double>> inputTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
    }

    private ImList<Server> populateShop() {
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 1; i <= numOfServers; i++) {
            Server server = new Server(i, 0.0);
            serverList = serverList.add(server);
        }
        return serverList;
    }

    private ImList<ImList<Customer>> populateQueue() {
        ImList<ImList<Customer>> allQueues = new ImList<ImList<Customer>>();
        for (int i = 1; i <= numOfServers; i++) {
            ImList<Customer> queue  = new ImList<Customer>();
            allQueues = allQueues.add(queue);
        }
        return allQueues;
    }

    public String simulate() {
        String s = "";
        Shop shop = new Shop(populateShop(), populateQueue(), qmax);
        PQ<Event> pq = new PQ<Event>(new EventComp());
        ImList<Double> statistics = new ImList<Double>(List.of(0.0,0.0,0.0));
        int index = 1;
        //populate pq with arrive event
        for (Pair<Double, Double> input : inputTimes) {
            Customer customer = new Customer(input.first(), input.second(), index);
            Event newArrive = new Arrive(shop, customer, customer.getArrivalTime());
            index++;
            pq = pq.add(newArrive);
        }

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pr = pq.poll();
            Event event = pr.first();
            pq = pr.second();
            s += (event.toString() + "\n");
            Event nextEvent = event.getNextEvent(shop);
            boolean shouldAdd = false;
            if (event != nextEvent) {
                shouldAdd = true;
                statistics = nextEvent.updateStatistics(statistics);
                shop = nextEvent.getShop();
            }
            if (shouldAdd) {
                pq = pq.add(nextEvent);
            }
        }
        double averageTime = statistics.get(0) / statistics.get(1);
        int servedTotal = statistics.get(1).intValue();
        int leftTotal = statistics.get(2).intValue();

        s += String.format("[%.3f %d %d]", averageTime, servedTotal, leftTotal);
        return s;
    }
}