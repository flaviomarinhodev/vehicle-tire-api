package com.example.vehicletire.entity;

public enum VehicleStatus {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    VehicleStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static VehicleStatus fromString(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }

        for (VehicleStatus vehicleStatus : VehicleStatus.values()) {
            if (vehicleStatus.name().equalsIgnoreCase(status) ||
                    vehicleStatus.descricao.equalsIgnoreCase(status)) {
                return vehicleStatus;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + status +
                ". Valores permitidos: ATIVO, INATIVO");
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
