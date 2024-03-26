package com.liwei.rent.controller;


import com.liwei.rent.common.dto.ApartmentDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.service.IApartmentService;
import com.liwei.rent.common.vo.ApartmentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公寓表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/apartment")
public class ApartmentController {
    private static final Logger logger = LoggerFactory.getLogger(ApartmentController.class);
    @Autowired
    private IApartmentService apartmentService;

    @PostMapping(value = "/saveOrUpdate")
    public Result<Void> saveOrUpdateApartment(@RequestBody ApartmentVO apartmentVO){
        apartmentService.saveOrUpdateApartment(apartmentVO);
        return Result.ok();
    }

    /**
     * 查询房东的所有公寓
     * @param userId
     * @return
     */
    @GetMapping(value = "/listApartmentByUserId")
    public Result<List<ApartmentDTO>> listApartment(@RequestParam String userId){
        List<ApartmentDTO> apartmentDTOList = apartmentService.listApartmentByUserId(userId);
        return Result.build(apartmentDTOList);
    }

    @DeleteMapping(value = "/del")
    public Result<Void> delApartment(@RequestParam Integer id){
        apartmentService.removeById(id);
        return Result.ok();
    }

}
