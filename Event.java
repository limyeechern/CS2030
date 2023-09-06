abstract class Event {
    protected final Shop shop;
    protected final Customer customer;
    protected final double time;

    Event(Shop shop, Customer customer, double time) {
        this.customer = customer;
        this.shop = shop;
        this.time = time;
    }

    public abstract Event getNextEvent(Shop shop);

    public abstract int getPriority();

    public abstract int getServerId();

    public abstract ImList<Double> updateStatistics(ImList<Double> statistics);
    
    public Shop getShop() {
        return this.shop;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return this.time + customer.toString();
    }
}
