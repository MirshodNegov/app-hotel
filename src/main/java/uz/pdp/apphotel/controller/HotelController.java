package uz.pdp.apphotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apphotel.entity.Hotel;
import uz.pdp.apphotel.repository.HotelRepository;

import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel) {
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists)
            return "This hotel name already exist !";
        Hotel savedHotel = hotelRepository.save(hotel);
        return "Hotel saved ! ID=" + savedHotel.getId();
    }

    @GetMapping
    public Page<Hotel> getHotelPages(@RequestParam int page) {

        Pageable pageable = PageRequest.of(page, 3);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage;

    }

    @DeleteMapping("/{hotelId}")
    public String deleteHotel(@PathVariable Integer hotelId) {
        boolean exists = hotelRepository.existsById(hotelId);
        if (!exists)
            return "Hotel not found with given ID !";
        hotelRepository.deleteById(hotelId);
        return "Hotel deleted Successfully !";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel1 = optionalHotel.get();
            hotel1.setName(hotel.getName());
            return "Hotel edited !";
        }
        return "Hotel not found !";
    }

}
