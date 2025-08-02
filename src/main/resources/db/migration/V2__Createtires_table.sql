CREATE TABLE tires (
                       id BIGSERIAL PRIMARY KEY,
                       numero_fogo VARCHAR(50) NOT NULL UNIQUE,
                       marca VARCHAR(100) NOT NULL,
                       pressao_atual DOUBLE PRECISION NOT NULL CHECK (pressao_atual > 0),
                       status VARCHAR(20) NOT NULL CHECK (status IN ('EM_USO', 'DISPONIVEL')),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tires_numero_fogo ON tires(numero_fogo);
CREATE INDEX idx_tires_status ON tires(status);
