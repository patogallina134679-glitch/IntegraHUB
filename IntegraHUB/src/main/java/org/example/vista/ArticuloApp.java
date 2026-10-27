package org.example.vista;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.ArticuloDAO;
import org.example.modelo.Articulo;

import java.util.ArrayList;

public class ArticuloApp extends Application {

    private final ArticuloDAO articuloDAO = new ArticuloDAO();
    private TableView<Articulo> tablaArticulos;
    private ObservableList<Articulo> listaArticulos;

    private TextField txtIdProducto, txtTipoProducto, txtNombre, txtCantidad, txtModelo, txtColor, txtProductoDe, txtIdProveedor, txtIdAlmacen;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Artículos - IntegraHUB");

        tablaArticulos = new TableView<>();
        listaArticulos = FXCollections.observableArrayList();

        TableColumn<Articulo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id_Producto"));

        TableColumn<Articulo, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoProducto"));

        TableColumn<Articulo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Articulo, Float> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<Articulo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Articulo, String> colColor = new TableColumn<>("Color");
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Articulo, String> colProcedencia = new TableColumn<>("Producto De");
        colProcedencia.setCellValueFactory(new PropertyValueFactory<>("producto_De"));

        TableColumn<Articulo, Integer> colProv = new TableColumn<>("ID Prov");
        colProv.setCellValueFactory(new PropertyValueFactory<>("id_Proveedor1"));

        TableColumn<Articulo, Integer> colAlmacen = new TableColumn<>("ID Almacén");
        colAlmacen.setCellValueFactory(new PropertyValueFactory<>("id_Almacen5"));

        tablaArticulos.getColumns().addAll(colId, colTipo, colNombre, colCantidad, colModelo, colColor, colProcedencia, colProv, colAlmacen);
        cargarDatosTabla();

        tablaArticulos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtIdProducto.setText(String.valueOf(newVal.getId_Producto()));
                txtTipoProducto.setText(newVal.getTipoProducto());
                txtNombre.setText(newVal.getNombre());
                txtCantidad.setText(String.valueOf(newVal.getCantidad()));
                txtModelo.setText(newVal.getModelo());
                txtColor.setText(newVal.getColor());
                txtProductoDe.setText(newVal.getProducto_De());
                txtIdProveedor.setText(String.valueOf(newVal.getId_Proveedor1()));
                txtIdAlmacen.setText(String.valueOf(newVal.getId_Almacen5()));
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        int row = 0;
        grid.add(new Label("ID Producto:"), 0, row);
        txtIdProducto = new TextField();
        grid.add(txtIdProducto, 1, row++);

        grid.add(new Label("Tipo Producto:"), 0, row);
        txtTipoProducto = new TextField();
        grid.add(txtTipoProducto, 1, row++);

        grid.add(new Label("Nombre:"), 0, row);
        txtNombre = new TextField();
        grid.add(txtNombre, 1, row++);

        grid.add(new Label("Cantidad:"), 0, row);
        txtCantidad = new TextField();
        grid.add(txtCantidad, 1, row++);

        grid.add(new Label("Modelo:"), 0, row);
        txtModelo = new TextField();
        grid.add(txtModelo, 1, row++);

        grid.add(new Label("Color:"), 0, row);
        txtColor = new TextField();
        grid.add(txtColor, 1, row++);

        grid.add(new Label("Producto De:"), 0, row);
        txtProductoDe = new TextField();
        grid.add(txtProductoDe, 1, row++);

        grid.add(new Label("ID Proveedor:"), 0, row);
        txtIdProveedor = new TextField();
        grid.add(txtIdProveedor, 1, row++);

        grid.add(new Label("ID Almacén:"), 0, row);
        txtIdAlmacen = new TextField();
        grid.add(txtIdAlmacen, 1, row++);

        Button btnAgregar = new Button("Agregar");
        Button btnModificar = new Button("Modificar");
        Button btnEliminar = new Button("Eliminar");
        Button btnLimpiar = new Button("Limpiar");

