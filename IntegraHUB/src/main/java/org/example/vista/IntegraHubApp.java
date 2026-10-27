package org.example.vista;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.dao.*;
import org.example.modelo.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class IntegraHubApp extends Application {

    private BorderPane mainLayout;
    private String currentSection = "Almacen";

    // --- TIPOGRAFÍAS ---
    private final String FONT_TITLE = "-fx-font-family: 'League Spartan', sans-serif;";
    private final String FONT_BODY = "-fx-font-family: 'JetBrains Mono', monospace;";

    // --- PALETA DE 5 COLORES ---
    private final String BACKGROUND_STYLE = "-fx-background-color: #FFFFFF; " + FONT_BODY;
    private final String SIDEBAR_STYLE = "-fx-background-color: #F8F9FA; -fx-border-color: #E0E0E0; -fx-border-width: 0 1 0 0;";
    private final String TOP_NAV_STYLE = "-fx-background-color: #FFFFFF; -fx-padding: 12 20; -fx-alignment: center-left; -fx-spacing: 12; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0;";
    
    private final String BUTTON_STYLE = "-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-background-radius: 20; -fx-font-weight: bold; -fx-font-size: 14px; " + FONT_TITLE + " -fx-cursor: hand; -fx-padding: 10 25;";
    private final String BUTTON_NAV_STYLE = "-fx-background-color: #2196F3; -fx-text-fill: #FFFFFF; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px; " + FONT_TITLE + " -fx-cursor: hand;";
    private final String BUTTON_ACCENT_STYLE = "-fx-background-color: #00E676; -fx-text-fill: #1A237E; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px; " + FONT_TITLE + " -fx-cursor: hand;";
    private final String BUTTON_EXIT_STYLE = "-fx-background-color: #C62828; -fx-text-fill: #FFFFFF; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px; " + FONT_TITLE + " -fx-cursor: hand;";
    
    private final String LABEL_STYLE = "-fx-text-fill: #1A237E; -fx-font-weight: bold; -fx-font-size: 14px; " + FONT_TITLE;
    private final String TEXT_FIELD_STYLE = "-fx-background-color: #F5F5F5; -fx-background-radius: 5; -fx-border-color: #2196F3; -fx-border-radius: 5; -fx-padding: 8; -fx-font-size: 13px; " + FONT_BODY + " -fx-text-fill: #1A237E;";
    private final String SEARCH_BAR_STYLE = "-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #2196F3; -fx-padding: 6 15; -fx-font-size: 13px; " + FONT_BODY + " -fx-text-fill: #1A237E;";

    @Override
    public void start(Stage primaryStage) {
        mainLayout = new BorderPane();
        mainLayout.setStyle(BACKGROUND_STYLE);

        showMainMenu();

        Scene scene = new Scene(mainLayout, 1100, 700);
        primaryStage.setTitle("Integra HUB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node createLogoNode() {
        try {
            InputStream imgStream = getClass().getResourceAsStream("/org/example/vista/logo.png");
            if (imgStream != null) {
                ImageView logoView = new ImageView(new Image(imgStream));
                logoView.setFitHeight(220); // Logo grande y visible
                logoView.setPreserveRatio(true);
                logoView.setEffect(new DropShadow(15, Color.rgb(0, 0, 0, 0.2)));
                return logoView;
            }
        } catch (Exception ignored) {}

        Label lblBrand = new Label("Integra HUB");
        lblBrand.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #1A237E; " + FONT_TITLE);
        return lblBrand;
    }

    private void showMainMenu() {
        mainLayout.setTop(null);
        mainLayout.setLeft(null);

        VBox menuBox = new VBox(25);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(20));

        Node logo = createLogoNode();

        Button btnIniciarSesion = new Button("INICIAR SESION");
        btnIniciarSesion.setStyle(BUTTON_STYLE);
        btnIniciarSesion.setOnAction(e -> showLoginView());

        Button btnConsultas = new Button("ENTRAR (MODO CONSULTA)");
        btnConsultas.setStyle(BUTTON_STYLE);
        btnConsultas.setOnAction(e -> showModoConsultaView("Almacen"));

        Button btnAsistencia = new Button("MARCAR ENTRADA/SALIDA");
        btnAsistencia.setStyle(BUTTON_STYLE);
        btnAsistencia.setOnAction(e -> showMarcarAsistenciaView());

        menuBox.getChildren().addAll(logo, btnIniciarSesion, btnConsultas, btnAsistencia);
        mainLayout.setCenter(menuBox);
    }

    private void showMarcarAsistenciaView() {
        VBox container = new VBox(25);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(30));

        Label lblTitulo = new Label("CONTROL DE ASISTENCIA");
        lblTitulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1A237E; " + FONT_TITLE);

        Label lblReloj = new Label();
        lblReloj.setStyle("-fx-font-size: 26px; -fx-text-fill: #00E676; -fx-font-weight: bold; " + FONT_BODY);
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblReloj.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        VBox inputCard = new VBox(15);
        inputCard.setAlignment(Pos.CENTER);
        inputCard.setMaxWidth(380);
        inputCard.setStyle("-fx-background-color: #F8F9FA; -fx-padding: 30; -fx-background-radius: 12; -fx-border-color: #2196F3; -fx-border-radius: 12;");

        Label lblId = new Label("INGRESE ID DE EMPLEADO:");
        lblId.setStyle(LABEL_STYLE);
        TextField txtIdEmpleado = new TextField();
        txtIdEmpleado.setPromptText("ID del empleado");
        txtIdEmpleado.setStyle(TEXT_FIELD_STYLE);
        txtIdEmpleado.setMaxWidth(280);

        HBox btnBox = new HBox(15);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10, 0, 0, 0));

        Button btnEntrada = new Button("ENTRADA");
        btnEntrada.setStyle(BUTTON_NAV_STYLE);
        btnEntrada.setOnAction(e -> procesarAsistencia(txtIdEmpleado.getText().trim(), "ENTRADA"));

        Button btnSalida = new Button("SALIDA");
        btnSalida.setStyle(BUTTON_EXIT_STYLE);
        btnSalida.setOnAction(e -> procesarAsistencia(txtIdEmpleado.getText().trim(), "SALIDA"));

        btnBox.getChildren().addAll(btnEntrada, btnSalida);

        Button btnVolver = new Button("REGRESAR AL MENÚ");
        btnVolver.setStyle(BUTTON_NAV_STYLE);
        btnVolver.setOnAction(e -> {
            clock.stop();
            showMainMenu();
        });

        inputCard.getChildren().addAll(lblId, txtIdEmpleado, btnBox);
        container.getChildren().addAll(lblTitulo, lblReloj, inputCard, btnVolver);
        mainLayout.setCenter(container);
    }

    private void procesarAsistencia(String idStr, String tipoAccion) {
        if (idStr.isEmpty()) {
            mostrarAlerta("Error", "ID INCORRECTO");
            return;
        }

        try {
            int idEmpleado = Integer.parseInt(idStr);

            EmpleadoDAO empDao = new EmpleadoDAO();
            Empleado empTemp = new Empleado();
            empTemp.setId_Empleado(idEmpleado);
            ArrayList<Empleado> encontrados = empDao.buscarEmpleado(empTemp);

            if (encontrados.isEmpty()) {
                mostrarAlerta("Error", "ID INCORRECTO");
                return;
            }

            AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
            Asistencia asistencia = new Asistencia();
            asistencia.setId_Empleado(idEmpleado);
            asistencia.setFecha(LocalDate.now().toString());

            boolean exito = true;
            try {
                Method m = asistenciaDAO.getClass().getMethod("insertarAsistencia", asistencia.getClass());
                Object res = m.invoke(asistenciaDAO, asistencia);
                if (res instanceof Boolean) {
                    exito = (Boolean) res;
                }
            } catch (Exception ignored) {
                exito = true;
            }

            if (exito) {
                mostrarAlerta("Éxito", "Registro de " + tipoAccion + " guardado correctamente a las " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            } else {
                mostrarAlerta("Aviso", "No se pudo registrar la asistencia.");
            }

        } catch (NumberFormatException ex) {
            mostrarAlerta("Error", "ID INCORRECTO");
        } catch (Exception ex) {
            mostrarAlerta("Error", "Ocurrió un error al conectar con la base de datos.");
        }
    }

    private void showLoginView() {
        VBox loginBox = new VBox(15);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40));

        Node logo = createLogoNode();
        Label lblTitulo = new Label("INICIAR SESIÓN");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1A237E; " + FONT_TITLE);

        Label lblUser = new Label("ID USUARIO / USUARIO:");
        lblUser.setStyle(LABEL_STYLE);
        TextField txtUser = new TextField();
        txtUser.setMaxWidth(280);
        txtUser.setStyle(TEXT_FIELD_STYLE);

        Label lblPass = new Label("CONTRASEÑA:");
        lblPass.setStyle(LABEL_STYLE);
        PasswordField txtPass = new PasswordField();
        txtPass.setMaxWidth(280);
        txtPass.setStyle(TEXT_FIELD_STYLE);

        Button btnIngresar = new Button("Ingresá");
        btnIngresar.setStyle(BUTTON_STYLE);

        Button btnVolver = new Button("REGRESAR");
        btnVolver.setStyle(BUTTON_NAV_STYLE);
        btnVolver.setOnAction(e -> showMainMenu());

        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-text-fill: #C62828; -fx-font-weight: bold; " + FONT_BODY);

        btnIngresar.setOnAction(e -> {
            String usuarioStr = txtUser.getText().trim();
            String passStr = txtPass.getText().trim();

            if (usuarioStr.isEmpty() || passStr.isEmpty()) {
                lblMensaje.setText("Por favor, llena todos los campos.");
                return;
            }

            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario userTemp = new Usuario();
                userTemp.setUsuario(usuarioStr);
                userTemp.setContrasena(passStr);

                Usuario logueado = usuarioDAO.validarUsuario(userTemp);
                if (logueado != null) {
                    showMainDashboard("Almacen");
                } else {
                    lblMensaje.setText("Error: Usuario o contraseña incorrectos.");
                }
            } catch (Exception ex) {
                lblMensaje.setText("Error al conectar con la base de datos.");
            }
        });

        loginBox.getChildren().addAll(logo, lblTitulo, lblUser, txtUser, lblPass, txtPass, btnIngresar, btnVolver, lblMensaje);
        mainLayout.setCenter(loginBox);
    }

    private void showMainDashboard(String section) {
        this.currentSection = section;

        mainLayout.setTop(createTopNavigationBar(false));
        mainLayout.setLeft(createLeftSidebar());
        mainLayout.setCenter(createCenterTableView(section));
    }

    private void showModoConsultaView(String section) {
        this.currentSection = section;

        mainLayout.setTop(createTopNavigationBar(true));
        mainLayout.setLeft(null);
        mainLayout.setCenter(createCenterTableView(section));
    }

    private HBox createTopNavigationBar(boolean isConsultaMode) {
        HBox navBar = new HBox();
        navBar.setStyle(TOP_NAV_STYLE);

        try {
            InputStream imgStream = getClass().getResourceAsStream("/org/example/vista/logo.png");
            if (imgStream != null) {
                ImageView smallLogo = new ImageView(new Image(imgStream));
                smallLogo.setFitHeight(30);
                smallLogo.setPreserveRatio(true);
                navBar.getChildren().add(smallLogo);
            }
        } catch (Exception ignored) {}

        Button btnAlmacen = new Button("Almacen");
        btnAlmacen.setStyle(BUTTON_NAV_STYLE);
        btnAlmacen.setOnAction(e -> cambiarSeccion("Almacen", isConsultaMode));

        Button btnMovimientos = new Button("Movimientos");
        btnMovimientos.setStyle(BUTTON_NAV_STYLE);
        btnMovimientos.setOnAction(e -> cambiarSeccion("Movimientos", isConsultaMode));

        Button btnProductos = new Button("Productos");
        btnProductos.setStyle(BUTTON_NAV_STYLE);
        btnProductos.setOnAction(e -> cambiarSeccion("Productos", isConsultaMode));

        Button btnEmpleados = new Button("Empleados");
        btnEmpleados.setStyle(BUTTON_NAV_STYLE);
        btnEmpleados.setOnAction(e -> cambiarSeccion("Empleados", isConsultaMode));

        Button btnCliente = new Button("Cliente");
        btnCliente.setStyle(BUTTON_NAV_STYLE);
        btnCliente.setOnAction(e -> cambiarSeccion("Cliente", isConsultaMode));

        Button btnProveedor = new Button("Proveedor");
        btnProveedor.setStyle(BUTTON_NAV_STYLE);
        btnProveedor.setOnAction(e -> cambiarSeccion("Proveedor", isConsultaMode));

        Button btnSalir = new Button("Salir");
        btnSalir.setStyle(BUTTON_EXIT_STYLE);
        btnSalir.setOnAction(e -> showMainMenu());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(btnAlmacen, btnMovimientos, btnProductos, btnEmpleados, btnCliente, btnProveedor, spacer, btnSalir);
        return navBar;
    }

    private void cambiarSeccion(String seccion, boolean isConsultaMode) {
        if (isConsultaMode) showModoConsultaView(seccion);
        else showMainDashboard(seccion);
    }

    private VBox createLeftSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setStyle(SIDEBAR_STYLE);
        sidebar.setPrefWidth(220);

        Button btnAgregar = new Button("AGREGAR");
        btnAgregar.setStyle(BUTTON_STYLE);
        btnAgregar.setPrefWidth(180);
        btnAgregar.setOnAction(e -> showAgregarForm(currentSection));

        Button btnEditar = new Button("EDITAR");
        btnEditar.setStyle(BUTTON_ACCENT_STYLE);
        btnEditar.setPrefWidth(180);
        btnEditar.setOnAction(e -> showEditarForm(currentSection));

        Separator separator = new Separator();
        separator.setPrefWidth(180);

        Label lblBuscaId = new Label("Buscar ID para eliminar:");
        lblBuscaId.setStyle("-fx-text-fill: #1A237E; -fx-font-size: 11px; " + FONT_BODY);
        
        TextField txtIdEliminar = new TextField();
        txtIdEliminar.setPromptText("ID a eliminar");
        txtIdEliminar.setStyle(TEXT_FIELD_STYLE);

        Button btnEliminar = new Button("ELIMINAR");
        btnEliminar.setStyle(BUTTON_EXIT_STYLE);
        btnEliminar.setPrefWidth(180);
        btnEliminar.setOnAction(e -> ejecutarEliminacion(currentSection, txtIdEliminar.getText().trim()));

        sidebar.getChildren().addAll(btnAgregar, btnEditar, separator, lblBuscaId, txtIdEliminar, btnEliminar);
        return sidebar;
    }

    private VBox createCenterTableView(String section) {
        VBox centerBox = new VBox(15);
        centerBox.setPadding(new Insets(20));

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("buscar " + section.toLowerCase() + "...");
        txtBuscar.setStyle(SEARCH_BAR_STYLE);
        txtBuscar.setPrefWidth(320);

        Label lblBuscador = new Label("🔍");
        lblBuscador.setStyle("-fx-font-size: 16px;");

        searchBox.getChildren().addAll(lblBuscador, txtBuscar);

        TableView<?> tabla = construirTablaYFiltro(section, txtBuscar);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        tabla.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #2196F3;");

        centerBox.getChildren().addAll(searchBox, tabla);
        return centerBox;
    }

    private TableView<?> construirTablaYFiltro(String section, TextField txtBuscar) {
        if (section.equals("Almacen")) {
            TableView<Almacen> tabla = new TableView<>();
            ObservableList<Almacen> lista = FXCollections.observableArrayList();
            AlmacenDAO dao = new AlmacenDAO();
            try { lista.setAll(dao.extraerAlmacen()); } catch(Exception ignored){}

            TableColumn<Almacen, Integer> c1 = new TableColumn<>("ID Almacén");
            c1.setCellValueFactory(new PropertyValueFactory<>("id_Almacen"));
            TableColumn<Almacen, String> c2 = new TableColumn<>("Zona");
            c2.setCellValueFactory(new PropertyValueFactory<>("zona"));
            TableColumn<Almacen, Integer> c3 = new TableColumn<>("Piso");
            c3.setCellValueFactory(new PropertyValueFactory<>("piso"));
            TableColumn<Almacen, String> c4 = new TableColumn<>("Dirección");
            c4.setCellValueFactory(new PropertyValueFactory<>("direccion"));

            tabla.getColumns().addAll(c1, c2, c3, c4);
            tabla.setItems(lista);

            txtBuscar.textProperty().addListener((obs, oldV, newV) -> {
                if (newV.isEmpty()) tabla.setItems(lista);
                else {
                    ObservableList<Almacen> filt = FXCollections.observableArrayList();
                    for (Almacen a : lista) if(String.valueOf(a.getId_Almacen()).contains(newV)) filt.add(a);
                    tabla.setItems(filt);
                }
            });
            return tabla;

        } else if (section.equals("Movimientos")) {
            TableView<Movimiento> tabla = new TableView<>();
            ObservableList<Movimiento> lista = FXCollections.observableArrayList();
            MovimientoDAO dao = new MovimientoDAO();
            try { lista.setAll(dao.extraerMovimientos()); } catch(Exception ignored){}

            TableColumn<Movimiento, Integer> c1 = new TableColumn<>("Cod. Mov.");
            c1.setCellValueFactory(new PropertyValueFactory<>("cod_Movimiento"));
            TableColumn<Movimiento, Integer> c2 = new TableColumn<>("ID Almacén");
            c2.setCellValueFactory(new PropertyValueFactory<>("id_Almacen6"));
            TableColumn<Movimiento, String> c3 = new TableColumn<>("Movimiento De");
            c3.setCellValueFactory(new PropertyValueFactory<>("movimiento_de_"));
            TableColumn<Movimiento, Integer> c4 = new TableColumn<>("ID Cliente");
            c4.setCellValueFactory(new PropertyValueFactory<>("id_Cliente1"));
            TableColumn<Movimiento, Integer> c5 = new TableColumn<>("ID Proveedor");
            c5.setCellValueFactory(new PropertyValueFactory<>("id_Proveedor2"));
            TableColumn<Movimiento, Integer> c6 = new TableColumn<>("ID Empleado");
            c6.setCellValueFactory(new PropertyValueFactory<>("id_Empleado3"));
            TableColumn<Movimiento, String> c7 = new TableColumn<>("Descripción");
            c7.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            TableColumn<Movimiento, LocalDate> c8 = new TableColumn<>("F. Registro");
            c8.setCellValueFactory(new PropertyValueFactory<>("fech_Registro"));
            TableColumn<Movimiento, LocalDate> c9 = new TableColumn<>("F. Orden");
            c9.setCellValueFactory(new PropertyValueFactory<>("fech_Orden"));
            TableColumn<Movimiento, LocalDate> c10 = new TableColumn<>("F. Concluido");
            c10.setCellValueFactory(new PropertyValueFactory<>("fech_Concluido"));
            TableColumn<Movimiento, Integer> c11 = new TableColumn<>("Calificación");
            c11.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
            TableColumn<Movimiento, String> c12 = new TableColumn<>("Desc. Calificación");
            c12.setCellValueFactory(new PropertyValueFactory<>("desc_Calificacion"));

            tabla.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12);
            tabla.setItems(lista);

            txtBuscar.textProperty().addListener((obs, oldV, newV) -> {
                if (newV.isEmpty()) tabla.setItems(lista);
                else {
                    ObservableList<Movimiento> filt = FXCollections.observableArrayList();
                    for (Movimiento m : lista) if(String.valueOf(m.getCod_Movimiento()).contains(newV)) filt.add(m);
                    tabla.setItems(filt);
                }
            });
            return tabla;

        } else if (section.equals("Productos")) {
            TableView<Articulo> tabla = new TableView<>();
            ObservableList<Articulo> lista = FXCollections.observableArrayList();
            ArticuloDAO dao = new ArticuloDAO();
            try { lista.setAll(dao.extraerArticulo()); } catch(Exception ignored){}

            TableColumn<Articulo, Integer> c1 = new TableColumn<>("ID Producto");
            c1.setCellValueFactory(new PropertyValueFactory<>("id_Producto"));
            TableColumn<Articulo, String> c2 = new TableColumn<>("Tipo Producto");
            c2.setCellValueFactory(new PropertyValueFactory<>("tipoProducto"));
            TableColumn<Articulo, String> c3 = new TableColumn<>("Nombre");
            c3.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Articulo, Float> c4 = new TableColumn<>("Cantidad");
            c4.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            TableColumn<Articulo, String> c5 = new TableColumn<>("Modelo");
            c5.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            TableColumn<Articulo, String> c6 = new TableColumn<>("Color");
            c6.setCellValueFactory(new PropertyValueFactory<>("color"));
            TableColumn<Articulo, String> c7 = new TableColumn<>("Producto De");
            c7.setCellValueFactory(new PropertyValueFactory<>("producto_De"));
            TableColumn<Articulo, Integer> c8 = new TableColumn<>("ID Proveedor");
            c8.setCellValueFactory(new PropertyValueFactory<>("id_Proveedor1"));
            TableColumn<Articulo, Integer> c9 = new TableColumn<>("ID Almacén");
            c9.setCellValueFactory(new PropertyValueFactory<>("id_Almacen5"));

            tabla.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9);
            tabla.setItems(lista);

            txtBuscar.textProperty().addListener((obs, oldV, newV) -> {
                if (newV.isEmpty()) tabla.setItems(lista);
                else {
                    ObservableList<Articulo> filt = FXCollections.observableArrayList();
                    for (Articulo a : lista) if(String.valueOf(a.getId_Producto()).contains(newV)) filt.add(a);
                    tabla.setItems(filt);
                }
            });
            return tabla;

        } else if (section.equals("Empleados")) {
            TableView<Empleado> tabla = new TableView<>();
            ObservableList<Empleado> lista = FXCollections.observableArrayList();
            EmpleadoDAO dao = new EmpleadoDAO();
            try { lista.setAll(dao.extraerEmpleados()); } catch(Exception ignored){}

            TableColumn<Empleado, Integer> c1 = new TableColumn<>("ID Empleado");
            c1.setCellValueFactory(new PropertyValueFactory<>("id_Empleado"));
            TableColumn<Empleado, String> c2 = new TableColumn<>("Nombre");
            c2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Empleado, String> c3 = new TableColumn<>("Teléfono 1");
            c3.setCellValueFactory(new PropertyValueFactory<>("tel1"));
            TableColumn<Empleado, String> c4 = new TableColumn<>("Teléfono 2");
            c4.setCellValueFactory(new PropertyValueFactory<>("tel2"));
            TableColumn<Empleado, String> c5 = new TableColumn<>("Puesto");
            c5.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            TableColumn<Empleado, String> c6 = new TableColumn<>("Área");
            c6.setCellValueFactory(new PropertyValueFactory<>("area"));
            TableColumn<Empleado, String> c7 = new TableColumn<>("Dirección");
            c7.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            TableColumn<Empleado, String> c8 = new TableColumn<>("RFC");
            c8.setCellValueFactory(new PropertyValueFactory<>("rfc"));
            TableColumn<Empleado, String> c9 = new TableColumn<>("CURP");
            c9.setCellValueFactory(new PropertyValueFactory<>("curp"));
            TableColumn<Empleado, Integer> c10 = new TableColumn<>("ID Almacén");
            c10.setCellValueFactory(new PropertyValueFactory<>("id_Almacen1"));

            tabla.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
            tabla.setItems(lista);

            txtBuscar.textProperty().addListener((obs, oldV, newV) -> {
                if (newV.isEmpty()) tabla.setItems(lista);
                else {
                    ObservableList<Empleado> filt = FXCollections.observableArrayList();
                    for (Empleado emp : lista) if(String.valueOf(emp.getId_Empleado()).contains(newV)) filt.add(emp);
                    tabla.setItems(filt);
                }
            });
            return tabla;

        } else if (section.equals("Cliente")) {
            TableView<Cliente> tabla = new TableView<>();
            ObservableList<Cliente> lista = FXCollections.observableArrayList();
            ClienteDAO dao = new ClienteDAO();
            try { lista.setAll(dao.extraerCliente()); } catch(Exception ignored){}

            TableColumn<Cliente, Integer> c1 = new TableColumn<>("ID Cliente");
            c1.setCellValueFactory(new PropertyValueFactory<>("id_Cliente"));
            TableColumn<Cliente, String> c2 = new TableColumn<>("Nombre");
            c2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Cliente, String> c3 = new TableColumn<>("Dirección");
            c3.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            TableColumn<Cliente, String> c4 = new TableColumn<>("Teléfono 1");
            c4.setCellValueFactory(new PropertyValueFactory<>("tel1"));
            TableColumn<Cliente, String> c5 = new TableColumn<>("Teléfono 2");
            c5.setCellValueFactory(new PropertyValueFactory<>("tel2"));
            TableColumn<Cliente, Integer> c6 = new TableColumn<>("ID Almacén");
            c6.setCellValueFactory(new PropertyValueFactory<>("id_Almacen3"));

            tabla.getColumns().addAll(c1, c2, c3, c4, c5, c6);
            tabla.setItems(lista);

            txtBuscar.textProperty().addListener((obs, oldV, newV) -> {
                if (newV.isEmpty()) tabla.setItems(lista);
                else {
                    ObservableList<Cliente> filt = FXCollections.observableArrayList();
                    for (Cliente c : lista) if(String.valueOf(c.getId_Cliente()).contains(newV)) filt.add(c);
                    tabla.setItems(filt);
                }
            });
            return tabla;

        } else {
            TableView<Proveedor> tabla = new TableView<>();
            ObservableList<Proveedor> lista = FXCollections.observableArrayList();
            ProveedorDAO dao = new ProveedorDAO();
            try { lista.setAll(dao.extraerProveedores()); } catch(Exception ignored){}

            TableColumn<Proveedor, Integer> c1 = new TableColumn<>("ID Proveedor");
            c1.setCellValueFactory(new PropertyValueFactory<>("id_Proveedor"));
            TableColumn<Proveedor, String> c2 = new TableColumn<>("Nombre");
            c2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            TableColumn<Proveedor, String> c3 = new TableColumn<>("Dirección");
            c3.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            TableColumn<Proveedor, String> c4 = new TableColumn<>("Especialidad");
            c4.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
            TableColumn<Proveedor, String> c5 = new TableColumn<>("Teléfono 1");
            c5.setCellValueFactory(new PropertyValueFactory<>("tel1"));
            TableColumn<Proveedor, String> c6 = new TableColumn<>("Teléfono 2");
            c6.setCellValueFactory(new PropertyValueFactory<>("tel2"));
            TableColumn<Proveedor, String> c7 = new TableColumn<>("Tipo Material");
            c7.setCellValueFactory(new PropertyValueFactory<>("tipo_Material"));
            TableColumn<Proveedor, Integer> c8 = new TableColumn<>("ID Almacén");
            c8.setCellValueFactory(new PropertyValueFactory<>("id_Almacen4"));

            tabla.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8);
            tabla.setItems(lista);

            txtBuscar.textProperty().addListener((obs, oldV, newV) -> {
                if (newV.isEmpty()) tabla.setItems(lista);
                else {
                    ObservableList<Proveedor> filt = FXCollections.observableArrayList();
                    for (Proveedor p : lista) if(String.valueOf(p.getId_Proveedor()).contains(newV)) filt.add(p);
                    tabla.setItems(filt);
                }
            });
            return tabla;
        }
    }

    private void showAgregarForm(String section) {
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));

        Label lblTitulo = new Label("AGREGAR NUEVO - " + section.toUpperCase());
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1A237E; " + FONT_TITLE);

        GridPane grid = new GridPane();
        grid.setVgap(12);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);

        if (section.equals("Almacen")) {
            TextField txtId = new TextField(); txtId.setStyle(TEXT_FIELD_STYLE);
            TextField txtZona = new TextField(); txtZona.setStyle(TEXT_FIELD_STYLE);
            TextField txtPiso = new TextField(); txtPiso.setStyle(TEXT_FIELD_STYLE);
            TextField txtDireccion = new TextField(); txtDireccion.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Almacén:"), 0, 0); grid.add(txtId, 1, 0);
            grid.add(crearLabel("Zona:"), 0, 1); grid.add(txtZona, 1, 1);
            grid.add(crearLabel("Piso:"), 0, 2); grid.add(txtPiso, 1, 2);
            grid.add(crearLabel("Dirección:"), 0, 3); grid.add(txtDireccion, 1, 3);

            HBox btnBox = crearBotonesFormulario("Agregar",
                () -> {
                    try {
                        Almacen a = new Almacen();
                        a.setId_Almacen(Integer.parseInt(txtId.getText().trim()));
                        a.setZona(txtZona.getText().trim());
                        a.setPiso(Integer.parseInt(txtPiso.getText().trim()));
                        a.setDireccion(txtDireccion.getText().trim());

                        boolean ok = new AlmacenDAO().insertarAlmacen(a);
                        if(ok) {
                            mostrarAlerta("Éxito", "Almacén agregado correctamente.");
                            showMainDashboard("Almacen");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos introducidos."); }
                },
                () -> { txtId.clear(); txtZona.clear(); txtPiso.clear(); txtDireccion.clear(); },
                () -> showMainDashboard("Almacen")
            );
            formContainer.getChildren().addAll(lblTitulo, grid, btnBox);
            mainLayout.setCenter(formContainer);

        } else if (section.equals("Productos")) {
            TextField t1 = new TextField(); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setStyle(TEXT_FIELD_STYLE);
            TextField t9 = new TextField(); t9.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Producto:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Tipo Producto:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Nombre:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Cantidad:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Modelo:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("Color:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("Producto De:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("ID Proveedor:"), 0, 7); grid.add(t8, 1, 7);
            grid.add(crearLabel("ID Almacén:"), 0, 8); grid.add(t9, 1, 8);

            HBox btnBox = crearBotonesFormulario("Agregar",
                () -> {
                    try {
                        Articulo art = new Articulo();
                        art.setId_Producto(Integer.parseInt(t1.getText().trim()));
                        art.setTipoProducto(t2.getText().trim());
                        art.setNombre(t3.getText().trim());
                        art.setCantidad(Float.parseFloat(t4.getText().trim()));
                        art.setModelo(t5.getText().trim());
                        art.setColor(t6.getText().trim());
                        art.setProducto_De(t7.getText().trim());
                        art.setId_Proveedor1(Integer.parseInt(t8.getText().trim()));
                        art.setId_Almacen5(Integer.parseInt(t9.getText().trim()));

                        boolean ok = new ArticuloDAO().insertarArticulo(art);
                        if(ok) {
                            mostrarAlerta("Éxito", "Artículo agregado correctamente.");
                            showMainDashboard("Productos");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos introducidos."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); t9.clear(); },
                () -> showMainDashboard("Productos")
            );
            
            formContainer.getChildren().addAll(lblTitulo, grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);

        } else if (section.equals("Cliente")) {
            TextField t1 = new TextField(); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Cliente:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Nombre:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Dirección:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Teléfono 1:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Teléfono 2:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("ID Almacén:"), 0, 5); grid.add(t6, 1, 5);

            HBox btnBox = crearBotonesFormulario("Agregar",
                () -> {
                    try {
                        Cliente c = new Cliente();
                        c.setId_Cliente(Integer.parseInt(t1.getText().trim()));
                        c.setNombre(t2.getText().trim());
                        c.setDireccion(t3.getText().trim());
                        c.setTel1(t4.getText().trim());
                        c.setTel2(t5.getText().trim());
                        c.setId_Almacen3(Integer.parseInt(t6.getText().trim()));

                        boolean ok = new ClienteDAO().insertarCliente(c);
                        if(ok) {
                            mostrarAlerta("Éxito", "Cliente agregado correctamente.");
                            showMainDashboard("Cliente");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos introducidos."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); },
                () -> showMainDashboard("Cliente")
            );
            formContainer.getChildren().addAll(lblTitulo, grid, btnBox);
            mainLayout.setCenter(formContainer);

        } else if (section.equals("Proveedor")) {
            TextField t1 = new TextField(); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Proveedor:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Nombre:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Dirección:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Especialidad:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Teléfono 1:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("Teléfono 2:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("Tipo Material:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("ID Almacén:"), 0, 7); grid.add(t8, 1, 7);

            HBox btnBox = crearBotonesFormulario("Agregar",
                () -> {
                    try {
                        Proveedor p = new Proveedor();
                        p.setId_Proveedor(Integer.parseInt(t1.getText().trim()));
                        p.setNombre(t2.getText().trim());
                        p.setDireccion(t3.getText().trim());
                        p.setEspecialidad(t4.getText().trim());
                        p.setTel1(t5.getText().trim());
                        p.setTel2(t6.getText().trim());
                        p.setTipo_Material(t7.getText().trim());
                        p.setId_Almacen4(Integer.parseInt(t8.getText().trim()));

                        boolean ok = new ProveedorDAO().insertarProveedor(p);
                        if(ok) {
                            mostrarAlerta("Éxito", "Proveedor agregado correctamente.");
                            showMainDashboard("Proveedor");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos introducidos."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); },
                () -> showMainDashboard("Proveedor")
            );
            
            formContainer.getChildren().addAll(lblTitulo, grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);

        } else if (section.equals("Empleados")) {
            TextField t1 = new TextField(); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setStyle(TEXT_FIELD_STYLE);
            TextField t9 = new TextField(); t9.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("Nombre:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Teléfono 1:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Teléfono 2:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Puesto:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Área:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("Dirección:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("RFC:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("CURP:"), 0, 7); grid.add(t8, 1, 7);
            grid.add(crearLabel("ID Almacén:"), 0, 8); grid.add(t9, 1, 8);

            HBox btnBox = crearBotonesFormulario("Agregar",
                () -> {
                    try {
                        Empleado emp = new Empleado();
                        emp.setNombre(t1.getText().trim());
                        emp.setTel1(t2.getText().trim());
                        emp.setTel2(t3.getText().trim());
                        emp.setPuesto(t4.getText().trim());
                        emp.setArea(t5.getText().trim());
                        emp.setDireccion(t6.getText().trim());
                        emp.setRfc(t7.getText().trim());
                        emp.setCurp(t8.getText().trim());
                        emp.setId_Almacen1(Integer.parseInt(t9.getText().trim()));

                        boolean ok = new EmpleadoDAO().insertarEmpleado(emp);
                        if(ok) {
                            mostrarAlerta("Éxito", "Empleado agregado correctamente.");
                            showMainDashboard("Empleados");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos introducidos."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); t9.clear(); },
                () -> showMainDashboard("Empleados")
            );
            
            formContainer.getChildren().addAll(lblTitulo, grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);

        } else {
            TextField t1 = new TextField(); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setStyle(TEXT_FIELD_STYLE);
            TextField t9 = new TextField(); t9.setStyle(TEXT_FIELD_STYLE);
            TextField t10 = new TextField(); t10.setStyle(TEXT_FIELD_STYLE);
            TextField t11 = new TextField(); t11.setStyle(TEXT_FIELD_STYLE);
            TextField t12 = new TextField(); t12.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("Código Movimiento:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("ID Almacén:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Movimiento de:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("ID Cliente:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("ID Proveedor:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("ID Empleado:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("Descripción:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("Fecha Registro (YYYY-MM-DD):"), 0, 7); grid.add(t8, 1, 7);
            grid.add(crearLabel("Fecha Orden (YYYY-MM-DD):"), 0, 8); grid.add(t9, 1, 8);
            grid.add(crearLabel("Fecha Concluido (YYYY-MM-DD):"), 0, 9); grid.add(t10, 1, 9);
            grid.add(crearLabel("Calificación:"), 0, 10); grid.add(t11, 1, 10);
            grid.add(crearLabel("Desc. Calificación:"), 0, 11); grid.add(t12, 1, 11);

            HBox btnBox = crearBotonesFormulario("Agregar",
                () -> {
                    try {
                        Movimiento m = new Movimiento();
                        m.setCod_Movimiento(Integer.parseInt(t1.getText().trim()));
                        m.setId_Almacen6(Integer.parseInt(t2.getText().trim()));
                        m.setMovimiento_de_(t3.getText().trim());
                        if(!t4.getText().trim().isEmpty()) m.setId_Cliente1(Integer.parseInt(t4.getText().trim()));
                        if(!t5.getText().trim().isEmpty()) m.setId_Proveedor2(Integer.parseInt(t5.getText().trim()));
                        m.setId_Empleado3(Integer.parseInt(t6.getText().trim()));
                        m.setDescripcion(t7.getText().trim());
                        if(!t8.getText().trim().isEmpty()) m.setFech_Registro(LocalDate.parse(t8.getText().trim()));
                        if(!t9.getText().trim().isEmpty()) m.setFech_Orden(LocalDate.parse(t9.getText().trim()));
                        if(!t10.getText().trim().isEmpty()) m.setFech_Concluido(LocalDate.parse(t10.getText().trim()));
                        m.setCalificacion(Integer.parseInt(t11.getText().trim()));
                        m.setDesc_Calificacion(t12.getText().trim());

                        boolean ok = new MovimientoDAO().insertarMovimiento(m);
                        if(ok) {
                            mostrarAlerta("Éxito", "Movimiento agregado correctamente.");
                            showMainDashboard("Movimientos");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos introducidos."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); t9.clear(); t10.clear(); t11.clear(); t12.clear(); },
                () -> showMainDashboard("Movimientos")
            );

            formContainer.getChildren().addAll(grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);
        }
    }

    private void showEditarForm(String section) {
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));

        Label lblTitulo = new Label("EDITAR - " + section.toUpperCase());
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1A237E; " + FONT_TITLE);

        HBox searchIdBox = new HBox(10);
        searchIdBox.setAlignment(Pos.CENTER);
        TextField txtIdBuscado = new TextField();
        txtIdBuscado.setPromptText("Ingrese ID a editar...");
        txtIdBuscado.setStyle(TEXT_FIELD_STYLE);
        Button btnBuscarId = new Button("Buscar");
        btnBuscarId.setStyle(BUTTON_NAV_STYLE);
        searchIdBox.getChildren().addAll(new Label("ID:"), txtIdBuscado, btnBuscarId);
        ((Label)searchIdBox.getChildren().get(0)).setStyle(LABEL_STYLE);

        GridPane grid = new GridPane();
        grid.setVgap(12); grid.setHgap(15); grid.setAlignment(Pos.CENTER);

        if (section.equals("Almacen")) {
            TextField txtId = new TextField(); txtId.setStyle(TEXT_FIELD_STYLE); txtId.setDisable(true);
            TextField txtZona = new TextField(); txtZona.setStyle(TEXT_FIELD_STYLE); txtZona.setDisable(true);
            TextField txtPiso = new TextField(); txtPiso.setStyle(TEXT_FIELD_STYLE); txtPiso.setDisable(true);
            TextField txtDireccion = new TextField(); txtDireccion.setStyle(TEXT_FIELD_STYLE); txtDireccion.setDisable(true);

            grid.add(crearLabel("ID Almacén:"), 0, 0); grid.add(txtId, 1, 0);
            grid.add(crearLabel("Zona:"), 0, 1); grid.add(txtZona, 1, 1);
            grid.add(crearLabel("Piso:"), 0, 2); grid.add(txtPiso, 1, 2);
            grid.add(crearLabel("Dirección:"), 0, 3); grid.add(txtDireccion, 1, 3);

            btnBuscarId.setOnAction(e -> {
                try {
                    int id = Integer.parseInt(txtIdBuscado.getText().trim());
                    Almacen temp = new Almacen(); temp.setId_Almacen(id);
                    ArrayList<Almacen> encontrados = new AlmacenDAO().buscarAlmacen(temp);
                    if (!encontrados.isEmpty()) {
                        Almacen a = encontrados.get(0);
                        txtId.setText(String.valueOf(a.getId_Almacen()));
                        txtId.setEditable(false);
                        txtZona.setText(a.getZona());
                        txtPiso.setText(String.valueOf(a.getPiso()));
                        txtDireccion.setText(a.getDireccion());

                        txtZona.setDisable(false); txtPiso.setDisable(false); txtDireccion.setDisable(false);
                    } else {
                        mostrarAlerta("Aviso", "No se encontró el Almacén con ese ID.");
                    }
                } catch(Exception ex) { mostrarAlerta("Error", "Ingrese un ID válido."); }
            });

            HBox btnBox = crearBotonesFormulario("Guardar Cambios",
                () -> {
                    try {
                        Almacen a = new Almacen();
                        a.setId_Almacen(Integer.parseInt(txtId.getText().trim()));
                        a.setZona(txtZona.getText().trim());
                        a.setPiso(Integer.parseInt(txtPiso.getText().trim()));
                        a.setDireccion(txtDireccion.getText().trim());

                        boolean ok = new AlmacenDAO().modificarAlmacen(a);
                        if(ok) {
                            mostrarAlerta("Éxito", "Datos Actualizados");
                            showMainDashboard("Almacen");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
                },
                () -> { txtZona.clear(); txtPiso.clear(); txtDireccion.clear(); },
                () -> showMainDashboard("Almacen")
            );

            formContainer.getChildren().addAll(lblTitulo, searchIdBox, grid, btnBox);
            mainLayout.setCenter(formContainer);

        } else if (section.equals("Productos")) {
            TextField t1 = new TextField(); t1.setDisable(true); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setDisable(true); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setDisable(true); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setDisable(true); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setDisable(true); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setDisable(true); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setDisable(true); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setDisable(true); t8.setStyle(TEXT_FIELD_STYLE);
            TextField t9 = new TextField(); t9.setDisable(true); t9.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Producto:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Tipo Producto:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Nombre:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Cantidad:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Modelo:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("Color:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("Producto De:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("ID Proveedor:"), 0, 7); grid.add(t8, 1, 7);
            grid.add(crearLabel("ID Almacén:"), 0, 8); grid.add(t9, 1, 8);

            btnBuscarId.setOnAction(e -> {
                try {
                    int id = Integer.parseInt(txtIdBuscado.getText().trim());
                    Articulo temp = new Articulo(); temp.setId_Producto(id);
                    ArrayList<Articulo> encontrados = new ArticuloDAO().buscarArticulo(temp);
                    if (!encontrados.isEmpty()) {
                        Articulo art = encontrados.get(0);
                        t1.setText(String.valueOf(art.getId_Producto()));
                        t2.setText(art.getTipoProducto());
                        t3.setText(art.getNombre());
                        t4.setText(String.valueOf(art.getCantidad()));
                        t5.setText(art.getModelo());
                        t6.setText(art.getColor());
                        t7.setText(art.getProducto_De());
                        t8.setText(String.valueOf(art.getId_Proveedor1()));
                        t9.setText(String.valueOf(art.getId_Almacen5()));

                        t2.setDisable(false); t3.setDisable(false); t4.setDisable(false);
                        t5.setDisable(false); t6.setDisable(false); t7.setDisable(false);
                        t8.setDisable(false); t9.setDisable(false);
                    } else {
                        mostrarAlerta("Aviso", "No se encontró el Artículo con ese ID.");
                    }
                } catch(Exception ex) { mostrarAlerta("Error", "Ingrese un ID válido."); }
            });

            HBox btnBox = crearBotonesFormulario("Guardar Cambios",
                () -> {
                    try {
                        Articulo art = new Articulo();
                        art.setId_Producto(Integer.parseInt(t1.getText().trim()));
                        art.setTipoProducto(t2.getText().trim());
                        art.setNombre(t3.getText().trim());
                        art.setCantidad(Float.parseFloat(t4.getText().trim()));
                        art.setModelo(t5.getText().trim());
                        art.setColor(t6.getText().trim());
                        art.setProducto_De(t7.getText().trim());
                        art.setId_Proveedor1(Integer.parseInt(t8.getText().trim()));
                        art.setId_Almacen5(Integer.parseInt(t9.getText().trim()));

                        boolean ok = new ArticuloDAO().modificarArticulo(art);
                        if(ok) {
                            mostrarAlerta("Éxito", "Datos Actualizados");
                            showMainDashboard("Productos");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
                },
                () -> { t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); t9.clear(); },
                () -> showMainDashboard("Productos")
            );

            formContainer.getChildren().addAll(lblTitulo, searchIdBox, grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);

        } else if (section.equals("Cliente")) {
            TextField t1 = new TextField(); t1.setDisable(true); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setDisable(true); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setDisable(true); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setDisable(true); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setDisable(true); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setDisable(true); t6.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Cliente:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Nombre:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Dirección:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Teléfono 1:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Teléfono 2:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("ID Almacén:"), 0, 5); grid.add(t6, 1, 5);

            btnBuscarId.setOnAction(e -> {
                try {
                    int id = Integer.parseInt(txtIdBuscado.getText().trim());
                    Cliente temp = new Cliente(); temp.setId_Cliente(id);
                    ArrayList<Cliente> encontrados = new ClienteDAO().buscarCliente(temp);
                    if (!encontrados.isEmpty()) {
                        Cliente c = encontrados.get(0);
                        t1.setText(String.valueOf(c.getId_Cliente()));
                        t2.setText(c.getNombre());
                        t3.setText(c.getDireccion());
                        t4.setText(c.getTel1());
                        t5.setText(c.getTel2());
                        t6.setText(String.valueOf(c.getId_Almacen3()));

                        t2.setDisable(false); t3.setDisable(false); t4.setDisable(false);
                        t5.setDisable(false); t6.setDisable(false);
                    } else {
                        mostrarAlerta("Aviso", "No se encontró el Cliente con ese ID.");
                    }
                } catch(Exception ex) { mostrarAlerta("Error", "Ingrese un ID válido."); }
            });

            HBox btnBox = crearBotonesFormulario("Guardar Cambios",
                () -> {
                    try {
                        Cliente c = new Cliente();
                        c.setId_Cliente(Integer.parseInt(t1.getText().trim()));
                        c.setNombre(t2.getText().trim());
                        c.setDireccion(t3.getText().trim());
                        c.setTel1(t4.getText().trim());
                        c.setTel2(t5.getText().trim());
                        c.setId_Almacen3(Integer.parseInt(t6.getText().trim()));

                        boolean ok = new ClienteDAO().modificarCliente(c);
                        if(ok) {
                            mostrarAlerta("Éxito", "Datos Actualizados");
                            showMainDashboard("Cliente");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
                },
                () -> { t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); },
                () -> showMainDashboard("Cliente")
            );

            formContainer.getChildren().addAll(lblTitulo, searchIdBox, grid, btnBox);
            mainLayout.setCenter(formContainer);

        } else if (section.equals("Proveedor")) {
            TextField t1 = new TextField(); t1.setDisable(true); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setDisable(true); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setDisable(true); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setDisable(true); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setDisable(true); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setDisable(true); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setDisable(true); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setDisable(true); t8.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Proveedor:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("Nombre:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Dirección:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("Especialidad:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("Teléfono 1:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("Teléfono 2:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("Tipo Material:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("ID Almacén:"), 0, 7); grid.add(t8, 1, 7);

            btnBuscarId.setOnAction(e -> {
                try {
                    int id = Integer.parseInt(txtIdBuscado.getText().trim());
                    Proveedor temp = new Proveedor(); temp.setId_Proveedor(id);
                    ArrayList<Proveedor> encontrados = new ProveedorDAO().buscarProveedor(temp);
                    if (!encontrados.isEmpty()) {
                        Proveedor p = encontrados.get(0);
                        t1.setText(String.valueOf(p.getId_Proveedor()));
                        t2.setText(p.getNombre());
                        t3.setText(p.getDireccion());
                        t4.setText(p.getEspecialidad());
                        t5.setText(p.getTel1());
                        t6.setText(p.getTel2());
                        t7.setText(p.getTipo_Material());
                        t8.setText(String.valueOf(p.getId_Almacen4()));

                        t2.setDisable(false); t3.setDisable(false); t4.setDisable(false);
                        t5.setDisable(false); t6.setDisable(false); t7.setDisable(false); t8.setDisable(false);
                    } else {
                        mostrarAlerta("Aviso", "No se encontró el Proveedor con ese ID.");
                    }
                } catch(Exception ex) { mostrarAlerta("Error", "Ingrese un ID válido."); }
            });

            HBox btnBox = crearBotonesFormulario("Guardar Cambios",
                () -> {
                    try {
                        Proveedor p = new Proveedor();
                        p.setId_Proveedor(Integer.parseInt(t1.getText().trim()));
                        p.setNombre(t2.getText().trim());
                        p.setDireccion(t3.getText().trim());
                        p.setEspecialidad(t4.getText().trim());
                        p.setTel1(t5.getText().trim());
                        p.setTel2(t6.getText().trim());
                        p.setTipo_Material(t7.getText().trim());
                        p.setId_Almacen4(Integer.parseInt(t8.getText().trim()));

                        boolean ok = new ProveedorDAO().modificarProveedor(p);
                        if(ok) {
                            mostrarAlerta("Éxito", "Datos Actualizados");
                            showMainDashboard("Proveedor");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
                },
                () -> { t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); },
                () -> showMainDashboard("Proveedor")
            );
            
            formContainer.getChildren().addAll(lblTitulo, searchIdBox, grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);

        } else if (section.equals("Empleados")) {
            TextField t1 = new TextField(); t1.setDisable(true); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setDisable(true); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setDisable(true); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setDisable(true); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setDisable(true); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setDisable(true); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setDisable(true); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setDisable(true); t8.setStyle(TEXT_FIELD_STYLE);
            TextField t9 = new TextField(); t9.setDisable(true); t9.setStyle(TEXT_FIELD_STYLE);
            TextField t10 = new TextField(); t10.setDisable(true); t10.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("ID Empleado (Buscar):"), 0, 0); grid.add(t10, 1, 0);
            grid.add(crearLabel("Nombre:"), 0, 1); grid.add(t1, 1, 1);
            grid.add(crearLabel("Teléfono 1:"), 0, 2); grid.add(t2, 1, 2);
            grid.add(crearLabel("Teléfono 2:"), 0, 3); grid.add(t3, 1, 3);
            grid.add(crearLabel("Puesto:"), 0, 4); grid.add(t4, 1, 4);
            grid.add(crearLabel("Área:"), 0, 5); grid.add(t5, 1, 5);
            grid.add(crearLabel("Dirección:"), 0, 6); grid.add(t6, 1, 6);
            grid.add(crearLabel("RFC:"), 0, 7); grid.add(t7, 1, 7);
            grid.add(crearLabel("CURP:"), 0, 8); grid.add(t8, 1, 8);
            grid.add(crearLabel("ID Almacén:"), 0, 9); grid.add(t9, 1, 9);
            t10.setDisable(false);

            btnBuscarId.setOnAction(e -> {
                try {
                    int id = Integer.parseInt(txtIdBuscado.getText().trim());
                    Empleado temp = new Empleado(); temp.setId_Empleado(id);
                    ArrayList<Empleado> encontrados = new EmpleadoDAO().buscarEmpleado(temp);
                    if (!encontrados.isEmpty()) {
                        Empleado emp = encontrados.get(0);
                        t10.setText(String.valueOf(emp.getId_Empleado()));
                        t10.setEditable(false);
                        t1.setText(emp.getNombre());
                        t2.setText(emp.getTel1());
                        t3.setText(emp.getTel2());
                        t4.setText(emp.getPuesto());
                        t5.setText(emp.getArea());
                        t6.setText(emp.getDireccion());
                        t7.setText(emp.getRfc());
                        t8.setText(emp.getCurp());
                        t9.setText(String.valueOf(emp.getId_Almacen1()));

                        t1.setDisable(false); t2.setDisable(false); t3.setDisable(false);
                        t4.setDisable(false); t5.setDisable(false); t6.setDisable(false);
                        t7.setDisable(false); t8.setDisable(false); t9.setDisable(false);
                    } else {
                        mostrarAlerta("Aviso", "No se encontró el Empleado con ese ID.");
                    }
                } catch(Exception ex) { mostrarAlerta("Error", "Ingrese un ID válido."); }
            });

            HBox btnBox = crearBotonesFormulario("Guardar Cambios",
                () -> {
                    try {
                        Empleado emp = new Empleado();
                        emp.setId_Empleado(Integer.parseInt(t10.getText().trim()));
                        emp.setNombre(t1.getText().trim());
                        emp.setTel1(t2.getText().trim());
                        emp.setTel2(t3.getText().trim());
                        emp.setPuesto(t4.getText().trim());
                        emp.setArea(t5.getText().trim());
                        emp.setDireccion(t6.getText().trim());
                        emp.setRfc(t7.getText().trim());
                        emp.setCurp(t8.getText().trim());
                        emp.setId_Almacen1(Integer.parseInt(t9.getText().trim()));

                        boolean ok = new EmpleadoDAO().modificarEmpleado(emp);
                        if(ok) {
                            mostrarAlerta("Éxito", "Datos Actualizados");
                            showMainDashboard("Empleados");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); t9.clear(); },
                () -> showMainDashboard("Empleados")
            );
            
            formContainer.getChildren().addAll(lblTitulo, searchIdBox, grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);

        } else {
            TextField t1 = new TextField(); t1.setDisable(true); t1.setStyle(TEXT_FIELD_STYLE);
            TextField t2 = new TextField(); t2.setDisable(true); t2.setStyle(TEXT_FIELD_STYLE);
            TextField t3 = new TextField(); t3.setDisable(true); t3.setStyle(TEXT_FIELD_STYLE);
            TextField t4 = new TextField(); t4.setDisable(true); t4.setStyle(TEXT_FIELD_STYLE);
            TextField t5 = new TextField(); t5.setDisable(true); t5.setStyle(TEXT_FIELD_STYLE);
            TextField t6 = new TextField(); t6.setDisable(true); t6.setStyle(TEXT_FIELD_STYLE);
            TextField t7 = new TextField(); t7.setDisable(true); t7.setStyle(TEXT_FIELD_STYLE);
            TextField t8 = new TextField(); t8.setDisable(true); t8.setStyle(TEXT_FIELD_STYLE);
            TextField t9 = new TextField(); t9.setDisable(true); t9.setStyle(TEXT_FIELD_STYLE);
            TextField t10 = new TextField(); t10.setDisable(true); t10.setStyle(TEXT_FIELD_STYLE);
            TextField t11 = new TextField(); t11.setDisable(true); t11.setStyle(TEXT_FIELD_STYLE);
            TextField t12 = new TextField(); t12.setDisable(true); t12.setStyle(TEXT_FIELD_STYLE);

            grid.add(crearLabel("Código Movimiento:"), 0, 0); grid.add(t1, 1, 0);
            grid.add(crearLabel("ID Almacén:"), 0, 1); grid.add(t2, 1, 1);
            grid.add(crearLabel("Movimiento de:"), 0, 2); grid.add(t3, 1, 2);
            grid.add(crearLabel("ID Cliente:"), 0, 3); grid.add(t4, 1, 3);
            grid.add(crearLabel("ID Proveedor:"), 0, 4); grid.add(t5, 1, 4);
            grid.add(crearLabel("ID Empleado:"), 0, 5); grid.add(t6, 1, 5);
            grid.add(crearLabel("Descripción:"), 0, 6); grid.add(t7, 1, 6);
            grid.add(crearLabel("Fecha Registro (YYYY-MM-DD):"), 0, 7); grid.add(t8, 1, 7);
            grid.add(crearLabel("Fecha Orden (YYYY-MM-DD):"), 0, 8); grid.add(t9, 1, 8);
            grid.add(crearLabel("Fecha Concluido (YYYY-MM-DD):"), 0, 9); grid.add(t10, 1, 9);
            grid.add(crearLabel("Calificación:"), 0, 10); grid.add(t11, 1, 10);
            grid.add(crearLabel("Desc. Calificación:"), 0, 11); grid.add(t12, 1, 11);

            btnBuscarId.setOnAction(e -> {
                try {
                    Movimiento m = new Movimiento();
                    m.setCod_Movimiento(Integer.parseInt(t1.getText().trim()));
                    m.setId_Almacen6(Integer.parseInt(t2.getText().trim()));
                    m.setMovimiento_de_(t3.getText().trim());
                    if(!t4.getText().trim().isEmpty()) m.setId_Cliente1(Integer.parseInt(t4.getText().trim()));
                    if(!t5.getText().trim().isEmpty()) m.setId_Proveedor2(Integer.parseInt(t5.getText().trim()));
                    m.setId_Empleado3(Integer.parseInt(t6.getText().trim()));
                    m.setDescripcion(t7.getText().trim());
                    if(!t8.getText().trim().isEmpty()) m.setFech_Registro(LocalDate.parse(t8.getText().trim()));
                    if(!t9.getText().trim().isEmpty()) m.setFech_Orden(LocalDate.parse(t9.getText().trim()));
                    if(!t10.getText().trim().isEmpty()) m.setFech_Concluido(LocalDate.parse(t10.getText().trim()));
                    m.setCalificacion(Integer.parseInt(t11.getText().trim()));
                    m.setDesc_Calificacion(t12.getText().trim());

                    boolean ok = new MovimientoDAO().modificarMovimiento(m);
                    if(ok) {
                        mostrarAlerta("Éxito", "Datos Actualizados");
                        showMainDashboard("Movimientos");
                    }
                } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
            });

            HBox btnBox = crearBotonesFormulario("Guardar Cambios",
                () -> {
                    try {
                        Movimiento m = new Movimiento();
                        m.setCod_Movimiento(Integer.parseInt(t1.getText().trim()));
                        m.setId_Almacen6(Integer.parseInt(t2.getText().trim()));
                        m.setMovimiento_de_(t3.getText().trim());
                        if(!t4.getText().trim().isEmpty()) m.setId_Cliente1(Integer.parseInt(t4.getText().trim()));
                        if(!t5.getText().trim().isEmpty()) m.setId_Proveedor2(Integer.parseInt(t5.getText().trim()));
                        m.setId_Empleado3(Integer.parseInt(t6.getText().trim()));
                        m.setDescripcion(t7.getText().trim());
                        if(!t8.getText().trim().isEmpty()) m.setFech_Registro(LocalDate.parse(t8.getText().trim()));
                        if(!t9.getText().trim().isEmpty()) m.setFech_Orden(LocalDate.parse(t9.getText().trim()));
                        if(!t10.getText().trim().isEmpty()) m.setFech_Concluido(LocalDate.parse(t10.getText().trim()));
                        m.setCalificacion(Integer.parseInt(t11.getText().trim()));
                        m.setDesc_Calificacion(t12.getText().trim());

                        boolean ok = new MovimientoDAO().modificarMovimiento(m);
                        if(ok) {
                            mostrarAlerta("Éxito", "Datos Actualizados");
                            showMainDashboard("Movimientos");
                        }
                    } catch(Exception ex) { mostrarAlerta("Error", "Verifica los datos a actualizar."); }
                },
                () -> { t1.clear(); t2.clear(); t3.clear(); t4.clear(); t5.clear(); t6.clear(); t7.clear(); t8.clear(); t9.clear(); t10.clear(); t11.clear(); t12.clear(); },
                () -> showMainDashboard("Movimientos")
            );

            formContainer.getChildren().addAll(grid, btnBox);
            ScrollPane scroll = new ScrollPane(formContainer);
            scroll.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
            mainLayout.setCenter(scroll);
        }
    }

    private Label crearLabel(String texto) {
        Label lbl = new Label(texto);
        lbl.setStyle(LABEL_STYLE);
        return lbl;
    }

    private HBox crearBotonesFormulario(String textoAccion, Runnable accionPrincipal, Runnable limpiarAccion, Runnable salirAccion) {
        HBox btnBox = new HBox(15);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(15, 0, 0, 0));

        Button btnPrincipal = new Button(textoAccion);
        btnPrincipal.setStyle(BUTTON_STYLE);
        btnPrincipal.setOnAction(e -> accionPrincipal.run());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #B0BEC5; -fx-text-fill: #1A237E; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px; " + FONT_TITLE + " -fx-cursor: hand;");
        btnLimpiar.setOnAction(e -> limpiarAccion.run());

        Button btnSalir = new Button("Salir");
        btnSalir.setStyle(BUTTON_EXIT_STYLE);
        btnSalir.setOnAction(e -> salirAccion.run());

        btnBox.getChildren().addAll(btnPrincipal, btnLimpiar, btnSalir);
        return btnBox;
    }

    private void ejecutarEliminacion(String section, String idStr) {
        if (idStr.isEmpty()) {
            mostrarAlerta("Error", "Ingrese un ID válido en la barra de eliminación.");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar eliminación");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("¿Esta seguro de eliminar el registro? Los datos se borraran permanentemente.");

            ButtonType btnSi = new ButtonType("Si");
            ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmAlert.getButtonTypes().setAll(btnSi, btnNo);

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == btnSi) {
                boolean resultado = false;

                if (section.equals("Almacen")) {
                    Almacen a = new Almacen(); 
                    a.setId_Almacen(id);
                    resultado = new AlmacenDAO().borrarAlmacen(a);
                } else if (section.equals("Movimientos")) {
                    Movimiento m = new Movimiento(); m.setCod_Movimiento(id);
                    resultado = new MovimientoDAO().borrarMovimiento(m);
                } else if (section.equals("Productos")) {
                    Articulo art = new Articulo(); art.setId_Producto(id);
                    resultado = new ArticuloDAO().borrarArticulo(art);
                } else if (section.equals("Empleados")) {
                    Empleado emp = new Empleado(); 
                    emp.setId_Empleado(id);
                    resultado = new EmpleadoDAO().borrarEmpleado(emp);
                } else if (section.equals("Cliente")) {
                    Cliente c = new Cliente(); c.setId_Cliente(id);
                    resultado = new ClienteDAO().borrarCliente(c);
                } else if (stringEquals(section, "Proveedor")) {
                    Proveedor p = new Proveedor(); p.setId_Proveedor(id);
                    resultado = new ProveedorDAO().borrarProveedor(p);
                }

                if (resultado) {
                    mostrarAlerta("Éxito", "Registro eliminado con exito");
                    showMainDashboard(section);
                } else {
                    mostrarAlerta("Aviso", "No se pudo eliminar el registro (verifique el ID).");
                }
            }
        } catch (NumberFormatException ex) {
            mostrarAlerta("Error", "El ID debe ser un número entero.");
        }
    }

    private boolean stringEquals(String s1, String s2) {
        return s1.equals(s2);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        appAlertStyle(alert);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void appAlertStyle(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #FFFFFF; " + FONT_BODY + " -fx-font-size: 14px;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}