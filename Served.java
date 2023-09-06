class Served extends Event {
    private static final int PRIORITY = 2;
    private final int serverId;

    Served(Shop shop, Customer customer, double time) {
        super(shop, customer, time);
        this.serverId = shop.getServerServingCustomer(customer);
    }

    Served(Shop shop, Customer customer, double time, int serverId) {
        super(shop, customer, time);
        this.serverId = serverId;
    }

    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public int getServerId() {
        return serverId;
    }

    @Override
    public Event getNextEvent(Shop shop) {
        Shop newShop = shop.servedCustomer(customer, time);
        return new Done(newShop, customer, time + customer.getServiceTime(), serverId);  
    }

    @Override
    public ImList<Double> updateStatistics(ImList<Double> statistics) {
        double numLeft = statistics.get(1) + 1;
        double waitingTime = statistics.get(0) + (time - customer.getArrivalTime());
        statistics = statistics.set(1, numLeft);
        statistics = statistics.set(0, waitingTime);
        return statistics;
    }

    @Override
    public String toString() {
        return String.format("%.3f ",this.time) + customer.toString() + " serves by " + serverId;
    }
}
