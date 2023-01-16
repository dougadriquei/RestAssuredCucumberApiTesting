package model;

public class Payment {
    String end_to_end;
    String conciliation_id;
    Double amount;
    Recipient recipient;
    Sender sender;

    public String getEnd_to_end() {
        return end_to_end;
    }

    public void setEnd_to_end(String end_to_end) {
        this.end_to_end = end_to_end;
    }

    public String getConciliation_id() {
        return conciliation_id;
    }

    public void setConciliation_id(String conciliation_id) {
        this.conciliation_id = conciliation_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }


}
