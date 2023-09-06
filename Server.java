class Server {
    private final int id;
    private final double timeServingTill;
    private final Customer customer;

    Server(int id, double timeServingTill) {
        this.id = id;
        this.timeServingTill = timeServingTill;
        this.customer = new Customer(0.0, 0.0, -1);
    }

    Server(int id, double timeServingTill, Customer customer) {
        this.id = id;
        this.timeServingTill = timeServingTill;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getServerTimeServingTill() {
        return this.timeServingTill;
    }

    public int getServerId() {
        return this.id;
    }

    public boolean serverIsBusy(Customer customer, double timeNow) {
        if (this.timeServingTill <= timeNow) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Server serving until " + timeServingTill;
    }
}
