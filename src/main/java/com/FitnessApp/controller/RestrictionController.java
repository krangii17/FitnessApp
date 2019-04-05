package com.FitnessApp.controller;


import com.FitnessApp.exceptions.RestrictionNotFoundException;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.services.RestrictionService;
import com.FitnessApp.utils.converters.RestrictionConverter;
import com.FitnessApp.utils.dtos.RestrictionDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RestrictionController {

    @Autowired
    private RestrictionConverter converter;

    @Autowired
    private RestrictionService service;

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get list of restrictions",
            nickname = "get list of muscles groups",
            response = RestrictionDTO.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "basicAuth")})
    @Transactional(readOnly = true)
    @GetMapping(value = "/restrictions")
    public ResponseEntity getRestrictionList() {
        Optional<List<Restriction>> restrictionsList = service.getAllRestrictions();
        return ResponseEntity.ok(restrictionsList.<Iterable<RestrictionDTO>>map(restrictionTemp -> restrictionTemp
                .stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList())).orElse(null));
    }

    @ApiOperation(httpMethod = "PUT",
            value = "Resource to change status of Restriction by ID",
            nickname = "rename UserProgram by ID")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Restriction status has been changed"),
            @ApiResponse(code = 404, message = "Page is not found"),
            @ApiResponse(code = 400, message = "Json object of Restriction is not right")})
    @PutMapping(value = "/changeRestrictionStatus")
    public ResponseEntity changeRestStatus(@RequestParam boolean status,
                                           @RequestParam Long id) throws RestrictionNotFoundException {
        boolean result = service.changeRestStatus(status, id);
        if (result) {
            return ResponseEntity.ok("state was changed");
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @ApiOperation(httpMethod = "PUT",
            value = "Resource to change evaluation of Restriction by ID",
            nickname = "rename UserProgram by ID")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Restriction evaluation has been changed"),
            @ApiResponse(code = 404, message = "Page is not found"),
            @ApiResponse(code = 400, message = "Json object of Restriction is not right")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "RestrictionDTO", value = "RestrictionDTO", required = true, dataType = "RestrictionDTO", paramType = "RestrictionDTO")
    })
    @PutMapping(value = "/changeRestrictionEvaluation")
    @Transactional(readOnly = true)
    public ResponseEntity changeEvaluation(@RequestParam Long id, @RequestBody RestrictionDTO restrictionDTO) throws RestrictionNotFoundException {
        boolean result = service.changeEvaluation(id, converter.convertToEntity(restrictionDTO));
        if (result) {
            return ResponseEntity.ok("evaluation was changed");
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