        btnAgregar.setOnAction(e -> agregarArticulo());
        btnModificar.setOnAction(e -> modificarArticulo());
        btnEliminar.setOnAction(e -> eliminarArticulo());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        HBox botonesBox = new HBox(10, btnAgregar, btnModificar, btnEliminar, btnLimpiar);
        botonesBox.setPadding(new Insets(10, 0, 0, 0));
        grid.add(botonesBox, 1, row);

        VBox vbox = new VBox(10, tablaArticulos, grid);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vbox, 900, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cargarDatosTabla() {
        ArrayList<Articulo> articulosFromDB = articuloDAO.extraerArticulo();
        listaArticulos.setAll(articulosFromDB);
        tablaArticulos.setItems(listaArticulos);
    }

    private void agregarArticulo() {
        try {
            Articulo art = new Articulo();
            art.setId_Producto(Integer.parseInt(txtIdProducto.getText().trim()));
            art.setTipoProducto(txtTipoProducto.getText().trim());
            art.setNombre(txtNombre.getText().trim());
            art.setCantidad(Float.parseFloat(txtCantidad.getText().trim()));
            art.setModelo(txtModelo.getText().trim());
            art.setColor(txtColor.getText().trim());
            art.setProducto_De(txtProductoDe.getText().trim());
            art.setId_Proveedor1(Integer.parseInt(txtIdProveedor.getText().trim()));
            art.setId_Almacen5(Integer.parseInt(txtIdAlmacen.getText().trim()));

            boolean exito = articuloDAO.insertarArticulo(art);
            if (exito) {
                cargarDatosTabla();
                limpiarFormulario();
                mostrarAlerta("Éxito", "Artículo registrado correctamente en la base de datos.");
            }
        } catch (Exception ex) {
            mostrarAlerta("Error", "Verifica los datos introducidos: " + ex.getMessage());
        }
    }

    private void modificarArticulo() {
        try {
            Articulo art = new Articulo();
            art.setId_Producto(Integer.parseInt(txtIdProducto.getText().trim()));
            art.setTipoProducto(txtTipoProducto.getText().trim());
            art.setNombre(txtNombre.getText().trim());
            art.setCantidad(Float.parseFloat(txtCantidad.getText().trim()));
            art.setModelo(txtModelo.getText().trim());
            art.setColor(txtColor.getText().trim());
            art.setProducto_De(txtProductoDe.getText().trim());
            art.setId_Proveedor1(Integer.parseInt(txtIdProveedor.getText().trim()));
            art.setId_Almacen5(Integer.parseInt(txtIdAlmacen.getText().trim()));

            boolean exito = articuloDAO.modificarArticulo(art);
            if (exito) {
                cargarDatosTabla();
                limpiarFormulario();
                mostrarAlerta("Éxito", "Artículo actualizado correctamente.");
            }
        } catch (Exception ex) {
            mostrarAlerta("Error", "Verifica los datos a modificar: " + ex.getMessage());
        }
    }

    private void eliminarArticulo() {
        try {
            Articulo seleccionado = tablaArticulos.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                seleccionado = new Articulo();
                seleccionado.setId_Producto(Integer.parseInt(txtIdProducto.getText().trim()));
            }

            boolean exito = articuloDAO.borrarArticulo(seleccionado);
            if (exito) {
                cargarDatosTabla();
                limpiarFormulario();
                mostrarAlerta("Éxito", "Artículo eliminado con éxito.");
            } else {
                mostrarAlerta("Aviso", "No se pudo eliminar el artículo.");
            }
        } catch (Exception ex) {
            mostrarAlerta("Error", "Selecciona o ingresa un ID válido para borrar: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtIdProducto.clear();
        txtTipoProducto.clear();
        txtNombre.clear();
        txtCantidad.clear();
        txtModelo.clear();
        txtColor.clear();
        txtProductoDe.clear();
        txtIdProveedor.clear();
        txtIdAlmacen.clear();
        tablaArticulos.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}