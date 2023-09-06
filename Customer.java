class Customer {
    private final double arrivalTime;
    private final double serviceTime;
    private final int index;

    Customer(double arrivalTime, double serviceTime, int index) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.index = index;
    }    

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Customer customer) {
            return this.index == customer.index;
        } else {
            return false;
        }
    }
}
