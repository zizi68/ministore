/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class Report {
    private String label;
    private Long revenue;
    private Long funds;
    private Long returns;
    private Long profit;
    private Long profitRate;

    public Report() {
    }

    public Report(String label, Long revenue, Long funds, Long returns, Long profit, Long profitRate) {
        this.label = label;
        this.revenue = revenue;
        this.funds = funds;
        this.returns = returns;
        this.profit = profit;
        this.profitRate = profitRate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getFunds() {
        return funds;
    }

    public void setFunds(Long funds) {
        this.funds = funds;
    }

    public Long getReturns() {
        return returns;
    }

    public void setReturns(Long returns) {
        this.returns = returns;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public Long getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(Long profitRate) {
        this.profitRate = profitRate;
    }
    
}
