package com.riyasewana.api.service;

import com.riyasewana.api.model.Services;
import com.riyasewana.api.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleWorkerService {

    private final ServiceRepository serviceRepository;
    @Autowired
    public VehicleWorkerService(ServiceRepository serviceRepository){
        this.serviceRepository = serviceRepository;
    }

    public Services insertService(Services services){
        return serviceRepository.save(services);
    }

    public List<Services> getServices(){
       return serviceRepository.findAll();
    }
}
