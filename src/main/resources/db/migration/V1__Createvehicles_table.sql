CREATE TABLE vehicles (
                          id BIGSERIAL PRIMARY KEY,
                          placa VARCHAR(7) NOT NULL UNIQUE,
                          marca VARCHAR(100) NOT NULL,
                          quilometragem INTEGER NOT NULL CHECK (quilometragem >= 0),
                          status VARCHAR(20) NOT NULL CHECK (status IN ('ATIVO', 'INATIVO')),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vehicles_placa ON vehicles(placa);
CREATE INDEX idx_vehicles_status ON vehicles(status);
