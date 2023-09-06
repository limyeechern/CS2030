class Arrive extends Event {
    private static final int PRIORITY = 4;
    
    Arrive(Shop shop, Customer customer, double time) {
        super(shop, customer, time);
    }

    @Override
    public Event getNextEvent(Shop shop) {
        Shop newShop = shop.newCustomer(customer, time);
        if (shop == newShop) {
            return new Leave(shop, customer, time);
        } else if (newShop.isCustomerInQueue(customer)) {
            return new Wait(newShop, customer, time);
        } else {
            return new Served(newShop, customer, time);
        }
    }

    @Override
    public int getServerId() {
        return 0;
    }

    @Override
    public ImList<Double> updateStatistics(ImList<Double> statistics) {
        return statistics;
    }

    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public String toString() {
        return String.format("%.3f ",this.time) + customer.toString() + " arrives";
    }
}
