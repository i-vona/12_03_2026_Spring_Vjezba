package hr.java.spring.boot.Example.service;

import hr.java.spring.boot.Example.domain.Category;
import hr.java.spring.boot.Example.domain.Hardware;
import hr.java.spring.boot.Example.domain.SearchHardware;
import hr.java.spring.boot.Example.dto.HardwareDTO;
import hr.java.spring.boot.Example.dto.SearchHardwareDTO;
import hr.java.spring.boot.Example.repository.HardwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    private HardwareRepository hardwareRepository;

    @Override
    public List<HardwareDTO> getAllHardware() {
        return hardwareRepository.getAllHardware().stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();
    }

    @Override
    public List<HardwareDTO> getHardwareByName(String hardwareName) {
        return hardwareRepository.getHardwareByName(hardwareName).stream()
                .map(this::convertHardwareToHardwareDTO)
                .toList();
    }

    @Override
    public Hardware saveNewHardware(HardwareDTO hardwareDTO) {
        return hardwareRepository.saveNewHardware(convertHardwareDtoToHardware(hardwareDTO));
    }

    @Override
    public List<HardwareDTO> filterByParameters(SearchHardwareDTO searchHardwareDTO) {
        return hardwareRepository.filterByParameters(
                        convertSearchHardwareDtoToSearchHardware(searchHardwareDTO))
                .stream().map(this::convertHardwareToHardwareDTO)
                .toList();
    }

    @Override
    public Optional<HardwareDTO> updateHardware(HardwareDTO hardwareDTO, Long id) {
        Optional<Hardware> updatedHardwareOptional =
                hardwareRepository.updateHardware(convertHardwareDtoToHardware(hardwareDTO), id);

        if(updatedHardwareOptional.isPresent()) {
            return Optional.of(convertHardwareToHardwareDTO(updatedHardwareOptional.get()));
        }

        return Optional.empty();
    }

    @Override
    public boolean hardwareByIdExists(Long id) {
        return hardwareRepository.hardwareByIdExists(id);
    }

    @Override
    public boolean deleteHardwareById(Long id) {
        return hardwareRepository.deleteHardwareById(id);
    }

    private HardwareDTO convertHardwareToHardwareDTO(Hardware hardware) {
        return new HardwareDTO(hardware.getName(),
                hardware.getDescription(), hardware.getPrice(),
                hardware.getCategory().getName());
    }

    private Hardware convertHardwareDtoToHardware(HardwareDTO hardwareDTO) {

        return new Hardware(
                null,
                hardwareDTO.getHardwareName(),
                hardwareDTO.getHardwareDescription(),
                hardwareDTO.getHardwarePrice(),
                Category.valueOf(hardwareDTO.getCategoryName().toUpperCase()));
    }

    private SearchHardware convertSearchHardwareDtoToSearchHardware(SearchHardwareDTO searchHardwareDTO) {
        return new SearchHardware(
                searchHardwareDTO.getHardwareName(),
                searchHardwareDTO.getHardwareDescription(),
                searchHardwareDTO.getLowerPrice(),
                searchHardwareDTO.getUpperPrice(),
                Category.valueOf(searchHardwareDTO.getCategoryName()));
    }
    
}
