class Wait extends Event {
    private static final int PRIORITY = 5;

    Wait(Shop shop, Customer customer, double time) {
        super(shop, customer, time);
    }

    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public int getServerId() {
        return 0;
    }

    @Override
    public Event getNextEvent(Shop shop) {
        double waitingQueueTime = shop.getWaitingQueueTime(customer);
        return new Served(shop, customer, waitingQueueTime, shop.getCustomerQueue(customer));
    }

    @Override
    public ImList<Double> updateStatistics(ImList<Double> statistics) {
        return statistics;
    }

    @Override
    public String toString() {
        return String.format("%.3f ",this.time) + customer.toString() +
            " waits at " + shop.getCustomerQueue(customer);
    }
}
