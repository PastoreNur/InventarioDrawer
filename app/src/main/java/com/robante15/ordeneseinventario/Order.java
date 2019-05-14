package com.robante15.ordeneseinventario;

import java.util.List;

public class Order {

    private int id;
    private  String _id;
    private  Client cliente;
    private double fecha;
    private List<Product> Products;
    private int[] cantidades;
    private double total;
    private String estado;

    public void Order(int id, String _id, Client client, double fecha, List<Product> product,int[] cantidades,
                      double total, String estado){

        this.id = id;
        this._id = _id;
        this.cliente = client;
        this.Products = product;
        this.cantidades = cantidades;
        this.total = total;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public double getFecha() {
        return fecha;
    }

    public void setFecha(double fecha) {
        this.fecha = fecha;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public int[] getCantidades() {
        return cantidades;
    }

    public void setCantidades(int[] cantidades) {
        this.cantidades = cantidades;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
