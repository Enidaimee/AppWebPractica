package com.upiiz.Practica.controllers;

import com.upiiz.Practica.models.Doctor;
import com.upiiz.Practica.services.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DoctorController {

    private final DoctorService doctorService;

    // 🔹 Usuarios en memoria
    private Map<String, String> usuarios = new HashMap<>();

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // -------------------- LOGIN --------------------
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        if (usuarios.containsKey(email) && usuarios.get(email).equals(password)) {
            return "redirect:/doctores";
        } else {
            model.addAttribute("error", "Email o contraseña incorrecta");
            return "login";
        }
    }

    // -------------------- REGISTRO --------------------
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "register";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String email,
                                   @RequestParam String password,
                                   Model model) {
        if (usuarios.containsKey(email)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }
        usuarios.put(email, password);
        return "redirect:/login";
    }

    // -------------------- DOCTORES --------------------
    @GetMapping("/doctores")
    public String listarDoctores(Model model) {
        model.addAttribute("doctores", doctorService.getAll());
        return "projects"; // tu HTML de listado
    }

    @GetMapping("/doctores/agregar")
    public String mostrarAgregar(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "project-add";
    }

    @PostMapping("/doctores/agregar")
    public String agregarDoctor(@ModelAttribute Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctores";
    }

    @GetMapping("/doctores/editar/{id}")
    public String mostrarEditar(@PathVariable int id, Model model) {
        Doctor doctor = doctorService.getById(id);
        model.addAttribute("doctor", doctor);
        return "project-edit";
    }

    @PostMapping("/doctores/editar")
    public String editarDoctor(@ModelAttribute Doctor doctor) {
        doctorService.update(doctor);
        return "redirect:/doctores";
    }

    // -------------------- ELIMINACIÓN --------------------
    @GetMapping("/doctores/eliminar/{id}")
    public String mostrarEliminar(@PathVariable int id, Model model) {
        Doctor doctor = doctorService.getById(id);
        model.addAttribute("doctor", doctor);
        return "project-delete"; // tu HTML de confirmación
    }

    @PostMapping("/doctores/eliminar")
    public String eliminarDoctor(@RequestParam int id) {
        doctorService.delete(id);
        return "redirect:/doctores";
    }
}
