package com.quiz.app.controller;

import com.quiz.app.model.*;
import com.quiz.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClubController {

    @Autowired private ClubRepository clubRepository;
    @Autowired private EntrenadorRepository entrenadorRepository;
    @Autowired private JugadorRepository jugadorRepository;
    @Autowired private AsociacionRepository asociacionRepository;
    @Autowired private CompeticionRepository competicionRepository;

    // ==================== PÁGINA PRINCIPAL ====================
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // ==================== CLUBES ====================
    @GetMapping("/clubes")
    public String listarClubes(Model model) {
        model.addAttribute("clubes", clubRepository.findAll());
        model.addAttribute("club", new Club());
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "clubes";
    }

    @PostMapping("/clubes/guardar")
    public String guardarClub(@ModelAttribute Club club) {
        clubRepository.save(club);
        return "redirect:/clubes";
    }

    @GetMapping("/clubes/editar/{id}")
    public String editarClub(@PathVariable Long id, Model model) {
        model.addAttribute("club", clubRepository.findById(id).orElse(null));
        model.addAttribute("clubes", clubRepository.findAll());
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "clubes";
    }

    @PostMapping("/clubes/actualizar/{id}")
    public String actualizarClub(@PathVariable Long id, @ModelAttribute Club club) {
        club.setId(id);
        clubRepository.save(club);
        return "redirect:/clubes";
    }

    @GetMapping("/clubes/eliminar/{id}")
    public String eliminarClub(@PathVariable Long id) {
        clubRepository.deleteById(id);
        return "redirect:/clubes";
    }

    @GetMapping("/clubes/ver/{id}")
    public String verClub(@PathVariable Long id, Model model) {
        System.out.println("=== VER CLUB ID: " + id + " ===");
        
        Club club = clubRepository.findById(id).orElse(null);
        System.out.println("Club encontrado: " + (club != null ? club.getNombre() : "null"));
        
        model.addAttribute("clubDetalle", club);
        model.addAttribute("clubes", clubRepository.findAll());
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        
        System.out.println("Entrenadores: " + entrenadorRepository.findAll().size());
        System.out.println("Jugadores: " + jugadorRepository.findAll().size());
        System.out.println("Asociaciones: " + asociacionRepository.findAll().size());
        
        return "club-detalle";
    }
    
    // Relaciones
    @GetMapping("/clubes/asignar-entrenador/{clubId}/{entrenadorId}")
    public String asignarEntrenador(@PathVariable Long clubId, @PathVariable Long entrenadorId) {
        Club club = clubRepository.findById(clubId).orElse(null);
        Entrenador entrenador = entrenadorRepository.findById(entrenadorId).orElse(null);
        if (club != null && entrenador != null) {
            club.setEntrenador(entrenador);
            clubRepository.save(club);
        }
        return "redirect:/clubes/ver/" + clubId;
    }

    @GetMapping("/clubes/asignar-asociacion/{clubId}/{asociacionId}")
    public String asignarAsociacion(@PathVariable Long clubId, @PathVariable Long asociacionId) {
        Club club = clubRepository.findById(clubId).orElse(null);
        Asociacion asociacion = asociacionRepository.findById(asociacionId).orElse(null);
        if (club != null && asociacion != null) {
            club.setAsociacion(asociacion);
            clubRepository.save(club);
        }
        return "redirect:/clubes/ver/" + clubId;
    }

    @GetMapping("/clubes/agregar-jugador/{clubId}/{jugadorId}")
    public String agregarJugador(@PathVariable Long clubId, @PathVariable Long jugadorId) {
        Club club = clubRepository.findById(clubId).orElse(null);
        Jugador jugador = jugadorRepository.findById(jugadorId).orElse(null);
        if (club != null && jugador != null) {
            club.getJugadores().add(jugador);
            clubRepository.save(club);
        }
        return "redirect:/clubes/ver/" + clubId;
    }

    @GetMapping("/clubes/remover-jugador/{clubId}/{jugadorId}")
    public String removerJugador(@PathVariable Long clubId, @PathVariable Long jugadorId) {
        Club club = clubRepository.findById(clubId).orElse(null);
        Jugador jugador = jugadorRepository.findById(jugadorId).orElse(null);
        if (club != null && jugador != null) {
            club.getJugadores().remove(jugador);
            clubRepository.save(club);
        }
        return "redirect:/clubes/ver/" + clubId;
    }

    @GetMapping("/clubes/agregar-competicion/{clubId}/{competicionId}")
    public String agregarCompeticion(@PathVariable Long clubId, @PathVariable Long competicionId) {
        Club club = clubRepository.findById(clubId).orElse(null);
        Competicion competicion = competicionRepository.findById(competicionId).orElse(null);
        if (club != null && competicion != null) {
            club.getCompeticiones().add(competicion);
            clubRepository.save(club);
        }
        return "redirect:/clubes/ver/" + clubId;
    }

    @GetMapping("/clubes/remover-competicion/{clubId}/{competicionId}")
    public String removerCompeticion(@PathVariable Long clubId, @PathVariable Long competicionId) {
        Club club = clubRepository.findById(clubId).orElse(null);
        Competicion competicion = competicionRepository.findById(competicionId).orElse(null);
        if (club != null && competicion != null) {
            club.getCompeticiones().remove(competicion);
            clubRepository.save(club);
        }
        return "redirect:/clubes/ver/" + clubId;
    }

    // ==================== ENTRENADORES ====================
    @GetMapping("/entrenadores")
    public String listarEntrenadores(Model model) {
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("entrenador", new Entrenador());
        return "entrenadores";
    }

    @PostMapping("/entrenadores/guardar")
    public String guardarEntrenador(@ModelAttribute Entrenador entrenador) {
        entrenadorRepository.save(entrenador);
        return "redirect:/entrenadores";
    }

    @GetMapping("/entrenadores/editar/{id}")
    public String editarEntrenador(@PathVariable Long id, Model model) {
        model.addAttribute("entrenador", entrenadorRepository.findById(id).orElse(null));
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        return "entrenadores";
    }

    @PostMapping("/entrenadores/actualizar/{id}")
    public String actualizarEntrenador(@PathVariable Long id, @ModelAttribute Entrenador entrenador) {
        entrenador.setId(id);
        entrenadorRepository.save(entrenador);
        return "redirect:/entrenadores";
    }

    @GetMapping("/entrenadores/eliminar/{id}")
    public String eliminarEntrenador(@PathVariable Long id) {
        entrenadorRepository.deleteById(id);
        return "redirect:/entrenadores";
    }

    // ==================== JUGADORES ====================
    @GetMapping("/jugadores")
    public String listarJugadores(Model model) {
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("jugador", new Jugador());
        return "jugadores";
    }

    @PostMapping("/jugadores/guardar")
    public String guardarJugador(@ModelAttribute Jugador jugador) {
        jugadorRepository.save(jugador);
        return "redirect:/jugadores";
    }

    @GetMapping("/jugadores/editar/{id}")
    public String editarJugador(@PathVariable Long id, Model model) {
        model.addAttribute("jugador", jugadorRepository.findById(id).orElse(null));
        model.addAttribute("jugadores", jugadorRepository.findAll());
        return "jugadores";
    }

    @PostMapping("/jugadores/actualizar/{id}")
    public String actualizarJugador(@PathVariable Long id, @ModelAttribute Jugador jugador) {
        jugador.setId(id);
        jugadorRepository.save(jugador);
        return "redirect:/jugadores";
    }

    @GetMapping("/jugadores/eliminar/{id}")
    public String eliminarJugador(@PathVariable Long id) {
        jugadorRepository.deleteById(id);
        return "redirect:/jugadores";
    }

    // ==================== ASOCIACIONES ====================
    @GetMapping("/asociaciones")
    public String listarAsociaciones(Model model) {
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("asociacion", new Asociacion());
        return "asociaciones";
    }

    @PostMapping("/asociaciones/guardar")
    public String guardarAsociacion(@ModelAttribute Asociacion asociacion) {
        asociacionRepository.save(asociacion);
        return "redirect:/asociaciones";
    }

    @GetMapping("/asociaciones/editar/{id}")
    public String editarAsociacion(@PathVariable Long id, Model model) {
        model.addAttribute("asociacion", asociacionRepository.findById(id).orElse(null));
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        return "asociaciones";
    }

    @PostMapping("/asociaciones/actualizar/{id}")
    public String actualizarAsociacion(@PathVariable Long id, @ModelAttribute Asociacion asociacion) {
        asociacion.setId(id);
        asociacionRepository.save(asociacion);
        return "redirect:/asociaciones";
    }

    @GetMapping("/asociaciones/eliminar/{id}")
    public String eliminarAsociacion(@PathVariable Long id) {
        asociacionRepository.deleteById(id);
        return "redirect:/asociaciones";
    }

    // ==================== COMPETICIONES ====================
    @GetMapping("/competiciones")
    public String listarCompeticiones(Model model) {
        model.addAttribute("competiciones", competicionRepository.findAll());
        model.addAttribute("competicion", new Competicion());
        return "competiciones";
    }

    @PostMapping("/competiciones/guardar")
    public String guardarCompeticion(@ModelAttribute Competicion competicion) {
        competicionRepository.save(competicion);
        return "redirect:/competiciones";
    }

    @GetMapping("/competiciones/editar/{id}")
    public String editarCompeticion(@PathVariable Long id, Model model) {
        model.addAttribute("competicion", competicionRepository.findById(id).orElse(null));
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "competiciones";
    }

    @PostMapping("/competiciones/actualizar/{id}")
    public String actualizarCompeticion(@PathVariable Long id, @ModelAttribute Competicion competicion) {
        competicion.setId(id);
        competicionRepository.save(competicion);
        return "redirect:/competiciones";
    }

    @GetMapping("/competiciones/eliminar/{id}")
    public String eliminarCompeticion(@PathVariable Long id) {
        competicionRepository.deleteById(id);
        return "redirect:/competiciones";
    }
}