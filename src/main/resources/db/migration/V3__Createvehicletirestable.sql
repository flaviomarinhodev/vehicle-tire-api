CREATE TABLE vehicle_tires (
                               id BIGSERIAL PRIMARY KEY,
                               vehicle_id BIGINT NOT NULL,
                               tire_id BIGINT NOT NULL,
                               posicao VARCHAR(10) NOT NULL,
                               installed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               CONSTRAINT fk_vehicle_tires_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE,
                               CONSTRAINT fk_vehicle_tires_tire FOREIGN KEY (tire_id) REFERENCES tires(id) ON DELETE CASCADE,
                               CONSTRAINT uk_vehicle_position UNIQUE (vehicle_id, posicao)
);

CREATE INDEX idx_vehicle_tires_vehicle ON vehicle_tires(vehicle_id);
CREATE INDEX idx_vehicle_tires_tire ON vehicle_tires(tire_id);
