package com.robante15.ordeneseinventario;

import java.util.List;

public class Order {

    private  String ID;
    private  Client cliente;
    private double fecha;
    private List<Product> Products;
    private int cantidades;
    private double total;
    private String estado;

    public double getFecha() {
        return fecha;
    }

    public void setFecha(double fecha) {
        this.fecha = fecha;
    }

    public List<com.robante15.ordeneseinventario.Product> getProduct() {
        return getProducts();
    }

    public void setProduct(List<com.robante15.ordeneseinventario.Product> product) {
        setProducts(product);
    }

    public int getCantidades() {
        return cantidades;
    }

    public void setCantidades(int cantidades) {
        this.cantidades = cantidades;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
