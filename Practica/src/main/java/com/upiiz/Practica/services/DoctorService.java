package com.upiiz.Practica.services;

import com.upiiz.Practica.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private List<Doctor> doctores = new ArrayList<>();
    private int contadorId = 1;

    public List<Doctor> getAll() {
        return doctores;
    }

    public Doctor getById(int id) {
        return doctores.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void save(Doctor doctor) {
        doctor.setId(contadorId++);
        doctores.add(doctor);
    }

    public void update(Doctor doctor) {
        for (int i = 0; i < doctores.size(); i++) {
            if (doctores.get(i).getId() == doctor.getId()) {
                doctores.set(i, doctor);
                return;
            }
        }
    }

    public void delete(int id) {
        doctores.removeIf(d -> d.getId() == id);
    }
}
