package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.dao.RoomMapper;
import com.liwei.rent.common.dto.RoomDTO;
import com.liwei.rent.entity.Room;
import com.liwei.rent.service.IRoomService;
import com.liwei.rent.common.vo.RoomBaseInfo;
import com.liwei.rent.common.vo.RoomVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
    private static final Logger logger = LoggerFactory.getLogger(IRoomService.class);

    @Override
    public void saveOrUpdateRoom(RoomVO roomVO){
        List<RoomBaseInfo> roomBaseInfoList = roomVO.getRoomBaseInfoList();
        if (CollectionUtils.isEmpty(roomBaseInfoList)){
            logger.info("房间列表为空");
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        roomBaseInfoList.forEach(roomBaseInfo -> {
            Room oldRoom = this.getById(roomBaseInfo.getId());
            if(oldRoom != null){
                BeanUtils.copyProperties(roomBaseInfo,oldRoom);
                oldRoom.setUpdateTime(now);
                this.saveOrUpdate(oldRoom);
            }else {
                Room room = new Room();
                BeanUtils.copyProperties(roomBaseInfo,room);
                room.setApartmentId(roomVO.getApartmentId());
                room.setApartmentName(roomVO.getApartmentName());
                room.setCreateTime(now);
                room.setUpdateTime(now);
                room.setDelFlag(DelFlagEnum.UN_DEL.value());
                this.save(room);
            }
        });
    }

    /**
     * 查询公寓下的所有房间
     * @param apartmentId
     * @return
     */
    @Override
    public List<RoomDTO> listRoomFromApartment(String apartmentId) {
        LambdaQueryWrapper<Room> cond = new LambdaQueryWrapper<>();
        cond.eq(Room::getApartmentId,apartmentId);
        cond.eq(Room::getDelFlag,DelFlagEnum.UN_DEL.value());
        List<Room> list = this.list(cond);
        List<RoomDTO> resultList = new ArrayList<>();
        list.forEach(room -> {
            RoomDTO roomDTO = new RoomDTO();
            BeanUtils.copyProperties(room,roomDTO);
            resultList.add(roomDTO);
        });
        return resultList;
    }
}
