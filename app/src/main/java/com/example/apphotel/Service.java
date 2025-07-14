package com.example.apphotel;

// Modelo simples para representar um serviço
public class Service {
    private final int iconResId;
    private final String name;

    public Service(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }
}