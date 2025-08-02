-- Inserir veículos de exemplo
INSERT INTO vehicles (placa, marca, quilometragem, status) VALUES
('ABC1234', 'Toyota', 50000, 'ATIVO'),
('XYZ5678', 'Honda', 75000, 'ATIVO'),
('DEF9012', 'Ford', 120000, 'INATIVO');

-- Inserir pneus de exemplo
INSERT INTO tires (numero_fogo, marca, pressao_atual, status) VALUES
('188', 'Bridgestone', 32.5, 'DISPONIVEL'),
('189', 'Michelin', 30.0, 'DISPONIVEL'),
('190', 'Goodyear', 31.5, 'DISPONIVEL'),
('191', 'Pirelli', 29.8, 'DISPONIVEL'),
('192', 'Continental', 33.0, 'EM_USO'),
('193', 'Bridgestone', 28.5, 'EM_USO');

-- Vincular alguns pneus aos veículos
INSERT INTO vehicle_tires (vehicle_id, tire_id, posicao) VALUES
(1, 5, 'A'),
(1, 6, 'B');
