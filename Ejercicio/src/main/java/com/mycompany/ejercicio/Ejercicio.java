/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ejercicio {
    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}

class Jugador {
    private String nombre;
    private int puntaje;

    public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public String toString() {
        return nombre + " - " + puntaje;
    }
}

class Clasificador {
    private ArrayList<Jugador> jugadores;

    public Clasificador() {
        jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        ordenarJugadores();
    }

    public void ordenarJugadores() {
        jugadores.sort(Comparator.comparingInt(Jugador::getPuntaje));
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador buscarPorPuntaje(int puntaje) {
        int inicio = 0, fin = jugadores.size() - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int puntajeMedio = jugadores.get(medio).getPuntaje();

            if (puntajeMedio == puntaje) {
                return jugadores.get(medio);
            } else if (puntajeMedio < puntaje) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null; 
    }
}

class VentanaPrincipal extends JFrame {
    private Clasificador clasificador;
    private JTextArea areaJugadores;
    private JTextField campoNombre;
    private JTextField campoPuntaje;
    private JTextField campoBusqueda;

    public VentanaPrincipal() {
        clasificador = new Clasificador();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Clasificación de Jugadores");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2));
        panelSuperior.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelSuperior.add(campoNombre);
        panelSuperior.add(new JLabel("Puntaje:"));
        campoPuntaje = new JTextField();
        panelSuperior.add(campoPuntaje);
        JButton botonAgregar = new JButton("Agregar Jugador");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarJugador();
            }
        });
        panelSuperior.add(botonAgregar);
        areaJugadores = new JTextArea();
        areaJugadores.setEditable(false);
        JScrollPane scrollJugadores = new JScrollPane(areaJugadores);
        add(scrollJugadores, BorderLayout.CENTER);
        JPanel panelInferior = new JPanel(new GridLayout(1, 3));
        campoBusqueda = new JTextField();
        panelInferior.add(campoBusqueda);
        JButton botonBuscar = new JButton("Buscar por Puntaje");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarJugador();
            }
        });
        panelInferior.add(botonBuscar);
        add(panelSuperior, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void agregarJugador() {
        String nombre = campoNombre.getText();
        int puntaje;
        try {
            puntaje = Integer.parseInt(campoPuntaje.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Puntaje debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        clasificador.agregarJugador(new Jugador(nombre, puntaje));
        actualizarListaJugadores();
        campoNombre.setText("");
        campoPuntaje.setText("");
    }

    private void buscarJugador() {
        int puntaje;
        try {
            puntaje = Integer.parseInt(campoBusqueda.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Jugador encontrado = clasificador.buscarPorPuntaje(puntaje);
        if (encontrado != null) {
            JOptionPane.showMessageDialog(this, "Jugador encontrado: " + encontrado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un jugador con ese puntaje.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarListaJugadores() {
        StringBuilder sb = new StringBuilder("Jugadores:\n");
        for (Jugador jugador : clasificador.getJugadores()) {
            sb.append(jugador).append("\n");
        }
        areaJugadores.setText(sb.toString());
    }
}
