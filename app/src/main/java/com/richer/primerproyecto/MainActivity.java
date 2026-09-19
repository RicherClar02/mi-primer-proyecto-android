package com.richer.primerproyecto;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * Unica pantalla de la aplicacion.
 *
 * Se encarga de tres cosas:
 *   1. Dibujar de borde a borde (edge-to-edge) sin que los textos queden
 *      debajo de la barra de estado ni de la barra de navegacion.
 *   2. Mantener el contenido protegido de las barras del sistema al girar.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pide al sistema dibujar detras de las barras. Se llama ANTES de
        // super.onCreate para que ya este activo cuando se infle la vista.
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        // Android elige solo entre res/layout y res/layout-land segun la
        // orientacion: aqui siempre se pide el mismo nombre.
        setContentView(R.layout.activity_main);

        configurarBarrasDelSistema();
        aplicarMargenesDeSeguridad();
    }

    /**
     * El fondo es un cielo azul intenso, asi que los iconos de las barras del
     * sistema se dibujan en blanco para que se vean bien.
     */
    private void configurarBarrasDelSistema() {
        WindowInsetsControllerCompat controlador =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controlador.setAppearanceLightStatusBars(false);
        controlador.setAppearanceLightNavigationBars(false);
    }

    /**
     * Separa el contenido de las zonas ocupadas por el sistema (barra de
     * estado, barra de navegacion y muescas de la camara).
     *
     * El relleno se pone en el ScrollView, no en la vista raiz: asi la imagen
     * de fondo sigue ocupando la pantalla completa, pero ningun texto ni boton
     * queda tapado. Funciona igual en vertical y en horizontal, porque los
     * insets cambian solos al girar el telefono.
     */
    private void aplicarMargenesDeSeguridad() {
        final View contenedor = findViewById(R.id.contenedor_scroll);

        ViewCompat.setOnApplyWindowInsetsListener(contenedor, (vista, insets) -> {
            Insets zonas = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout());

            vista.setPadding(zonas.left, zonas.top, zonas.right, zonas.bottom);
            return insets;
        });
    }

}
