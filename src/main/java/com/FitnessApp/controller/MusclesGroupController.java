package com.FitnessApp.controller;


import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.services.IEntityService;
import com.FitnessApp.utils.converters.MusclesGroupConverter;
import com.FitnessApp.utils.dtos.MusclesGroupDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(description = "This controller is used for edit 'Groups of muscles'", consumes = "application/json")
@RestController
public class MusclesGroupController {
    private final MusclesGroupConverter musclesGroupConverter;

    private final IEntityService<MusclesGroup> musclesGroupService;

    @Autowired
    public MusclesGroupController(MusclesGroupConverter musclesGroupConverter, IEntityService<MusclesGroup> musclesGroupService) {
        this.musclesGroupConverter = musclesGroupConverter;
        this.musclesGroupService = musclesGroupService;
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get list of muscles groups",
            nickname = "get list of muscles groups",
            response = MusclesGroupDTO.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/muscles-groups", method = RequestMethod.GET,
            produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getAllMusclesGroup() {
        Optional<List<MusclesGroup>> musclesGroups = musclesGroupService.getAll();
        return ResponseEntity.ok(musclesGroups.<Iterable<MusclesGroupDTO>>map(musclesGroups1 -> musclesGroups1
                .stream()
                .map(musclesGroupConverter::asMusclesGroupDTO)
                .collect(Collectors.toList())).orElse(null));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get by ID object 'Muscles group'",
            nickname = "get muscles group by id",
            response = MusclesGroupDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/muscles-groups/{id}", method = RequestMethod.GET,
            produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getMusclesGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(musclesGroupConverter
                .asMusclesGroupDTO(musclesGroupService.getByID(id)));
    }


    @ApiOperation(httpMethod = "POST",
            value = "Resource to add new object MusclesGroup",
            nickname = "add muscles group",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object musclesgroup has been added",
            response = String.class),
            @ApiResponse(code = 400, message = "Json object of UserProgram incorrect")})

    @RequestMapping(path = "/muscles-groups", method = RequestMethod.POST)
    public ResponseEntity addMusclesGroup(@RequestBody MusclesGroupDTO musclesGroupDTO) {
        MusclesGroup musclesGroup = musclesGroupConverter.asMusclesGroup(musclesGroupDTO);

        boolean res = musclesGroupService.save(musclesGroup);
        if (res) {
            return ResponseEntity.ok("Added");
        } else {
            return ResponseEntity.status(400).build();
        }
    }


    @ApiOperation(httpMethod = "PUT",
            value = "Resource to rename object MusclesGroup",
            nickname = "rename muscles group",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object musclesgroup has been renamed",
            response = String.class),
            @ApiResponse(code = 400, message = "Json object of MusclesGroup incorrect",
                    response = String.class)})

    @RequestMapping(path = "/muscles-groups/{id}", method = RequestMethod.PUT)
    public ResponseEntity renameMusclesGroup(@PathVariable Long id,
                                             @RequestBody MusclesGroupDTO musclesGroupDTO) {

        boolean res = musclesGroupService.update(id,
                musclesGroupConverter.asMusclesGroup(musclesGroupDTO));
        if (res) {
            return ResponseEntity.ok("Renamed");
        } else {
            return ResponseEntity.status(400).build();
        }
    }


    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete new object MusclesGroup",
            nickname = "delete muscles group",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object musclesgroup has been deleted",
            response = String.class)})

    @RequestMapping(path = "/muscles-groups/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMusclesGroup(@PathVariable Long id) {
        musclesGroupService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

}
