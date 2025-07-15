package com.btg.pactual.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private boolean active;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Fund fund;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Fund getFund() { return fund; }
    public void setFund(Fund fund) { this.fund = fund; }
}
