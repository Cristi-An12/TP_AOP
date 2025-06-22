package org.example.Gui;

import org.example.Model.*;
import org.example.Persistencia.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RadioCompetition {
    private JFrame frame;
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtId;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JComboBox<Concurso> comboBox;
    private JButton btnOk;

    // Servicio que maneja la lógica de negocio
    private ConcursoService concursoService;

    public RadioCompetition() {
        // Inyección de dependencias - creamos las implementaciones concretas
        ConcursoRepository concursoRepo = new ConcursoRepoTXT ("concursos.txt");
        InscripcionRepository inscripcionRepo = new InscripcionRepoTXT ("concursos.txt");
        this.concursoService = new ConcursoService(concursoRepo, inscripcionRepo);

        initializeUI();
        cargarConcursos();
    }

    private void initializeUI() {
        frame = new JFrame("Inscription to Competition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 451, 229);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);

        createFormElements();
        setupLayout();

        frame.setVisible(true);
    }

    private void createFormElements() {
        // Crear todos los componentes del formulario
        JLabel lblName = new JLabel("Nombre:");
        txtName = new JTextField(10);

        JLabel lblLastName = new JLabel("Apellido:");
        txtLastName = new JTextField(10);

        JLabel lblId = new JLabel("Dni:");
        txtId = new JTextField(10);

        JLabel lblPhone = new JLabel("Teléfono:");
        txtPhone = new JTextField(10);

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(10);

        JLabel lblCompetition = new JLabel("Concurso:");
        comboBox = new JComboBox<>();

        btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscribirPersona();
            }
        });

        // Layout simplificado para el ejemplo
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblName)
                                .addComponent(lblLastName)
                                .addComponent(lblId)
                                .addComponent(lblPhone)
                                .addComponent(lblEmail)
                                .addComponent(lblCompetition))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtName)
                                .addComponent(txtLastName)
                                .addComponent(txtId)
                                .addComponent(txtPhone)
                                .addComponent(txtEmail)
                                .addComponent(comboBox)
                                .addComponent(btnOk))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblName)
                                .addComponent(txtName))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblLastName)
                                .addComponent(txtLastName))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblId)
                                .addComponent(txtId))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPhone)
                                .addComponent(txtPhone))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmail)
                                .addComponent(txtEmail))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCompetition)
                                .addComponent(comboBox))
                        .addComponent(btnOk)
        );
    }

    private void setupLayout() {
        // El layout ya se configuró en createFormElements()
    }

    private void cargarConcursos() {
        comboBox.removeAllItems();
        comboBox.addItem(null); // Opción vacía

        List<Concurso> concursos = concursoService.obtenerConcursosDisponibles();
        for (Concurso concurso : concursos) {
            comboBox.addItem(concurso);
        }
    }

    private void inscribirPersona() {
        if (!validarFormulario()) {
            return;
        }

        try {
            btnOk.setEnabled(false);

            Concurso concursoSeleccionado = (Concurso) comboBox.getSelectedItem();

            concursoService.inscribirPersona(
                    txtName.getText().trim(),
                    txtLastName.getText().trim(),
                    txtId.getText().trim(),
                    txtPhone.getText().trim(),
                    txtEmail.getText().trim(),
                    concursoSeleccionado
            );

            JOptionPane.showMessageDialog(contentPane, "Inscripción guardada exitosamente!");
            limpiarFormulario();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(contentPane, "Error al guardar inscripción: " + e.getMessage());
        } finally {
            btnOk.setEnabled(true);
        }
    }

    private boolean validarFormulario() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "Nombre no puede ser vacío");
            return false;
        }

        if (txtLastName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "Apellido no puede ser vacío");
            return false;
        }

        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "DNI no puede ser vacío");
            return false;
        }

        if (!validarEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(contentPane, "Email debe ser válido");
            return false;
        }

        if (!validarTelefono(txtPhone.getText())) {
            JOptionPane.showMessageDialog(contentPane,
                    "El teléfono debe ingresarse de la siguiente forma: NNNN-NNNNNN");
            return false;
        }

        if (comboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(contentPane, "Debe elegir un Concurso");
            return false;
        }

        return true;
    }

    private boolean validarEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean validarTelefono(String telefono) {
        String regex = "\\d{4}-\\d{6}";
        return telefono.matches(regex);
    }

    private void limpiarFormulario() {
        txtName.setText("");
        txtLastName.setText("");
        txtId.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        comboBox.setSelectedIndex(0);
    }
}