    package com.example.vehicletire.service;

    import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
    import com.example.vehicletire.dto.response.VehicleListResponseDTO;
    import com.example.vehicletire.dto.response.VehicleResponseDTO;
    import com.example.vehicletire.entity.Tire;
    import com.example.vehicletire.entity.TireStatus;
    import com.example.vehicletire.entity.Vehicle;
    import com.example.vehicletire.entity.VehicleTire;
    import com.example.vehicletire.mapper.VehicleMapper;
    import com.example.vehicletire.repository.TireRepository;
    import com.example.vehicletire.repository.VehicleRepository;
    import com.example.vehicletire.repository.VehicleTireRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @Transactional
    public class VehicleService {

        private final VehicleRepository vehicleRepository;
        private final TireRepository tireRepository;
        private final VehicleTireRepository vehicleTireRepository;
        private final VehicleMapper vehicleMapper;

        public VehicleService(VehicleRepository vehicleRepository,
                              TireRepository tireRepository,
                              VehicleTireRepository vehicleTireRepository,
                              VehicleMapper vehicleMapper) {
            this.vehicleRepository = vehicleRepository;
            this.tireRepository = tireRepository;
            this.vehicleTireRepository = vehicleTireRepository;
            this.vehicleMapper = vehicleMapper;
        }

        @Transactional
        public List<VehicleListResponseDTO> findAll() {
            return vehicleRepository.findAll()
                    .stream()
                    .map(vehicleMapper::toListResponseDTO)
                    .collect(Collectors.toList());
        }

        @Transactional
        public VehicleResponseDTO findById(Long id) {
            Vehicle vehicle = vehicleRepository.findByIdWithTires(id)
                    .orElseThrow();

            return vehicleMapper.toResponseDTO(vehicle);
        }

        public VehicleResponseDTO create(VehicleCreateRequestDTO requestDTO) throws Exception {
            if (vehicleRepository.findByPlaca(requestDTO.getPlaca()).isPresent()) {
                throw new Exception("Já existe um veículo com a placa: " + requestDTO.getPlaca());
            }

            Vehicle vehicle = vehicleMapper.toEntity(requestDTO);
            Vehicle savedVehicle = vehicleRepository.save(vehicle);

            return vehicleMapper.toResponseDTO(savedVehicle);
        }

        public void attachTire(Long vehicleId, Long tireId, String posicao) throws Exception {
            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new Exception("Veículo não encontrado"));

            Tire tire = tireRepository.findById(tireId)
                    .orElseThrow(() -> new Exception("Pneu não encontrado"));

            if (tire.getStatus() != TireStatus.DISPONIVEL) {
                throw new Exception("Pneu não está disponível");
            }

            if (vehicleTireRepository.existsByVehicleIdAndPosicao(vehicleId, posicao)) {
                throw new Exception("Posição já está ocupada no veículo");
            }

            VehicleTire vehicleTire = new VehicleTire();
            vehicleTire.setVehicle(vehicle);
            vehicleTire.setTire(tire);
            vehicleTire.setPosicao(posicao);

            vehicleTireRepository.save(vehicleTire);

            tire.setStatus(TireStatus.EM_USO);
            tireRepository.save(tire);
        }

        public void detachTire(Long vehicleId, Long tireId) throws Exception {
            VehicleTire vehicleTire = vehicleTireRepository.findByVehicleIdAndTireId(vehicleId, tireId)
                    .orElseThrow(() -> new Exception("Associação não encontrada"));

            vehicleTireRepository.delete(vehicleTire);

            Tire tire = vehicleTire.getTire();
            tire.setStatus(TireStatus.DISPONIVEL);
            tireRepository.save(tire);
        }
    }
