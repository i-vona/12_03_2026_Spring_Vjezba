package hr.java.spring.boot.Example.repository;

import hr.java.spring.boot.Example.domain.Category;
import hr.java.spring.boot.Example.domain.Hardware;
import hr.java.spring.boot.Example.domain.SearchHardware;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MockHardwareRepository implements HardwareRepository {

    private static List<Hardware> hardwareList;

    static {
        hardwareList = new ArrayList<>();

        Hardware firstHardware = new Hardware(1L, "Intel Core i9-14900K", "High-performance desktop processor", new BigDecimal(589), Category.CPU);
        Hardware secondHardware = new Hardware(2L, "NVIDIA GeForce RTX 4090", "Flagship gaming graphics card", new BigDecimal(1599), Category.GPU);
        Hardware thirdHardware = new Hardware(3L, "ASUS ROG Maximus Z790", "High-end Intel motherboard", new BigDecimal(699), Category.MBO);
        Hardware fourthHardware = new Hardware(4L, "Corsair Vengeance DDR5 32GB", "High-speed desktop memory kit", new BigDecimal(129), Category.RAM);
        Hardware fifthHardware = new Hardware(5L, "Samsung 990 Pro 2TB NVMe", "Fast PCIe 4.0 SSD", new BigDecimal(179), Category.STORAGE);
        Hardware sixthHardware = new Hardware(6L, "Corsair RM1000x PSU", "1000W modular power supply", new BigDecimal(189), Category.OTHER);

        hardwareList.add(firstHardware);
        hardwareList.add(secondHardware);
        hardwareList.add(thirdHardware);
        hardwareList.add(fourthHardware);
        hardwareList.add(fifthHardware);
        hardwareList.add(sixthHardware);
    }

    public List<Hardware> getAllHardware() {
        return hardwareList;
    }

    public List<Hardware> getHardwareByName(String hardwareName) {
        return hardwareList.stream()
                .filter(h -> h.getName().toLowerCase().contains(hardwareName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Hardware> getHardwareById(Long id) {
        return Optional.empty();
    }

    @Override
    public Hardware saveNewHardware(Hardware hardware) {
        hardwareList.add(hardware);
        return hardware;
    }

    @Override
    public List<Hardware> filterByParameters(SearchHardware searchHardware) {

        List<Hardware> hardware = getAllHardware();

        if(Optional.ofNullable(searchHardware.getId()).isPresent()) {
            hardware = hardware.stream()
                    .filter(a -> a.getId().equals(searchHardware.getId()))
                    .collect(Collectors.toList());
        }

        if(!Optional.ofNullable( searchHardware.getName()).isEmpty()) {
            hardware = hardware.stream()
                    .filter(a -> a.getName().contains(searchHardware.getName()))
                    .collect(Collectors.toList());
        }

        if(!Optional.of(searchHardware.getDescription()).isEmpty()) {
            hardware = hardware.stream()
                    .filter(a -> a.getDescription().contains(searchHardware.getDescription()))
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(searchHardware.getLowerPrice()).isPresent()) {
            hardware = hardware.stream()
                    .filter(a -> a.getPrice().compareTo(searchHardware.getLowerPrice()) >= 0)
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(searchHardware.getUpperPrice()).isPresent()) {
            hardware = hardware.stream()
                    .filter(a -> a.getPrice().compareTo(searchHardware.getUpperPrice()) <= 0)
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(searchHardware.getCategory()).isPresent()) {
            hardware = hardware.stream()
                    .filter(a -> a.getCategory().getId().equals(searchHardware.getCategory().getId()))
                    .collect(Collectors.toList());
        }


        return hardware;
    }

    @Override
    public Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Long id) {
        Optional<Hardware> storedHardwareOptional = hardwareList.stream().filter(h -> h.getId().equals(id)).findFirst();
        if(storedHardwareOptional.isPresent()) {
            Hardware storedHardware = storedHardwareOptional.get();
            storedHardware.setName(hardwareToUpdate.getName());
            storedHardware.setCategory(hardwareToUpdate.getCategory());
            storedHardware.setDescription(hardwareToUpdate.getDescription());
            storedHardware.setPrice(hardwareToUpdate.getPrice());

            return Optional.of(storedHardware);
        }

        return Optional.empty();
    }

    @Override
    public boolean hardwareByIdExists(Long id) {
        return hardwareList.stream()
                .anyMatch(h -> h.getId().equals(id));
    }

    @Override
    public boolean deleteHardwareById(Long id) {
        return hardwareList.removeIf(h -> h.getId().equals(id));
    }
}
