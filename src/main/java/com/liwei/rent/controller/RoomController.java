package com.liwei.rent.controller;


import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.dto.RoomDTO;
import com.liwei.rent.service.IRoomService;
import com.liwei.rent.common.vo.RoomVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 房间表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-02-17
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private IRoomService roomService;

    @PostMapping(value = "/saveOrUpdateRoom")
    public Result<Void> addRoom(@RequestBody RoomVO roomVO){
        roomService.saveOrUpdateRoom(roomVO);
        return Result.ok();
    }

    @GetMapping(value = "/listRoomFromApartment")
    public Result<List<RoomDTO>> listRoomFromApartment(@RequestParam String apartmentId){
        return Result.build(roomService.listRoomFromApartment(apartmentId));
    }
}
