package uz.pdp.apphotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apphotel.entity.Hotel;
import uz.pdp.apphotel.entity.Room;
import uz.pdp.apphotel.payLoad.RoomDto;
import uz.pdp.apphotel.repository.HotelRepository;
import uz.pdp.apphotel.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;


    // Hotel ID orqali Room lar Pageni olish !!!
    @GetMapping("/getByHotelId/{hotelId}")
    public Page<Room> getRoomsPageByHotelId(@PathVariable Integer hotelId,@RequestParam int page){

        Pageable pageable= PageRequest.of(page,5);
        Page<Room> roomPage = roomRepository.findAllByHotel_Id(hotelId, pageable);
        return roomPage;

    }

    @GetMapping
    public List<Room> getRoomsList(){
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());

        if (!optionalHotel.isPresent())
            return "Hotel ID wrong !";
        Hotel hotel = optionalHotel.get();
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);
        return "Room saved ! ID=" + savedRoom.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        boolean exists = roomRepository.existsById(id);
        if (!exists)
            return "Room ID wrong !";
        roomRepository.deleteById(id);
        return "Room deleted !";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            room.setNumber(roomDto.getNumber());
            room.setFloor(roomDto.getFloor());
            room.setSize(roomDto.getSize());
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());

            if (!optionalHotel.isPresent())
                return "Hotel ID wrong !";
            Hotel hotel = optionalHotel.get();
            room.setHotel(hotel);
            Room savedRoom = roomRepository.save(room);
        }
        return "Room id wrong !";
    }
}
