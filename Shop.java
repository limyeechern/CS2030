class Shop {
    private final ImList<Server> serverList;
    private final ImList<ImList<Customer>> queueList;
    private final int qmax;

    Shop(ImList<Server> serverList, ImList<ImList<Customer>> queueList, int qmax) {
        this.serverList = serverList;
        this.queueList = queueList;
        this.qmax = qmax;
    }

    private boolean areAllServersBusy(Customer customer, double timeNow) {
        for (int i = 0; i < serverList.size(); i++) {
            if (!serverList.get(i).serverIsBusy(customer, timeNow)) {
                return false;
            }
        }
        return true;
    }

    private boolean areQueuesFull() {
        for (int i = 0; i < queueList.size(); i++) {
            if (queueList.get(i).size() < qmax) {
                return false;
            }
        }
        return true;
    }

    private ImList<ImList<Customer>> copyQueue() {
        ImList<ImList<Customer>> copyQueue = new ImList<ImList<Customer>>().addAll(this.queueList);
        return copyQueue;
    }

    private ImList<ImList<Customer>> addQueue(Customer customer) {
        ImList<ImList<Customer>> copyQueue = copyQueue();
        for (int i = 0; i < queueList.size(); i++) {
            if (queueList.get(i).size() < qmax) {
                ImList<Customer> newQueue = queueList.get(i).add(customer);
                copyQueue = copyQueue.set(i, newQueue);
                break;
            }
        }
        return copyQueue;
    }

    private ImList<ImList<Customer>> removeQueue(Customer customer) {
        ImList<ImList<Customer>> copyQueue = copyQueue();
        Pair<Integer, Integer> pair = getCustomerIndexInQueue(customer);
        ImList<Customer> newQueue = copyQueue.get(pair.first()).remove(pair.second());
        copyQueue = copyQueue.set(pair.first(), newQueue);
        return copyQueue;
    }

    private Pair<Integer, Integer> getCustomerIndexInQueue(Customer customer) {
        for (int i = 0; i < queueList.size(); i++) {
            for (int j = 0; j < qmax; j++) {
                try { 
                    if (queueList.get(i).size() > 0 && queueList.get(i).get(j).equals(customer)) {
                        return new Pair<Integer, Integer>(i, j);
                    }
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return new Pair<Integer, Integer>(-1, -1);
        
    }

    public int getCustomerQueue(Customer customer) {
        return getCustomerIndexInQueue(customer).first() + 1;
    }

    public boolean isCustomerInQueue(Customer customer) {
        Pair<Integer, Integer> pair = getCustomerIndexInQueue(customer);
        return pair.first() != -1;
    }

    public double getWaitingQueueTime(Customer customer) {
        Pair<Integer, Integer> pair = getCustomerIndexInQueue(customer);
        int serverId = pair.first();
        double waitingTime = serverList.get(serverId).getServerTimeServingTill();
        for (int i = 0; i < pair.second(); i++) {
            Customer customerOfQueue = queueList.get(serverId).get(i);
            waitingTime += customerOfQueue.getServiceTime();
            if (customer.equals(customerOfQueue)) {
                break;
            }
        }
        return waitingTime;
    }

    private ImList<Server> updateServer(Customer customer, double timeNow) {
        for (int i = 0; i < serverList.size(); i++) {
            Server server = serverList.get(i);
            if (!server.serverIsBusy(customer, timeNow)) {
                double time = timeNow + customer.getServiceTime();
                double newTime = time;
                Server updatedServer = new Server(server.getServerId(), newTime, customer);
                return serverList.set(i, updatedServer);
            }
        }
        return serverList;
    }

    public int getServerServingCustomer(Customer customer) {
        for (Server server : serverList) {
            if (customer.equals(server.getCustomer())) {
                return server.getServerId();
            }
        }
        return -1;
    }

    public Shop newCustomer(Customer customer, double timeNow) {
        if (areAllServersBusy(customer, timeNow)) {
            if (areQueuesFull()) {
                return this;
            } else {
                return new Shop(this.serverList, addQueue(customer), qmax);
            }
        } else {
            return new Shop(updateServer(customer, timeNow), this.queueList, qmax);
        }
    }

    public Shop servedCustomer(Customer customer, double timeNow) {
        if (isCustomerInQueue(customer)) {
            ImList<ImList<Customer>> newQueue = removeQueue(customer);
            return new Shop(updateServer(customer, timeNow), newQueue, qmax);
        } else {
            return this;
            // return new Shop(updateServer(customer, timeNow), this.queueList, qmax);
        }
    }
}
