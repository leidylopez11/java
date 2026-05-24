package com.quiz.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clubes")
public class Club {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String ciudad;
    private int anioFundacion;
    
    // RELACIÓN OneToOne con Entrenador (carga perezosa LAZY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;
    
    // RELACIÓN OneToMany con Jugador (con JoinColumn para evitar tabla intermedia)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    private List<Jugador> jugadores = new ArrayList<>();
    
    // RELACIÓN ManyToOne con Asociacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asociacion_id")
    private Asociacion asociacion;
    
    // RELACIÓN ManyToMany con Competicion
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "club_competicion",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "competicion_id")
    )
    private List<Competicion> competiciones = new ArrayList<>();
    
    // Constructor vacío
    public Club() {}
    
    // Constructor con parámetros básicos
    public Club(String nombre, String ciudad, int anioFundacion) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.anioFundacion = anioFundacion;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public int getAnioFundacion() { return anioFundacion; }
    public void setAnioFundacion(int anioFundacion) { this.anioFundacion = anioFundacion; }
    
    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }
    
    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }
    
    public Asociacion getAsociacion() { return asociacion; }
    public void setAsociacion(Asociacion asociacion) { this.asociacion = asociacion; }
    
    public List<Competicion> getCompeticiones() { return competiciones; }
    public void setCompeticiones(List<Competicion> competiciones) { this.competiciones = competiciones; }
    
    // Métodos helpers para agregar/remover jugadores
    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
    }
    
    public void removeJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }
    
    // Método helper para agregar competición
    public void addCompeticion(Competicion competicion) {
        competiciones.add(competicion);
    }
    
    @Override
    public String toString() {
        return nombre + " - " + ciudad;
    }
}