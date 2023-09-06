import java.util.Comparator;

class EventComp implements Comparator<Event> {
    public int compare(Event i, Event j) {
        double difference = i.getTime() - j.getTime();
        if (difference < 0) {
            return -1;
        } else if (difference > 0) {
            return 1;
        } else {
            int priority = i.getPriority() - j.getPriority();
            int customerIdDifference = i.getCustomer().getIndex() - j.getCustomer().getIndex();
            int returnValue = (priority == 0) ? customerIdDifference : priority;
            return returnValue;
        }
    }
}

