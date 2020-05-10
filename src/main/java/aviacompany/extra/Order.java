package aviacompany.extra;


import aviacompany.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<Ticket> tickets;
    private double total;

    public Order() {
        tickets = new ArrayList<>();
        total = 0.0;
    }

    public synchronized List<Ticket> getTickets() {
        return tickets;
    }

    public synchronized void addNewItemToOrder(Ticket ticket, boolean hasAddService) {
        boolean newItem = true;

        for (Ticket item : tickets) {
            if (item.getId() == ticket.getId()) {
                newItem = false;
                break;
            }
        }

        if (newItem) {
            tickets.add(ticket);

            if (hasAddService)
                total += ticket.getFullPrice();
            else
                total += ticket.getPrice();
        }
    }

    public synchronized void deleteItemInOrder(Ticket ticket) {
        for (Ticket item : tickets) {
            if (item.getId() == ticket.getId()) {
                total -= item.getPrice();
                tickets.remove(item);
                break;
            }
        }
    }

    public synchronized void clearOrder() {
        tickets.clear();
        total = 0.0;
    }

    public synchronized double getTotal() {
        return total;
    }
}
