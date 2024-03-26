package com.liwei.rent.service;

import com.liwei.rent.common.dto.RoomDTO;
import com.liwei.rent.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwei.rent.common.vo.RoomVO;

import java.util.List;

/**
 * <p>
 * 房间表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
public interface IRoomService extends IService<Room> {
    void saveOrUpdateRoom(RoomVO roomVO);
    List<RoomDTO> listRoomFromApartment(String apartmentId);
}
