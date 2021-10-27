package com.ttpsc.signingintheair.parcel.service;

import com.ttpsc.signingintheair.parcel.entity.CompletedDeliveryRequest;
import com.ttpsc.signingintheair.parcel.entity.Parcel;
import com.ttpsc.signingintheair.parcel.entity.ParcelStatus;
import com.ttpsc.signingintheair.parcel.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ParcelServiceImpl implements ParcelService {

    private ParcelRepository parcelRepository;

    @Autowired
    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public Parcel getParcel(UUID pid) {
        return parcelRepository.findById(pid).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Parcel> getAllNotDeliveredParcels() {
        return parcelRepository.getAllByStatusNotIn(Arrays.asList(ParcelStatus.COMPLETED));
    }

    @Override
    public List<Parcel> getOutForDeliveryParcels() {
        return parcelRepository.getAllByStatusIn(Arrays.asList(ParcelStatus.OUT_FOR_DELIVERY));
    }

    @Override
    public List<Parcel> getAllParcel() {
        return parcelRepository.findAll(Sort.by(Sort.Direction.DESC,"pid"));
    }

    @Override
    public Parcel addParcel(Parcel newParcel) {
        newParcel.setPid(UUID.randomUUID());
        newParcel.setStatus(ParcelStatus.LABEL_CREATED);
        return parcelRepository.save(newParcel);
    }

    @Override
    public Parcel deliverParcel(UUID pid) {
        Parcel deliveringParcel =
                parcelRepository.findById(pid).orElseThrow(EntityNotFoundException::new);
        deliveringParcel.setStatus(ParcelStatus.OUT_FOR_DELIVERY);
        return parcelRepository.save(deliveringParcel);
    }

    @Override
    public Parcel completeDelivery(
            CompletedDeliveryRequest completedDeliveryRequest) {

        Parcel completedParcel =
                parcelRepository.getOutForDeliveryParcelByPid(completedDeliveryRequest.getPid())
                                .orElseThrow(EntityNotFoundException::new);

        fillCompletedDeliveryInfo(completedDeliveryRequest, completedParcel);

        return parcelRepository.save(completedParcel);
    }

    @Override
    public Page<Parcel> getAll(Specification<Parcel> specification, Pageable pageable) {
        return parcelRepository.findAll(specification,pageable);
    }

    private void fillCompletedDeliveryInfo(
            CompletedDeliveryRequest completedDeliveryRequest,
            Parcel completingParcel) {

        if (completedDeliveryRequest.getPid().equals(completingParcel.getPid())) {
            completingParcel.setSigneePhoto(completedDeliveryRequest.getSigneePhoto().toString());
            completingParcel.setSigneeSignaturePhoto(
                    completedDeliveryRequest.getSigneeSignaturePhoto().toString());
            completingParcel.setStatus(ParcelStatus.COMPLETED);
        }
    }
}
