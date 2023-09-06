class Leave extends Event {
    private static final int PRIORITY = 6;

    Leave(Shop shop, Customer customer, double time) {
        super(shop, customer, time);
    }

    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public Event getNextEvent(Shop shop) {
        return this;        
    }

    @Override
    public int getServerId() {
        return 0;
    }

    @Override
    public ImList<Double> updateStatistics(ImList<Double> statistics) {
        double numLeft = statistics.get(2) + 1;
        return statistics.set(2, numLeft);
    }

    @Override
    public String toString() {
        return String.format("%.3f ",this.time) + customer.toString() + " leaves";
    }
}
