package uz.pdp.apphotel.payLoad;

import lombok.Data;

@Data
public class RoomDto {

    private Integer id;
    private Integer number;
    private Integer floor;
    private Integer size;
    private Integer hotelId;

}
