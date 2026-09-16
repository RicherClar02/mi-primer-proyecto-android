package com.richer.primerproyecto;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.widget.TextView;

/**
 * Unica pantalla de la aplicacion.
 *
 * Se encarga de tres cosas:
 *   1. Dibujar de borde a borde (edge-to-edge) sin que los textos queden
 *      debajo de la barra de estado ni de la barra de navegacion.
 *   2. Mostrar en pantalla cual es el idioma activo.
 *   3. Cambiar el idioma de la app cuando se pulsa uno de los cuatro botones.
 */
public class MainActivity extends AppCompatActivity {

    /** Codigos de idioma en el formato BCP 47 que espera Android. */
    private static final String IDIOMA_ESPANOL = "es";
    private static final String IDIOMA_INGLES = "en";
    private static final String IDIOMA_FRANCES = "fr";
    private static final String IDIOMA_ALEMAN = "de";

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
        mostrarIdiomaActual();
        configurarBotonesDeIdioma();
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

    /**
     * Escribe la linea "Idioma actual: ...".
     *
     * No hay ningun texto fijo en el codigo: se combinan dos cadenas de
     * strings.xml, y como cada carpeta de idioma tiene su propia version,
     * la linea se traduce sola.
     */
    private void mostrarIdiomaActual() {
        TextView etiqueta = findViewById(R.id.texto_idioma_actual);
        String nombreDelIdioma = getString(R.string.nombre_idioma);
        etiqueta.setText(getString(R.string.etiqueta_idioma_actual, nombreDelIdioma));
    }

    /** Conecta cada boton con su idioma. */
    private void configurarBotonesDeIdioma() {
        findViewById(R.id.boton_es).setOnClickListener(v -> cambiarIdioma(IDIOMA_ESPANOL));
        findViewById(R.id.boton_en).setOnClickListener(v -> cambiarIdioma(IDIOMA_INGLES));
        findViewById(R.id.boton_fr).setOnClickListener(v -> cambiarIdioma(IDIOMA_FRANCES));
        findViewById(R.id.boton_de).setOnClickListener(v -> cambiarIdioma(IDIOMA_ALEMAN));
    }

    /**
     * Cambia el idioma de la aplicacion (no el del telefono).
     *
     * setApplicationLocales es la forma recomendada por Android: el sistema
     * recrea la pantalla con los recursos del idioma elegido y ademas recuerda
     * la eleccion la proxima vez que se abra la app. En Android 13 o superior
     * lo guarda el propio sistema; en versiones anteriores lo guarda AppCompat
     * gracias al servicio AppLocalesMetadataHolderService del manifiesto.
     *
     * @param codigoDeIdioma codigo BCP 47: "es", "en", "fr" o "de".
     */
    private void cambiarIdioma(String codigoDeIdioma) {
        LocaleListCompat idiomas = LocaleListCompat.forLanguageTags(codigoDeIdioma);
        AppCompatDelegate.setApplicationLocales(idiomas);
    }
}
