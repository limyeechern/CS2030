class Done extends Event {
    private static final int PRIORITY = 2;
    private final int serverId;

    Done(Shop shop, Customer customer, double time, int serverId) {
        super(shop, customer, time);
        this.serverId = serverId;
    }

    @Override 
    public Event getNextEvent(Shop shop) {
        return this;
    }

    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public int getServerId() {
        return serverId;
    }

    @Override
    public ImList<Double> updateStatistics(ImList<Double> statistics) {
        return statistics;
    }

    @Override
    public String toString() {
        return String.format("%.3f ",this.time) + customer.toString()
            + " done serving by " + serverId;
    }
}

