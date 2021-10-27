package com.ttpsc.signingintheair.parcel.controller;

import com.ttpsc.signingintheair.parcel.entity.CompletedDeliveryRequest;
import com.ttpsc.signingintheair.parcel.entity.GridResponse;
import com.ttpsc.signingintheair.parcel.entity.Parcel;
import com.ttpsc.signingintheair.parcel.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/parcels")
public class ParcelController {

    private ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping("/out-for-delivery")
    public ResponseEntity<List<Parcel>> getOutForDeliveryParcels() {
        return ResponseEntity.ok(parcelService.getOutForDeliveryParcels());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parcel>> getAllDelivery(){
        return ResponseEntity.ok(parcelService.getAllParcel());
    }

    //負責導向jsp頁面
    @RequestMapping("/list")
    public String openquery1(Model model) {
        return "list";
    }

    //給jqgrid回傳資料
    @RequestMapping("/out-for-delivery1")
    @ResponseBody
    public GridResponse<Parcel> list(@RequestParam(name = "page",defaultValue = "1")Integer page,@RequestParam(name = "rows",defaultValue = "10")Integer size){
        GridResponse<Parcel> gridResponse = new GridResponse<>();
        Pageable pageable =  PageRequest.of(page-1,size);
        Specification<Parcel> specification= new Specification<Parcel>() {
            @Override
            public Predicate toPredicate(Root<Parcel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<Parcel> result =parcelService.getAll(specification,pageable);
        gridResponse.setPage(page);
        gridResponse.setTotal(result.getTotalPages());
        gridResponse.setRows(result.getContent());
        return  gridResponse;
    }

    @GetMapping("/not-delivered")
    public ResponseEntity<List<Parcel>> getNotDeliveredParcels() {
        return ResponseEntity.ok(parcelService.getAllNotDeliveredParcels());
    }

    @GetMapping("/{pid}")
    public ResponseEntity<Parcel> getParcel(@PathVariable UUID pid) {
        return ResponseEntity.ok(parcelService.getParcel(pid));
    }

    @PostMapping
    public ResponseEntity<Parcel> addNewParcel(@RequestBody Parcel newParcel) {
        return ResponseEntity.ok(parcelService.addParcel(newParcel));
    }

    @PatchMapping("/{pid}/out-for-delivery")
    public ResponseEntity<Parcel> deliverParcel(@PathVariable UUID pid) {
        return ResponseEntity.ok(parcelService.deliverParcel(pid));
    }

    @PatchMapping("/complete-delivery")
    public ResponseEntity<Parcel> completeDelivery(
            @RequestBody CompletedDeliveryRequest completedDeliveryRequest) {
        return ResponseEntity.ok(parcelService.completeDelivery(completedDeliveryRequest));
    }

}
