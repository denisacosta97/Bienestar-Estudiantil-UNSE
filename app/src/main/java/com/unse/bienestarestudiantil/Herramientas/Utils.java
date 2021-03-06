package com.unse.bienestarestudiantil.Herramientas;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.unse.bienestarestudiantil.Databases.AlumnoViewModel;
import com.unse.bienestarestudiantil.Databases.EgresadoViewModel;
import com.unse.bienestarestudiantil.Databases.ProfesorViewModel;
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.Modelos.Maraton;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class Utils {

    //Constantes de preferencias
    public static final String IS_FIRST_TIME_LAUNCH = "is_first";
    public static final String IS_LOGIN = "login_yes";
    public static final String MY_ID = "my_id_user";
    public static final String TOKEN = "my_token";
    public static final String IS_VISIT = "visit";
    //Constantes para las BD
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String FLOAT_TYPE = "float";
    public static final String NULL_TYPE = "not null";
    public static final String AUTO_INCREMENT = "primary key autoincrement";
    //Constantes para activities
    public static final String IS_EDIT_MODE = "edit_mode";
    public static final String USER_INFO = "user_info";
    public static final String DEPORTE_NAME = "dato_deporte";
    public static final String DEPORTE_TEMPORADA = "temporada_deporte";
    public static final String DEPORTE_ID = "id_deporte";
    public static final String TIPO_CREDENCIAL = "tipo_cred";
    public static final String TIPO_CREDENCIAL_DATO = "tipo_cred_dato";
    public static final String CREDENCIAL = "credencial";
    public static final String URI_IMAGE = "uri_image";
    public static final String DEPORTE_NAME_PROF = "dato_deporte_prof";
    public static final String IS_ADMIN_MODE = "is_admin_mode";
    public static final String NAME_GENERAL = "name_general";
    public static final String ANIO = "anio_temp";
    public static final String ROLES = "roles_all";
    public static final String ROLES_USER = "roles_user";
    public static final String ASISTENCIA = "asistencia";
    public static final String FECHA = "fecha";
    public static final String LINEA_NAME = "linea_name";
    public static final String TIPO_REGISTRO = "tipo_acdeso";
    public static final String LIST_REGULARIDAD = "lista_regularidad";
    public static final String ARCHIVO_NAME = "archivo_name";
    public static final String LIST_HIJOS = "list_hijos";
    public static final String DATA_FECHA = "data_fecha";
    public static final String LIST_SUSCROP = "list_sus";
    public static final String LIST_CRED = "list_cred";
    public static final String RECORRIDO = "recorrido";
    public static final String SERVICIO = "servicio_info";
    public static final String PUNTO = "punto_info";
    public static final String COLECTIVO = "cole_info";
    public static final String NOTICIA_INFO = "noticia_info";
    public static final String PASAJERO = "pasajeros";
    public static final String SERVUAPU = "servi";
    public static final String DOCTOR = "doc";
    public static final String FECHA_PASS = "fecha_pass";
    public static final String PACIENTE = "paciente";

    public static final String IMPRESION = "impresion";
    public static final String CANCHA = "cancha";
    //Constantes para activities
    public static final long UPDATE_INTERVAL = 12000;
    public static final int PICK_IMAGE = 9090;
    public static final int EDIT_IMAGE = 9091;
    public static final int PERMISSION_ALL = 9092;
    public static final int GET_FROM_DNI = 9093;
    public static final int SELECT_FILE = 9094;
    public static final int REQUEST_GROUP_PERMISSIONS_LOCATION = 9095;
    public static final int REQUEST_LOCATION = 9096;
    public static final int REQUEST_CHECK_SETTINGS = 9097;
    public static final int GET_FROM_GALLERY = 9096;
    public static final int REQUEST_FILE_CSV = 9098;
    //Constantes para tipos de usuario
    public static final int TIPO_USUARIO = 1;
    public static final int TIPO_ESTUDIANTE = 2;
    public static final int TIPO_ROLES = 10;
    public static final int TIPO_SOCIO = 11;
    public static final int TIPO_CHOFER = 12;
    //Constantes para busqueda
    public static final String PATRON_LEGAJO = "[0-9]{1,5}(-|/)[0-9]{2,4}";
    public static final String PATRON_DNI = "([0-9]){5,8}";
    public static final String PATRON_NOMBRES = "[a-zA-Z_ ]+";
    public static final String PATRON_NUMEROS = "[0-9]+";
    //Constante para tipo de usuario
    public static final int TIPO_ALUMNO = 1;
    public static final int TIPO_PROFESOR = 2;
    public static final int TIPO_NODOCENTE = 3;
    public static final int TIPO_EGRESADO = 4;
    public static final int TIPO_PARTICULAR = 5;
    //Constante para Volley
    public static final int MY_DEFAULT_TIMEOUT = 15000;
    //Constante de nombres de archivos
    public static final String PROFILE_PIC = "%s.jpg";
    public static final String FILE_EXT = "%s";
    //Constantes de permisos
    public static final int[] LIST_PERMISOS = new int[]{999, 998};

    public static final int NOTICIA_NORMAL = 3030;
    public static final int NOTICIA_BUTTON_WEB = 3131;
    public static final int NOTICIA_BUTTON_TIENDA = 3132;
    public static final int NOTICIA_BUTTON_APP = 3133;

    public static final int TIPO_COMEDOR = 1;
    public static final int TIPO_DEPORTE = 2;
    public static final int TIPO_TRANSPORTE = 3;
    public static final int TIPO_BECA = 4;
    public static final int TIPO_RESIDENCIA = 5;
    public static final int TIPO_CYBER = 6;
    public static final int TIPO_UPA = 7;

    public static final int TIPO_CANCHA = 1010;
    public static final int TIPO_QUINCHO = 1011;

    public static final String TYPE_RANGE = "type_range";

    public static final String MONSERRAT = "Montserrat-Regular.ttf";
    public static final String MONTSERRAT_BOLD = "Montserrat-Black.ttf";

    public static final String BECA_NAME = "dato_deporte";
    public static final String BARCODE = "dato_barcode";
    public static final String TORNEO = "dato_torneo";
    public static final String NOTICIA = "dato_noticias";
    public static final String RESERVA = "dato_reserva";
    public static final String ID_MENU = "id_menu";
    public static final String RESERVA_ESPECIALES = "reservaE";
    public static final String DATA_RESERVA = "dato_reserva";
    public static final String MESNAME = "mes_name";
    public static final String NUMERO_MES = "numero_mes";
    public static final String DATA_CONVOCATORIA = "dato_convocatoria";
    public static final String DATA_TURNO = "data_turno";
    public static final String DATA_FACULTAD = "data_facultad";
    public static final String DATA_SEXO = "data_sexo";
    public static final String DATA_PROVINCIA = "data_provincia";
    public static final String DATA_TIPO = "data_tipo";
    public static final String ALUMNO_NAME = "dato_alumno";
    public static final String USER_NAME = "dato_user";
    public static final String NUM_INST = "num_inst";
    public static final String NUM_CONVOC = "num_conv";
    public static final String MENU = "menu_comedor";
    public static final int LIST_RESET = 0;
    public static final int LIST_LEGAJO = 1;
    public static final int LIST_DNI = 2;
    public static final int LIST_NOMBRE = 3;
    public static final String COLOR = "color_op";


    private static final String IP = "sis.bienestar.unse.edu.ar/api";
    //USUARIO
    public static final String URL_USUARIO_INSERTAR = "https://" + IP + "/usuario/insertar.php";
    public static final String URL_USUARIO_ACTUALIZAR = "https://" + IP + "/usuario/actualizar.php";
    public static final String URL_USUARIO_LOGIN = "https://" + IP + "/usuario/login.php";
    public static final String URL_USUARIO_LOGIN_DATA = "https://" + IP + "/usuario/loginInfo.php";
    public static final String URL_USUARIO_IMAGE = "https://" + IP + "/general/uploadImage.php";
    public static final String URL_USUARIO_IMAGE_LOAD = "https://" + IP + "/usuariosImg/";
    public static final String URL_CAMBIO_CONTRASENIA = "https://" + IP + "/usuario/cambiarContrasenia.php";
    public static final String URL_REC_CONTRASENIA = "https://" + IP + "/mail/restablecer.php";
    public static final String URL_USUARIOS_LISTA = "https://" + IP + "/usuario/getUsuarios.php";
    public static final String URL_USUARIO_BY_ID = "https://" + IP + "/usuario/getUser.php";
    public static final String URL_USUARIO_ELIMINAR = "https://" + IP + "/usuario/eliminar.php";
    public static final String URL_USUARIO_BY_ID_REDUCE = "https://" + IP + "/usuario/getUserReduce.php";
    public static final String URL_USUARIO_ESTADISTICA = "https://" + IP + "/usuario/getEstadisticas.php";


    public static final String URL_INSCRIPCION_MARATON = "https://" + IP + "/deportes/carrera/insertar.php";
    //ALUMNO
    public static final String URL_REGULARIDAD = "https://" + IP + "/usuario/insertarRegularidad.php";
    public static final String URL_REGULARIDAD_CAMBIAR = "https://" + IP + "/usuario/cambiarRegularidad.php";

    //SOCIO
    public static final String URL_SOCIO_CREDENCIAL = "https://" + IP + "/socio/getCredencial.php";
    public static final String URL_SOCIO_LISTA = "https://" + IP + "/socio/getSocios.php";
    public static final String URL_SOCIO_PARTIULAR = "https://" + IP + "/socio/getSocio.php";
    public static final String URL_SOCIO_FAMILIAR_AGREGAR = "https://" + IP + "/socio/insertarFamiliar.php";
    public static final String URL_SOCIO_FAMILIAR_ACTUALIZAR = "https://" + IP + "/socio/actualizarFamiliar.php";
    public static final String URL_CREDENCIAL_SOCIO_CAMBIAR = "https://" + IP + "/socio/cambiarCredencial.php";
    public static final String URL_SOCIO_SUSCRIPCION = "https://" + IP + "/socio/getSuscripcion.php";
    public static final String URL_SOCIO_SUSCRIPCION_AGREGAR = "https://" + IP + "/socio/insertarInscripcion.php";
    public static final String URL_SOCIO_SUSCRIPCION_CAMBIAR = "https://" + IP + "/socio/actualizarSuscripcion.php";

    //ROLES
    public static final String URL_ROLES_LISTA = "https://" + IP + "/general/rol/getRoles.php";
    public static final String URL_ROLES_INSERTAR = "https://" + IP + "/general/rol/insertar.php";
    public static final String URL_ROLES_USER_LISTA = "https://" + IP + "/general/rol/getRolesByUsuario.php";

    //DEPORTES
    public static final String URL_DEPORTE_LISTA = "https://" + IP + "/deportes/getDeportes.php";
    public static final String URL_DEPORTE_TEMPORADA = "https://" + IP + "/deportes/inscripcion/getTemporada.php";
    public static final String URL_DEPORTE_INSCRIPCION = "https://" + IP + "/deportes/inscripcion/insertar.php";
    public static final String URL_INSCRIPCIONES_GENERALES = "https://" + IP + "/becas/getInscripciones.php";

    public static final String URL_DEPORTE_CREDENCIAL = "https://" + IP + "/deportes/getCredencial.php";
    public static final String URL_DEPORTE_BAJA = "https://" + IP + "/deportes/actualizarDeporte.php";
    public static final String URL_INSCRIPCIONES_POR_DEPORTE = "https://" + IP + "/deportes/getInscriptos.php";

    public static final String URL_INSCRIPCION_PARTICULAR = "https://" + IP + "/deportes/getInscripcion.php";
    public static final String URL_INSCRIPCION_PARTICULAR_ELIMIAR = "https://" + IP + "/deportes/eliminarInscripcion.php";
    public static final String URL_INSCRIPCION_ACTUALIZAR = "https://" + IP + "/deportes/actualizarInscripcion.php";
    public static final String URL_INSCRIPCION_CARNET = "https://" + IP + "/deportes/insertarCredencial.php";
    public static final String URL_CREDENCIAL_CAMBIAR = "https://" + IP + "/deportes/cambiarCredencial.php";
    public static final String URL_PROFES = "https://" + IP + "/deportes/getAllProfesores.php";
    public static final String URL_BECADOS = "https://" + IP + "/beca/getAllBecados.php";
    public static final String URL_ASISTENCIA = "https://" + IP + "/deportes/asistencia.php";

    //MARATON
    public static final String URL_INSCRIPTOS_MARATON = "https://" + IP + "/deportes/carrera/getInscriptos.php";
    public static final String URL_MARATON_ESTADISTICA = "https://" + IP + "/deportes/carrera/getEstadisticas.php";

    //POLIDEPORTIVO
    public static final String URL_INGRESO_POLI = "http://" + IP + "/bienestar/polideportivo/pileta/ingresoPoli.php";
    public static final String URL_INGRESO_PILE = "http://" + IP + "/bienestar/polideportivo/pileta/ingresoPileta.php";
    public static final String URL_INGRESO_TEMPORADA = "http://" + IP + "/bienestar/polideportivo/pileta/getIngresosByTemporada.php";
    public static final String URL_INGRESO_EMPLEADO = "http://" + IP + "/bienestar/polideportivo/pileta/getIngresosByEmpleado.php";
    public static final String URL_RESERVAS_ESPACIOS_FECHA = "http://" + IP + "/bienestar/polideportivo/espacio/getReservas.php";
    public static final String URL_RESERVA_ESPACIOS = "http://" + IP + "/bienestar/polideportivo/espacio/getReserva.php";
    public static final String URL_RESERVAS_ESPACIOS_ID = "http://" + IP + "/bienestar/polideportivo/espacio/getReservaByUser.php";

    //TORNEOS
    public static final String URL_TORNEO_CREDENCIAL = "https://" + IP + "/deportes/torneo/getCredencial.php";
    public static final String URL_TORNEOS_LISTA = "https://" + IP + "/deportes/torneo/getAllTorneos.php";
    public static final String URL_TORNEOS_ACTUALIZAR = "https://" + IP + "/deportes/torneo/actualizar.php";
    public static final String URL_TORNEOS_BAJA = "https://" + IP + "/deportes/torneo/borrar.php";
    public static final String URL_TORNEOS_INSERTAR = "https://" + IP + "/deportes/torneo/insertar.php";

    //BECAS
    public static final String URL_BECAS_CREDENCIAL = "http://" + IP + "/bienestar/beca/getCredencial.php";
    public static final String URL_INSERTAR_CONSULTA = "https://" + IP + "/becas/consulta/insertar.php";
    public static final String URL_CONSULTAS = "https://" + IP + "/becas/consulta/getConsultas.php";
    public static final String URL_CAMBIAR_FECHA = "https://" + IP + "/becas/fecha/eliminar.php";
    public static final String URL_FECHAS_INHAB = "https://" + IP + "/becas/fecha/getFechas.php";
    public static final String URL_INSERTAR_FECHAS = "https://" + IP + "/becas/fecha/insertar.php";
    public static final String URL_TURNOS_DIA = "https://" + IP + "/becas/turno/getTurnosToday.php";
    public static final String URL_TURNOS_BY_DAY = "https://" + IP + "/becas/turno/getTurnosByDay.php";
    public static final String URL_TURNOS_LISTA = "https://" + IP + "/becas/turno/getTurnos.php";
    public static final String URL_TURNOS_ACTUALIZAR = "https://" + IP + "/becas/turno/actualizar.php";
    public static final String URL_TURNOS_ATENDER = "https://" + IP + "/becas/turno/atender.php";
    public static final String URL_CONVOCATORIA = "https://" + IP + "/becas/convocatoria/getConvocatorias.php";
    public static final String URL_BAJA_CONVOCATORIA = "https://" + IP + "/becas/convocatoria/actualizar.php";

    //CIBER
    public static final String URL_REGISTRAR_INGRESO = "https://" + IP + "/ciber/maquina/insertar.php";
    public static final String URL_LISTA_INGRESOS = "https://" + IP + "/ciber/maquina/getUsosByDay.php";
    public static final String URL_LISTA_INGRESOS_HOY = "https://" + IP + "/ciber/maquina/getUsosToday.php";
    public static final String URL_HISTORICOS_INGRESOS = "https://" + IP + "/ciber/maquina/getUsos.php";
    public static final String URL_IMPRESIONES_HOY = "https://" + IP + "/ciber/impresion/getImpresionesToday.php";
    public static final String URL_IMPRESIONES_DIA = "https://" + IP + "/ciber/impresion/getImpresionByDay.php";
    public static final String URL_IMPRESIONES_HIST = "https://" + IP + "/ciber/impresion/getImpresiones.php";
    public static final String URL_REGISTRAR_IMPR = "https://" + IP + "/ciber/impresion/insertar.php";

    //GENERALES
    public static final String URL_ARCHIVOS_LISTA = "https://" + IP + "/general/getArchivos.php";
    public static final String URL_ARCHIVOS = "https://" + IP + "/archivos/";
    public static final String URL_UPLOAD_FILE = "https://" + IP + "/general/uploadFile.php";
    public static final String URL_PTOC_HISTORICOS = "https://" + IP + "/general/conectividad/getAll.php";
    //PUNTOS CONECTIVIDAD
    public static final String URL_PC_ESCANEAR = "https://" + IP + "/general/conectividad/actualizar.php";
    public static final String URL_TURNOS_DIA_PC = "https://" + IP + "/general/conectividad/getByDay.php";
    public static final String URL_PC_ESTADISTICA = "https://" + IP + "/general/conectividad/getEstadisticas.php";


    //TRANSPORTE
    public static final String URL_LINEAS = "https://" + IP + "/transporte/getAllLineas.php";
    public static final String URL_LINEAS_UPDATE = "https://" + IP + "/trasporte/actualizar.php";
    public static final String URL_LINEAS_ADD = "https://" + IP + "/transporte/insertar.php";
    public static final String URL_ACTUALIZAR_CHOFER = "https://" + IP + "/transporte/chofer/actualizarChofer.php";
    public static final String URL_ELIMINAR_CHOFER = "https://" + IP + "/transporte/chofer/eliminarChofer.php";
    //public static final String URL_ACTUALIZAR_TEMPORADA = "https://" + IP + "/transporte/chofer/actualizarTemporada.php";
    public static final String URL_GET_CHOFER = "https://" + IP + "/transporte/chofer/getChofer.php";
    public static final String URL_GET_CHOFERES = "https://" + IP + "/transporte/chofer/getChoferes.php";
    public static final String URL_SERVICIOS_CHOFER = "https://" + IP + "/transporte/chofer/getServiciosByChofer.php";
    public static final String URL_INSERTAR_CHOFER = "https://" + IP + "/transporte/chofer/insertarChofer.php";
    //public static final String URL_TEMPORADA_CHOFER = "https://" + IP + "/transporte/chofer/registrarTemporada.php";
    public static final String URL_ACTUALIZAR_COLECTIVO = "https://" + IP + "/transporte/colectivo/actualizarColectivo.php";
    public static final String URL_ELIMINAR_COLECTIVO = "https://" + IP + "/transporte/colectivo/eliminarColectivo.php";
    public static final String URL_GET_COLECTIVO = "https://" + IP + "/transporte/colectivo/getColectivo.php";
    public static final String URL_GET_COLECTIVOS = "https://" + IP + "/transporte/colectivocolectivo/getColectivos.php";
    public static final String URL_INSERTAR_COLECTIVO = "https://" + IP + "/transporte/colectivo/insertarColectivo.php";
    public static final String URL_ELIMINAR_RECORRIDO = "https://" + IP + "/transporte/recorrido/eliminarRecorrido.php";
    public static final String URL_GET_RECORRIDO = "https://" + IP + "/transporte/recorrido/getRecorrido.php";
    public static final String URL_GET_RECORRIDOS = "https://" + IP + "/transporte/recorrido/getRecorridos.php";
    public static final String URL_RECORRIDOS = "https://" + IP + "/transporte/recorrido/recorridos.json";
    public static final String URL_FINALIZAR_SERVICIO = "https://" + IP + "/transporte/servicio/finalizarServicio.php";
    public static final String URL_ULTIMO_SERVICIO = "https://" + IP + "/transporte/servicio/getLastPoint.php";
    public static final String URL_GET_SERVICIO = "https://" + IP + "/transporte/servicio/getServicio.php";
    public static final String URL_SERVICIO_ALUMNOS = "https://" + IP + "/transporte/servicio/getServiciosAlumno.php";
    public static final String URL_SERVICIO_CHOFER = "https://" + IP + "/transporte/servicio/getServiciosChofer.php";
    public static final String URL_INICIAR_SERVICIO = "https://" + IP + "/transporte/servicio/insertarServicio.php";
    public static final String URL_PASAJERO_REGISTRAR_SERVICIO = "https://" + IP + "/transporte/servicio/registrarPasajero.php";
    public static final String URL_ACTUALIZAR_SERVICIO = "https://" + IP + "/transporte/servicio/updatePosition.php";
    public static final String URL_PASAJERO_INFO_SERVICIO = "https://" + IP + "/transporte/servicio/getPasajeroServicio.php";
    public static final String URL_SERVICIOS_BY_FECHA = "https://" + IP + "/transporte/servicio/getServicios.php";

    //NOTICIAS
    public static final String URL_IMAGE_NOTICIA = "https://sis.bienestar.unse.edu.ar/api/general/noticia/img/%s";
    public static final String URL_NOTICIA_BY_ID = "https://sis.bienestar.unse.edu.ar/api/general/noticia/get.php";
    public static final String URL_LISTA_NOTICIA = "https://sis.bienestar.unse.edu.ar/api/general/noticia/getNoticias.php";
    public static final String URL_AGREGAR_NOTICIA = "https://sis.bienestar.unse.edu.ar/api/general/noticia/insertar.php";
    public static final String URL_ELIMINAR_NOTICIA = "https://sis.bienestar.unse.edu.ar/api/general/noticia/eliminar.php";
    public static final String URL_ACTUALIZAR_NOTICIA = "https://sis.bienestar.unse.edu.ar/api/general/noticia/actualizar.php";
    public static final String URL_NOTICIA_IMAGE = "https://sis.bienestar.unse.edu.ar/api/general/noticia/uploadImage.php";

    //UAPU
    public static final String URL_DOCTORES_LISTA = "https://" + IP + "/uapu/doctor/getDoctors.php";
    public static final String URL_ATENCION_DIARIA = "https://" + IP + "/uapu/atencion/getByDay.php";
    public static final String URL_ATENCION_HISTORICA = "https://" + IP + "/uapu/atencion/getAll.php";
    public static final String URL_ATENCION_NUEVA = "https://" + IP + "/uapu/atencion/insertarAtencion.php";
    public static final String URL_SERVICIOS = "https://" + IP + "/uapu/servicio/getServicios.php";
    public static final String URL_EDIT_SERVICIOS = "https://" + IP + "/uapu/servicio/actualizar.php";
    public static final String URL_BAJA_SERVICIOS = "https://" + IP + "/uapu/servicio/eliminar.php";
    public static final String URL_TURNOS_DIA_UAPU = "https://" + IP + "/uapu/turno/getAllByDay.php";
    public static final String URL_TURNOS_HIST_UAPU = "https://" + IP + "/uapu/turno/getAll.php";
    public static final String URL_UAPU_HORARIO = "https://" + IP + "/uapu/turno/getTurnoHorarios.php";
    public static final String URL_MEDICAM_DAY = "https://" + IP + "/uapu/medicamento/getByDay.php";
    public static final String URL_MEDICAM_UPDATE = "https://" + IP + "/uapu/medicamento/actualizar.php";
    public static final String URL_MEDICAM_ALL = "https://" + IP + "/uapu/medicamento/getAll.php";
    public static final String URL_MEDICAM_ESTADISTICA = "https://" + IP + "/uapu/medicamento/getEstadistica.php";
    public static final String URL_INFO_CONSULTA = "https://" + IP + "/uapu/atencion/getConsulta.php";
    public static final String URL_PACIENTES_DIARIA = "https://" + IP + "/uapu/turno/getTurnoByDay.php";
    public static final String URL_PACIENTES_HISTORICO = "https://" + IP + "/uapu/turno/getTurnos.php";
    public static final String URL_ACTUALIZAR_CONSULTA = "https://" + IP + "/uapu/turno/actualizar.php";
    public static final String URL_TURNO_UAPU_HORARIO = "https://" + IP + "/uapu/turno/horarios.json";
    public static final String URL_TURNO_NUEVO = "https://" + IP + "/becas/turno/insertar.php";
    public static final String URL_TURNO_UAPU_NUEVO = "https://" + IP + "/uapu/turno/insertar.php";
    public static final String URL_TURNO_ESTADISTICA = "https://" + IP + "/uapu/turno/getEstadistica.php";
    public static final String URL_CERTIFICADOS = "https://" + IP + "/uapu/certificado/getCertificados.php";
    public static final String URL_CERTIFICADOS_NUEVO = "https://" + IP + "/uapu/certificado/insertar.php";

    //COMEDOR
    private static final String IP_2 = "bienestar.unse.edu.ar";
    public static final String URL_MENU_NUEVO = "http://" + IP_2 + "/bienestar/comedor/menu/insertarMenu.php";
    public static final String URL_MENU_ACTUALIZAR = "http://" + IP_2 + "/bienestar/comedor/menu/actualizarMenu.php";
    public static final String URL_MENU_LISTADO= "http://" + IP_2 + "/bienestar/comedor/menu/getMenus.php";
    public static final String URL_USUARIO_INSERTAR_COMEDOR = "http://" + IP_2 + "/bienestar/comedor/beneficiario/insertarUsuario.php";
    public static final String URL_USUARIO_ACTUALIZAR_COMEDOR = "http://" + IP_2 + "/bienestar/comedor/beneficiario/actualizarUsuario.php";
    public static final String URL_USUARIO_ELIMINAR_COMEDOR = "http://" + IP_2 + "/bienestar/comedor/beneficiario/eliminarUsuario.php";
    public static final String URL_RESERVA_USUARIO = "http://" + IP_2 + "/bienestar/comedor/reserva/getReservaByUser.php";
    public static final String URL_USUARIOS_LISTA_COMEDOR = "http://" + IP_2 + "/bienestar/comedor/beneficiario/getUsuarios.php";
    public static final String URL_ESTADISTICA_COMEDOR = "http://" + IP_2 + "/bienestar/comedor/getReportes.php";
    public static final String URL_SUGERENCIA_LISTA = "http://" + IP_2 + "/bienestar/comedor/sugerencia/getSugerencias.php";
    public static final String URL_RESERVA_HOY = "http://" + IP_2 + "/bienestar/comedor/reserva/getReservaByDay.php";
    public static final String URL_DATOS_RESERVA_MENSUAL = "http://" + IP_2 + "/bienestar/comedor/getReporteMensualReservas.php";
    public static final String URL_RESERVA_HISTORIAL = "http://" + IP_2 + "/bienestar/comedor/reserva/getReservas.php";
    public static final String URL_MENU_HOY = "http://" + IP_2 + "/bienestar/comedor/menu/getMenuToday.php";
    public static final String URL_RESERVA_ACTUALIZAR = "http://" + IP_2 + "/bienestar/comedor/reserva/actualizarReserva.php";
    public static final String URL_MENU_TERMINAR = "http://" + IP_2 + "/bienestar/comedor/menu/terminarMenu.php";
    public static final String URL_MENU_RESTRINGIR = "http://" + IP_2 + "/bienestar/comedor/menu/restringirMenu.php";
    public static final String URL_USUARIO_BY_ID_REDUCE_COMEDOR = "http://" + IP_2 + "/bienestar/usuario/getUserReduce.php";
    public static final String URL_RESERVA_INSERTAR_ESPECIAL = "http://" + IP_2 + "/bienestar/comedor/reserva/insertarReservaEspecial.php";
    public static final String URL_RESERVA_INSERTAR_ALUMNO = "http://" + IP_2 + "/bienestar/comedor/reserva/insertarReservaAlumno.php";

    public static final String URL_USUARIO_BY_ID_COMEDOR = "http://" + IP_2 + "/bienestar/comedor/beneficiario/getUsuario.php";






    public static final String URL_FECHAS_VALIDA = "https://" + IP + "/becas/fecha/getFechaInvalidate.php";

    public static final long SECONS_TIMER = 15000;

    //CARPETAS
    public static final String FOLDER = "BIENESTAR_ESTUDIANTIL/";
    public static final String FOLDER_CREDENCIALES = FOLDER + "CREDENCIALES/";

    public static String[] facultad = {"FAyA", "FCEyT", "FCF", "FCM", "FHCSyS"};
    public static String[] faya = {"Ingeniería Agronómica", "Ingeniería en Alimentos", "Licenciatura en Biotecnología",
            "Licenciatura en Química", "Profesorado en Química", "Tecnicatura en Apicultura"};
    public static String[] fceyt = {"Ingeniería Civil", "Ingeniería Electromecánica", "Ingeniería Electrónica",
            "Ingeniería Eléctrica", "Ingeniería en Agrimensura", "Ingeniería Hidráulica",
            "Ingeniería Industrial", "Ingeniería Vial", "Licenciatura en Hidrología Subterránea",
            "Licenciatura en Matemática", "Licenciatura en Sistemas de Información",
            "Profesorado en Física", "Profesorado en Informática", "Profesorado en Matemática",
            "Programador Universitario en Informática", "Tecnicatura Universitaria Vial",
            "Tecnicatura Universitaria en Construcciones",
            "Tecnicatura Universitaria en Hidrología Subterránea",
            "Tecnicatura Universitaria en Organización y Control de la Producción"};
    public static String[] fcf = {"Ingeniería Forestal", "Ingeniería en Industrias Forestales",
            "Licenciatura en Ecología y Conservación del Ambiente",
            "Licenciatura en Biología",
            "Tecnicatura Universitaria Fitosanitarista",
            "Tecnicatura Universitaria en Viveros y Plantaciones Forestales",
            "Tecnicatura Universitaria en Aserraderos y Carpintería Industrial"};

    public static String[] fcm = {"Medicina"};

    public static String[] fhcys = {"Licenciatura en Administración", "Contador Público Nacional",
            "Licenciatura en Letras", "Licenciatura en Sociología", "Licenciatura en Enfermería",
            "Licenciatura en Educación para la Salud", "Obstetricia", "Licenciatura en Obstetricia",
            "Licenciatura en Filosofía", "Licenciatura en Trabajo Social",
            "Licenciatura en Periodismo", "Profesorado en Educación para la Salud",
            "Tecnicatura Sup. Adm. y Gestión Universitaria",
            "Tecnicatura en Educación Intercultural Bilingue"};

    public static String[] categorias = {"Alumno", "Profesor", "Nodocente", "Egresado",
            "Particular", "Afiliado", "Jubilado", "Otro"};

    public static String[] rangoEdad = {"Menor de 18 años", "19 a 24", "25 a 29", "30 a 34", "35 a 39", "40 a 44", "45 a 49", "50 a 54", "55 a 59", "60 o mas"};


    public static String dataAlumno = "?idU=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
            "&dom=%s&sex=%s&car=%s&fac=%s&anio=%s&leg=%s" +
            "&tipo=%s&mail=%s&tel=%s&barr=%s&fecham=%s&idReg=%s";

    public static String dataProfesor = "?idU=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
            "&dom=%s&sex=%s&tipo=%s&mail=%s&tel=%s" +
            "&prof=%s&fechain=%s&barr=%s&fecham=%s";

    public static String dataEgresado = "?idU=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
            "&dom=%s&sex=%s&tipo=%s&mail=%s&tel=%s" +
            "&prof=%s&fechaeg=%s&barr=%s&fecham=%s";

    public static String dataPartiNoDoc = "?idU=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
            "&dom=%s&sex=%s&tipo=%s&mail=%s&tel=%s&barr=%s&fecham=%s";

    public static int[] getColors() {

        return new int[]{
                ColorTemplate.rgb("#FF1744"),
                ColorTemplate.rgb("#1E88E5"),
                ColorTemplate.rgb("#00BFA5"),
                ColorTemplate.rgb("#00C853"),
                ColorTemplate.rgb("#F57F17"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#DD2C00"),
                ColorTemplate.rgb("#607D8B"),
                ColorTemplate.rgb("#827717"),
                ColorTemplate.rgb("#DCEDC8"),
                ColorTemplate.rgb("#B2DFDB"),
                ColorTemplate.rgb("#B2EBF2"),
                ColorTemplate.rgb("#4FC3F7"),
                ColorTemplate.rgb("#3F51B5"),
                ColorTemplate.rgb("#D1C4E9"),
                ColorTemplate.rgb("#BA68C8"),
                ColorTemplate.rgb("#E91E63"),
                ColorTemplate.rgb("#EF5350"),
                ColorTemplate.rgb("#FFCDD2"),
                ColorTemplate.rgb("#DCE775"),
                ColorTemplate.rgb("#0277BD"),
                ColorTemplate.rgb("#7CB342"),
                ColorTemplate.rgb("#FDD835")
                /* android.graphics.Color.rgb(255, 51, 51),
                 android.graphics.Color.rgb(102, 178, 255),

                 android.graphics.Color.rgb(255, 153, 51),
                 android.graphics.Color.rgb(102, 102, 255),

                 android.graphics.Color.rgb(255, 255, 51),
                 android.graphics.Color.rgb(178, 102, 255),

                 android.graphics.Color.rgb(178, 255, 102),
                 android.graphics.Color.rgb(255, 102, 255),

                 android.graphics.Color.rgb(102, 255, 102),
                 android.graphics.Color.rgb(255, 102, 178),

                 android.graphics.Color.rgb(102, 255, 178),
                 android.graphics.Color.rgb(192, 192, 192),
                 android.graphics.Color.rgb(102, 255, 255),


                 android.graphics.Color.rgb(218, 165, 32),
                 android.graphics.Color.rgb(70, 130, 180),

                 android.graphics.Color.rgb(128, 128, 0),
                 android.graphics.Color.rgb(139, 0, 139),

                 android.graphics.Color.rgb(0, 100, 0),
                 android.graphics.Color.rgb(255, 228, 196),
                 android.graphics.Color.rgb(47, 79, 79)*/};

    }


    public static void changeColorDrawable(ImageView view, Context context, int color) {
        DrawableCompat.setTint(
                DrawableCompat.wrap(view.getDrawable()),
                ContextCompat.getColor(context, color));
    }

    public static String getStringCamel(String string) {
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(string);
        StringBuilder resp = new StringBuilder();
        while (matcher.find()) {
            if (matcher.group(0).length() > 1)
                resp.append(matcher.group(0).charAt(0)).append(matcher.group(0).substring(1).toLowerCase()).append(" ");
            else
                resp.append(matcher.group(0));
        }
        return resp.toString();
    }


    public static void showToast(Context c, String msj) {
        Toast.makeText(c, msj, Toast.LENGTH_LONG).show();
    }

    public static void showLog(String title, String msj) {
        Log.e(title, msj);
    }

    public static Bitmap resize(Bitmap bitmapToScale, float newWidth, float newHeight) {
        if (bitmapToScale == null)
            return null;
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();

        Matrix matrix = new Matrix();

        matrix.postScale(newWidth / width, newHeight / height);

        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
    }

   /* public static void showCustomToast(Activity activity, Context context, String text, int icon) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(icon);
        TextView text2 = layout.findViewById(R.id.text);
        text2.setText(text);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 30);
        toast.setDuration(Toast.LENGTH_LONG + 4);
        toast.setView(layout);
        toast.show();
    }*/


    //Metodo para saber si un permiso esta autorizado o no
    public static boolean isPermissionGranted(Context ctx, String permision) {
        int permission = ActivityCompat.checkSelfPermission(
                ctx,
                permision);
        return permission == PackageManager.PERMISSION_GRANTED;

    }

    public static String getTwoDecimals(double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }

    public static int getColorIMC(String imc) {
        double auxImc = Double.parseDouble(imc);
//        String aux = getTwoDecimals(iMC);
//        double auxImc = Double.parseDouble(aux);
        if (auxImc <= 15) {
            return R.color.colorBrown;
        } else if (auxImc > 15 && auxImc <= 15.9) {
            return R.color.colorLightBlue;
        } else if (auxImc >= 16 && auxImc <= 18.4) {
            return R.color.colorBlue;
        } else if (auxImc >= 18.5 && auxImc <= 24.9) {
            return R.color.colorGreen;
        } else if (auxImc >= 25 && auxImc <= 29.9) {
            return R.color.colorOrange;
        } else if (auxImc >= 30 && auxImc <= 34.9) {
            return R.color.colorRed;
        } else if (auxImc >= 35 && auxImc <= 39.9) {
            return R.color.colorPrimaryDark;
        } else if (auxImc >= 40) {
            return R.color.colorPrimaryDark;
        }

        return R.color.colorRed;
    }

    public static String obtainEstado(String imc) {
        double auxImc = Double.parseDouble(imc);
//        String aux = getTwoDecimals(iMC);
//        double auxImc = Double.parseDouble(aux);
        String estado = " ";
        if (auxImc <= 15) {
            estado = "Delgadez muy severa";
        } else if (auxImc > 15 && auxImc <= 15.9) {
            estado = "Delgadez severa";
        } else if (auxImc >= 16 && auxImc <= 18.4) {
            estado = "Delgadez";
        } else if (auxImc >= 18.5 && auxImc <= 24.9) {
            estado = "Peso saludable";
        } else if (auxImc >= 25 && auxImc <= 29.9) {
            estado = "Sobrepeso";
        } else if (auxImc >= 30 && auxImc <= 34.9) {
            estado = "Obesidad moderada";
        } else if (auxImc >= 35 && auxImc <= 39.9) {
            estado = "Obesidad severa";
        } else if (auxImc >= 40) {
            estado = "Obesidad mórbida";
        }

        return estado;
    }

    public static String obtainIMC(String peso, String altura) {
        String imc = "DESCONOCIDO", aux = "DESCONOCIDO";
        double auximc = 0;
        if (!peso.equals(" ") && !altura.equals(" ")) {
            double pso = Double.parseDouble(peso);
            double alt = Double.parseDouble(altura);
            alt = alt / 100;
            auximc = pso / (alt * alt);
            aux = getTwoDecimals(auximc);
        }
        return aux;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getAppName(Context context, ComponentName name) {
        try {
            ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(
                    name, PackageManager.GET_META_DATA);
            return activityInfo.loadLabel(context.getPackageManager()).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setFont(Context context, ViewGroup view, String font) {
        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), font);
        fontChanger.replaceFonts(view);
    }

    private static void crearArchivoProvisorio(InputStream inputStream, File file) {

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getDirectoryPath(boolean ext, Context context) {
        String directory_path = (ext ? Environment.getExternalStorageDirectory().getPath() : context.getCacheDir()) + "/BIENESTAR_ESTUDIANTIL/";
        File directorio = new File(directory_path);
        if (!directorio.exists())
            directorio.mkdirs();
        return directory_path;
    }

    public static Object[] exist(Archivo archivo, Context context) {
        File file = new File(getDirectoryPath(true, context) + archivo.getNombreArchivo());
        Object[] a = new Object[2];
        a[0] = file.exists();
        a[1] = file.exists() ? file.lastModified() : 0;

        return a;
    }

    public static void createPDF(Context context) {
        File file = null;
        Document document = null;
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BIENESTAR/";
        try {
            File directorio = new File(directory_path);
            if (!directorio.exists())
                directorio.mkdirs();
            in = assetManager.open("ficha_deportes_label.pdf");
            file = new File(directory_path, "deportesProvisorio.pdf");
            crearArchivoProvisorio(in, file);

            File src = new File(directory_path, "deportesProvisorio.pdf");
            File des = new File(directory_path, "ficha_deportes.pdf");
            if (!src.exists()) {
                try {
                    InputStream is = context.getAssets().open("ficha_deportes_label.pdf");
                    byte[] buffer = new byte[1024];
                    is.read(buffer);
                    is.close();
                    FileOutputStream fos = new FileOutputStream(src);
                    fos.write(buffer);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(src), new PdfWriter(des));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
            form.getField("DNI").setValue("4000000");
            pdfDocument.close();
            src.delete();
            //document = new Document(pdfDocument);
            //document.close();
            /*document.add(getText("SYSTOCK", 15, true));
            document.add(getText("Sistema de Gestión de Mercadería, Facturación y Gestión de Clientes", 12, true));
            document.add(getText("----------------------------------------------------------------------------------------------------------------------", 11, true));
            document.add(getText("Datos del Pedido", 12, false));
            document.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createReportMensualPDF(List<Usuario> usuarios, Context context, String mes,
                                              int dias, HashMap<String, Integer[]> reservasAlumnosDias,
                                              Bitmap graficoBarra, Bitmap graficoTorta, int[] reserva) {
        Document document = null;
        FileOutputStream fileOutputStream = null;
        try {
            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BIENESTAR_ESTUDIANTIL/COMEDOR/";
            File file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            File filePath = new File(getNameFile(directory_path, 2, mes.toUpperCase()));
            if (!file.exists())
                file.createNewFile();
            fileOutputStream = new FileOutputStream(filePath);
            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            document = new Document(new PdfDocument(pdfWriter));
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            addHeadder(document, font, context, "COMEDOR UNIVERSITARIO");
            document.add(getText(String.format("REPORTE MENSUAL: %s", mes.toUpperCase()), 10, true).setFont(font));

            document.add(getText(String.format("TOTAL DE PORCIONES ENTREGADAS : %s", reserva[0]), 10, false).setFont(font));
            document.add(getText(String.format("TOTAL DE PORCIONES ESPECIALES : %s", reserva[1]), 10, false).setFont(font));
            document.add(getText(String.format("TOTAL DE PORCIONES : %s", reserva[0] + reserva[1]), 10, false).setFont(font));

            document.add(getText(String.format("TOTAL DE RESERVAS : %s", reserva[2]), 9, false).setFont(font));
            document.add(getText(String.format("TOTAL DE RESERVAS ESPECIALES: %s", reserva[3]), 9, false).setFont(font));

            if (graficoBarra != null) {
                document.add(getText("GRÁFICA TOTAL DE RESERVAS", 10, true).setFont(font));
                Image image = loadImage(context, graficoBarra, 250, 238);
                document.add(image.setTextAlignment(TextAlignment.CENTER));


            }

            if (graficoTorta != null) {
                document.add(getText("GRÁFICA DE RESERVAS POR FACULTAD", 10, true).setFont(font));
                Image image = loadImage(context, graficoTorta, 250, 229);
                document.add(image.setTextAlignment(TextAlignment.CENTER));
            }

            document.add(getText("TABLA MENSUAL", 10, true).setFont(font));
            UnitValue[] columnas = new UnitValue[2 + dias + 1];
            columnas[0] = new UnitValue(UnitValue.PERCENT, 18f);
            columnas[1] = new UnitValue(UnitValue.PERCENT, 6f);
            for (int i = 2; i < 11; i++) {
                columnas[i] = new UnitValue(UnitValue.PERCENT, 1.6f);
            }
            for (int i = 11; i < (dias + 2); i++) {
                columnas[i] = new UnitValue(UnitValue.PERCENT, 2f);
            }
            columnas[columnas.length - 1] = new UnitValue(UnitValue.PERCENT, 3f);
            Table table = new Table(columnas)
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10)
                    .setMarginBottom(10);
            table.addHeaderCell(createCell("Apellido y Nombre", true, 8).setFont(font));
            table.addHeaderCell(createCell("D.N.I", true, 8).setFont(font));
            for (int i = 1; i <= dias; i++) {
                table.addHeaderCell(createCell(String.valueOf(i), true, 8).setFont(font));
            }
            table.addHeaderCell(createCell("T", true, 8).setFont(font));
            for (Usuario usuario : usuarios) {
                table.addCell(getText(String.format("%s %s", usuario.getApellido(), usuario.getNombre()),
                        8, true));
                table.addCell(getText(String.valueOf(usuario.getIdUsuario()), 8, true));
                int total = 0;
                Integer[] reservas = reservasAlumnosDias.get(String.valueOf(usuario.getIdUsuario()));
                for (int i = 1; i <= dias; i++) {
                    if (reservas != null) {
                        if (reservas[i - 1] == 1) {
                            table.addCell(createCell("X", true, 7));
                            total++;
                        } else {
                            table.addCell(createCell(" ", true, 7));
                        }
                    } else {
                        table.addCell(createCell(" ", true, 7));
                    }
                }
                table.addCell(createCell(String.valueOf(total), true, 7));
            }
           /* table.addCell(createCell("TOTAL", true, 7).setFont(font));
            table.addCell(createCell("     ", true, 7).setFont(font));
            for (int i = 1; i <= dias; i++) {
                table.addCell(createCell(String.valueOf(new Random().nextInt(30)), true, 7));
            }*/
            document.add(table);

           /* document.add(getText("TOTAL DE RESERVAS POR DÍA", 10, true).setFont(font));
            columnas = new UnitValue[7];

            for (int i = 1; i < 7; i++) {
                columnas[i] = new UnitValue(UnitValue.PERCENT, 3f);
            }
            table = new Table(columnas)
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10)
                    .setMarginBottom(10);
            for (int i = 1; i <= 7; i++) {
                table.addCell(createCell(String.valueOf(i), true, 8).setFont(font));
                //table.addHeaderCell(createCell(String.valueOf(i), true, 8).setFont(font));
            }
            for (int i = 1; i <= 7; i++) {
                table.addCell(createCell(" 10 ", true, 8).setFont(font));
                //table.addHeaderCell(createCell(String.valueOf(i), true, 8).setFont(font));
            }
            for (int i = 1; i <= 7; i++) {
                table.addCell(createCell(String.valueOf(i), true, 8).setFont(font));
            }
            document.add(table);

           */


            addFooter(document, font);
            document.close();


        } catch (IOException e) {
            e.printStackTrace();
            showToast(context, context.getString(R.string.errorPDF));
        }

    }

    public static Bitmap getBitmapFromView(Activity context, View layout) {
        Bitmap returnBitmap = null;
        if (layout != null) {
            // create bitmap screen capture
            // View v1 = context.getWindow().getDecorView().getRootView();
            //v1.setDrawingCacheEnabled(true);
            //Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            //v1.setDrawingCacheEnabled(false);

            int totalHeight = layout.getHeight();
            int totalWidth = layout.getWidth();
            float percent = 0.99f;//use this value to scale bitmap to specific size

            returnBitmap = Bitmap.createBitmap(totalWidth,totalHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(returnBitmap);
            canvas.scale(percent, percent);
            layout.draw(canvas);
            return returnBitmap;


/*

            FileStorageManager fileStorageManager = new FileStorageManager(context.getApplicationContext());
            fileStorageManager.setFileName("pic1.jpg");
            fileStorageManager.setFolderName("pdf");
            FileOutputStream outputStream = null;
            try {
                File file = new File(Environment
                        .getExternalStorageDirectory().toString(), "pic1.jpg");
                if (!file.exists())
                    file.mkdir();
                outputStream = new FileOutputStream(file);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();
                file.delete();
                FileStorageManager.saveBitmap(context, "pdf","pic1.jpg", bitmap, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                returnBitmap = FileStorageManager.getBitmap(context.getApplicationContext(),
                        "pdf","pic1", false);
            }*/


            /*if (layout.getMeasuredHeight() < 0) {
                returnBitmap = Bitmap.createBitmap(layout.getLayoutParams().width,
                        layout.getLayoutParams().height, Bitmap.Config.ARGB_8888);
            } else {
                //layout.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                returnBitmap = Bitmap.createBitmap(layout.getMeasuredWidth(),
                        layout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(returnBitmap);
            canvas.drawColor(Color.TRANSPARENT);
            if (layout.getMeasuredHeight() > 0)
                layout.layout(layout.getLeft(), layout.getTop(), layout.getRight(), layout.getBottom());
            else
                layout.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredWidth());
            layout.draw(canvas);*/
        }
        return returnBitmap;
    }

    public static void createPDF(List<Usuario> usuarios, Context context) {
        Document document = null;
        FileOutputStream fileOutputStream = null;
        try {
            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BIENESTAR_ESTUDIANTIL/COMEDOR/";
            File file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            File filePath = new File(getNameFile(directory_path, 2, ""));
            if (!file.exists())
                file.createNewFile();
            fileOutputStream = new FileOutputStream(filePath);
            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            document = new Document(new PdfDocument(pdfWriter));
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            addHeadder(document, font, context, "COMEDOR UNIVERSITARIO");
            document.add(getText("LISTADO DE USUARIOS", 10, true).setFont(font));
            Table table = new Table(new UnitValue[]{
                    new UnitValue(UnitValue.PERCENT, 20f),
                    new UnitValue(UnitValue.PERCENT, 6f),
                    new UnitValue(UnitValue.PERCENT, 10f),
                    new UnitValue(UnitValue.PERCENT, 10f),
                    new UnitValue(UnitValue.PERCENT, 12f),
                    new UnitValue(UnitValue.PERCENT, 10f),
                    new UnitValue(UnitValue.PERCENT, 10f),
                    new UnitValue(UnitValue.PERCENT, 10f),
                    new UnitValue(UnitValue.PERCENT, 11f)
            })
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10).setMarginBottom(10);
            table.addHeaderCell(createCell("Apellido y Nombre", true, 8).setFont(font));
            table.addHeaderCell(createCell("D.N.I", true, 8).setFont(font));
            table.addHeaderCell(createCell("Lunes", true, 8).setFont(font));
            table.addHeaderCell(createCell("Martes", true, 8).setFont(font));
            table.addHeaderCell(createCell("Miércoles", true, 8).setFont(font));
            table.addHeaderCell(createCell("Jueves", true, 8).setFont(font));
            table.addHeaderCell(createCell("Viernes", true, 8).setFont(font));
            table.addHeaderCell(createCell("Sábado", true, 8).setFont(font));
            table.addHeaderCell(createCell("Domingo", true, 8).setFont(font));
            for (Usuario usuario : usuarios) {
                table.addCell(getText(String.format("%s %s", usuario.getApellido(), usuario.getNombre()),
                        8, true));
                table.addCell(getText(String.valueOf(usuario.getIdUsuario()), 8, true));
                table.addCell(createCell("          ", true, 9));
                table.addCell(createCell("          ", true, 9));
                table.addCell(createCell("          ", true, 9));
                table.addCell(createCell("          ", true, 9));
                table.addCell(createCell("          ", true, 9));
                table.addCell(createCell("          ", true, 9));
                table.addCell(createCell("          ", true, 9));
            }
            document.add(table);
            addFooter(document, font);
            document.close();


        } catch (IOException e) {
            e.printStackTrace();
            showToast(context, context.getString(R.string.errorPDF));
        }

    }

    public static void createPDFMaraton(List<Maraton> usuarios, Context context) {
        Document document = null;
        FileOutputStream fileOutputStream = null;
        try {
            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BIENESTAR_ESTUDIANTIL/DEPORTES/";
            File file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            File filePath = new File(getNameFile(directory_path, 1, ""));
            if (!file.exists())
                file.createNewFile();
            fileOutputStream = new FileOutputStream(filePath);
            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            document = new Document(new PdfDocument(pdfWriter));
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            addHeadder(document, font, context, "ÁREA DEPORTES");
            document.add(getText("LISTADO DE INSCRIPTOS - 1° MARATÓN VIRTUAL", 10, true).setFont(font));
            Table table = new Table(new UnitValue[]{
                    new UnitValue(UnitValue.PERCENT, 5f),
                    new UnitValue(UnitValue.PERCENT, 12f),
                    new UnitValue(UnitValue.PERCENT, 7f),
                    new UnitValue(UnitValue.PERCENT, 9f),
                    new UnitValue(UnitValue.PERCENT, 13f),
                    new UnitValue(UnitValue.PERCENT, 7f),
                    new UnitValue(UnitValue.PERCENT, 5f),
                    new UnitValue(UnitValue.PERCENT, 4f)
            })
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10).setMarginBottom(10);
            table.addHeaderCell(createCell("Nro", true, 8).setFont(font));
            table.addHeaderCell(createCell("Nombre y Apellido", true, 8).setFont(font));
            table.addHeaderCell(createCell("D.N.I", true, 8).setFont(font));
            table.addHeaderCell(createCell("Teléfono", true, 8).setFont(font));
            table.addHeaderCell(createCell("Mail", true, 8).setFont(font));
            table.addHeaderCell(createCell("Tipo", true, 8).setFont(font));
            table.addHeaderCell(createCell("Edad", true, 8).setFont(font));
            table.addHeaderCell(createCell("KM", true, 8).setFont(font));
            for (Maraton usuario : usuarios) {
                table.addCell(getText(String.format("# %s", usuario.getIdInscripcion()), 9, true));
                table.addCell(getText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()).toUpperCase(),
                        8, true));
                table.addCell(getText(String.valueOf(usuario.getIdUsuario()), 9, true));
                table.addCell(getText(String.valueOf(usuario.getTelefono()), 9, true));
                table.addCell(getText(String.valueOf(usuario.getMail()), 8, true));
                table.addCell(getText(String.valueOf(usuario.getTipo()), 8, true));
                int edad = Utils.getEdad(Utils.getFechaDate(usuario.getFechaNac()));
                table.addCell(getText(String.valueOf(edad), 9, true));
                table.addCell(getText(String.valueOf(usuario.getCarrera()), 9, true));
            }
            document.add(table);
            addFooter(document, font);
            document.close();


        } catch (IOException e) {
            e.printStackTrace();
            showToast(context, context.getString(R.string.errorPDF));
        }

    }

    public static void addHeadder(Document document, PdfFont font, Context context, String area) {
        Image image = loadImage(context, R.drawable.encabezado, 179, 79);
        document.add(image.setTextAlignment(TextAlignment.CENTER));
        document.add(getText("SECRETARÍA DE BIENESTAR ESTUDIANTIL", 12, true).setFont(font));
        document.add(getText("SISTEMA DE GESTIÓN - " + area, 11, true).setFont(font));

    }

    public static void addFooter(Document document, PdfFont font) {
        document.add(getText("Generado desde App Bienestar Estudiantil - UNSE", 10, false)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFont(font));
        document.add(getText("Fecha de Generación: " +
                getFechaOrder(new Date(System.currentTimeMillis())), 10, false)
                .setTextAlignment(TextAlignment.RIGHT));


    }

    private static Image loadImage(Context context, Bitmap bitmap, int width, int height) {
        bitmap = resize(bitmap, width, height);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = null;
        byte[] bytes = stream.toByteArray();
        image = new Image(ImageDataFactory.create(bytes));
        return image;
    }

    private static Image loadImage(Context context, int img, int width, int height) {
        Drawable drawable = context.getResources().getDrawable(img);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        // bitmap = resize(bitmap, width, height);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
        Image image = null;
        byte[] bytes = stream.toByteArray();
        image = new Image(ImageDataFactory.create(bytes));
        return image;
    }


    private static Paragraph getText(String text, float size, boolean center, Color color) {
        return center ? new Paragraph(text).setFontColor(color).setFontSize(size).setTextAlignment(TextAlignment.CENTER)
                : new Paragraph(text).setFontSize(size);
    }

    public static String getNameFile(String directory_path, int tipo, String extra) {
        String data = "";
        switch (tipo) {
            case 1:
                data = "LISTADO_INSCRIPTOS_MARATON_";
                break;
            case 2:
                data = "LISTADO_USUARIOS_COMEDOR_";
                break;
        }
        return String.format("%s%s%s%s", directory_path, data, extra,
                getFechaName(new Date(System.currentTimeMillis())) + ".pdf");

    }

    private static Cell createCell(String text, boolean center, int size) {
        return center ? new Cell().setPadding(0.8f)
                .add(new Paragraph(text).setFontSize(size)
                        .setMultipliedLeading(1)).setTextAlignment(TextAlignment.CENTER)
                : new Cell().setPadding(0.8f)
                .add(new Paragraph(text).setFontSize(size)
                        .setMultipliedLeading(1));
    }


    private static Paragraph getText(String text, float size, boolean center) {
        return center ? new Paragraph(text).setFontSize(size).setTextAlignment(TextAlignment.CENTER)
                : new Paragraph(text).setFontSize(size);
    }

    public static Date getFechaDate(String fecha) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaI = null;
        if (fecha != null)
            try {
                fechaI = simpleDateFormat.parse(fecha);

            } catch (ParseException e) {
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fechaI = simpleDateFormat.parse(fecha);

                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }

        return fechaI;

    }

    public static Date getFechaDateWithHour(String fecha) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaI = null;
        if (fecha != null)
            try {
                fechaI = simpleDateFormat.parse(fecha);

            } catch (ParseException e) {
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    fechaI = simpleDateFormat.parse(fecha);

                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }

        return fechaI;

    }

    public static String getHora(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String value = "";
        if (cal.get(Calendar.HOUR_OF_DAY) <= 9) {
            value = "0" + cal.get(Calendar.HOUR_OF_DAY);
        } else {
            value = "" + cal.get(Calendar.HOUR_OF_DAY);
        }
        value = value + ":";
        if (cal.get(Calendar.MINUTE) <= 9) {
            value = value + "0" + cal.get(Calendar.MINUTE);
        } else {
            value = value + "" + cal.get(Calendar.MINUTE);
        }
        return value;


    }

    public static String getHoraWithSeconds(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String value = "";
        if (cal.get(Calendar.HOUR_OF_DAY) <= 9) {
            value = "0" + cal.get(Calendar.HOUR_OF_DAY);
        } else {
            value = "" + cal.get(Calendar.HOUR_OF_DAY);
        }
        if (cal.get(Calendar.MINUTE) <= 9) {
            value = value + "0" + cal.get(Calendar.MINUTE);
        } else {
            value = value + "" + cal.get(Calendar.MINUTE);
        }
        if (cal.get(Calendar.SECOND) <= 9) {
            value = value + "0" + cal.get(Calendar.SECOND);
        } else {
            value = value + "" + cal.get(Calendar.SECOND);
        }
        return value;


    }

    public static String getFechaName(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String mesS, diaS, minutosS, segS, horasS;
        int mes = cal.get(Calendar.MONTH) + 1;
        if (mes < 10) {
            mesS = "0" + mes;
        } else
            mesS = String.valueOf(mes);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        if (dia < 10) {
            diaS = "0" + dia;
        } else
            diaS = String.valueOf(dia);

        int minutos = cal.get(Calendar.MINUTE);
        if (minutos < 10) {
            minutosS = "0" + minutos;
        } else
            minutosS = String.valueOf(minutos);

        int seg = cal.get(Calendar.SECOND);
        if (seg < 10) {
            segS = "0" + seg;
        } else
            segS = String.valueOf(seg);

        int horas = cal.get(Calendar.HOUR_OF_DAY);
        if (horas < 10)
            horasS = "0" + horas;
        else
            horasS = String.valueOf(horas);


        String value = cal.get(Calendar.YEAR) + "-" + mesS + "-"
                + diaS + " " + horasS + ":" +
                minutosS + ":" + segS;

        return value;

    }


    public static String getBirthday(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String mesS, diaS;
        int mes = cal.get(Calendar.MONTH) + 1;
        if (mes < 10) {
            mesS = "0" + mes;
        } else
            mesS = String.valueOf(mes);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        if (dia < 10) {
            diaS = "0" + dia;
        } else
            diaS = String.valueOf(dia);
        String value = diaS + "/" + mesS + "/" + cal.get(Calendar.YEAR);

        return value;

    }

    public static String getFechaNameWithinHour(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String mesS, diaS;
        int mes = cal.get(Calendar.MONTH) + 1;
        if (mes < 10) {
            mesS = "0" + mes;
        } else
            mesS = String.valueOf(mes);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        if (dia < 10) {
            diaS = "0" + dia;
        } else
            diaS = String.valueOf(dia);
        String value = cal.get(Calendar.YEAR) + "-" + mesS + "-"
                + diaS;

        return value;

    }

    public static String getDayWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "Lunes";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Miércoles";
            case Calendar.THURSDAY:
                return "Jueves";
            case Calendar.FRIDAY:
                return "Viernes";
            case Calendar.SATURDAY:
                return "Sábado";
            case Calendar.SUNDAY:
                return "Domingo";
        }
        return "";
    }

    public static String getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String mes = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        char in = mes.charAt(0);

        return in + mes.substring(1);
    }

    public static String getMonth(int date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.set(Calendar.MONTH, date - 1);
        String mes = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        char in = mes.charAt(0);
        return String.valueOf(in).toUpperCase() + mes.substring(1);
    }

    public static boolean isDateHabilited(Calendar calendar) {

        if (getDayWeek(calendar.getTime()).equals("Sábado") || getDayWeek(calendar.getTime()).equals("Domingo")) {
            return true;
        }
        return false;
    }

    public static int getEdad(Date fechaNac) {
        Date hoy = new Date(System.currentTimeMillis());
        long tiempo = hoy.getTime() - fechaNac.getTime();
        double years = tiempo / 3.15576e+10;
        int age = (int) Math.floor(years);
        return age;
    }

    public static void changeColor(Drawable drawable, Context mContext, int colorNo) {
        if (drawable instanceof ShapeDrawable)
            ((ShapeDrawable) drawable).getPaint().setColor(ContextCompat.getColor(mContext, colorNo));
        else if (drawable instanceof GradientDrawable)
            ((GradientDrawable) drawable).setColor(ContextCompat.getColor(mContext, colorNo));
        else if (drawable instanceof ColorDrawable)
            ((ColorDrawable) drawable).setColor(ContextCompat.getColor(mContext, colorNo));
    }


    public static String limpiarAcentos(String cadena) {
        String limpio = null;
        if (cadena != null) {
            String valor = cadena;
            valor = valor.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        }
        return limpio;
    }

    public static String convertImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] img = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(img, Base64.DEFAULT);

    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromUri(Context context, Uri uri) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int leng = 0;
            byte[] buf = new byte[1024];
            while (((leng = inputStream.read(buf)) != -1)) {
                byteArrayOutputStream.write(buf, 0, leng);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null;

    }

    public static String getTipoUser(int i) {
        switch (i) {
            case 1:
                return "Alumno";
            case 2:
                return "Profesor";
            case 3:
                return "Nodocente";
            case 4:
                return "Egresado";
            case 5:
                return "Particular";
        }
        return "";
    }

    public static String getFechaFormat(String fechaRegistro) {
        Calendar calendar = new GregorianCalendar();
        Date date = getFechaDateWithHour(fechaRegistro);
        if (date != null) {
            calendar.setTime(date);
            String dia = getDayWeek(date);
            String mes = getMonth(date);
            String anio = String.valueOf(calendar.get(Calendar.YEAR));
            String minutosS, segS, horasS;
            int minutos = calendar.get(Calendar.MINUTE);
            if (minutos < 10) {
                minutosS = "0" + minutos;
            } else
                minutosS = String.valueOf(minutos);

            int seg = calendar.get(Calendar.SECOND);
            if (seg < 10) {
                segS = "0" + seg;
            } else
                segS = String.valueOf(seg);

            int horas = calendar.get(Calendar.HOUR_OF_DAY);
            if (horas < 10)
                horasS = "0" + horas;
            else {
                horasS = String.valueOf(horas);
            }
            String hora = String.format("%s:%s:%s", horasS, minutosS, segS);
            String fecha = String.format("%s, %s de %s de %s - %s", dia, calendar.get(Calendar.DAY_OF_MONTH), mes, anio, hora);
            return fecha;
        }
        return "NO FECHA";

    }

    public static void resetData(Context context) {
        new UsuarioViewModel(context).deleteAll();
        new EgresadoViewModel(context).deleteAll();
        new ProfesorViewModel(context).deleteAll();
        new AlumnoViewModel(context).deleteAll();
        new RolViewModel().deleteAll();
        FileStorageManager.deleteAll(0);
    }

    public static String getExtension(String nombreArchivo) {
        int index = nombreArchivo.lastIndexOf(".");
        return index != -1 ? nombreArchivo.substring(index + 1) : "";
    }

    public static int getColorExtension(String ext) {
        ext = ext.toUpperCase();
        switch (ext) {
            case "PNG":
            case "JPG":
            case "JPEG":
                return R.color.colorBrown;
            case "PDF":
                return R.color.colorRed;
            case "DOC":
            case "DOCX":
                return R.color.colorBlue;
            case "PPT":
                return R.color.colorOrange;
            case "ZIP":
            case "RAR":
                return R.color.colorYellow;
            case "XLS":
                return R.color.colorGreen;
            default:
                return R.color.colorPink;
        }
    }

    public static String getSizeFile(Long size) {
        Double sizeD = Double.parseDouble(String.valueOf(size));
        if (sizeD > 1024 * 1024) {
            return String.format("%s %s", new DecimalFormat("#.##").format(sizeD / (1024 * 1024)), "MB");
        } else if (sizeD > 1024) {
            return String.format("%s %s", new DecimalFormat("#.##").format(sizeD / (1024)), "KB");
        } else return String.format("%s %s", sizeD, "B");
    }

    public static RequestBuilder<Drawable> loadPicture(ImageView img, int id) {
        String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, id);
        return Glide.with(img.getContext()).load(URL)

                .apply(new RequestOptions().error(R.drawable.ic_user)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE));

    }

    public static String getFechaOrder(Date date) {

        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
            String mesS, diaS, minutosS, segS, horasS;
            int mes = cal.get(Calendar.MONTH) + 1;
            if (mes < 10) {
                mesS = "0" + mes;
            } else
                mesS = String.valueOf(mes);

            int dia = cal.get(Calendar.DAY_OF_MONTH);
            if (dia < 10) {
                diaS = "0" + dia;
            } else
                diaS = String.valueOf(dia);

            int minutos = cal.get(Calendar.MINUTE);
            if (minutos < 10) {
                minutosS = "0" + minutos;
            } else
                minutosS = String.valueOf(minutos);

            int seg = cal.get(Calendar.SECOND);
            if (seg < 10) {
                segS = "0" + seg;
            } else
                segS = String.valueOf(seg);

            int horas = cal.get(Calendar.HOUR_OF_DAY);
            if (horas < 10)
                horasS = "0" + horas;
            else
                horasS = String.valueOf(horas);

            return String.format("%s/%s/%s - %s:%s:%s", diaS, mesS, cal.get(Calendar.YEAR), horasS, minutosS, segS);
        } else return "NO FECHA";

    }

    public static String getFechaOrderOnly(Date date) {

        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
            String mesS, diaS;
            int mes = cal.get(Calendar.MONTH) + 1;
            if (mes < 10) {
                mesS = "0" + mes;
            } else
                mesS = String.valueOf(mes);

            int dia = cal.get(Calendar.DAY_OF_MONTH);
            if (dia < 10) {
                diaS = "0" + dia;
            } else
                diaS = String.valueOf(dia);


            return String.format("%s/%s/%s", diaS, mesS, cal.get(Calendar.YEAR));
        } else return "NO FECHA";

    }

    public static String getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.get(Calendar.YEAR);

        return String.valueOf(value);
    }

    public static String getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.get(Calendar.DAY_OF_MONTH);

        return String.valueOf(value);
    }


    public static String getMes(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return "Enero";
            case Calendar.FEBRUARY:
                return "Febrero";
            case Calendar.MARCH:
                return "Marzo";
            case Calendar.APRIL:
                return "Abril";
            case Calendar.MAY:
                return "Mayo";
            case Calendar.JUNE:
                return "Junio";
            case Calendar.JULY:
                return "Julio";
            case Calendar.AUGUST:
                return "Agosto";
            case Calendar.SEPTEMBER:
                return "Septiembre";
            case Calendar.OCTOBER:
                return "Octubre";
            case Calendar.NOVEMBER:
                return "Noviembre";
            case Calendar.DECEMBER:
                return "Diciembre";
        }

        return "";
    }

    public static String getFechaOnlyDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String value = cal.get(Calendar.DAY_OF_MONTH) + "/"
                + (cal.get(Calendar.MONTH) + 1) + "/" +
                cal.get(Calendar.YEAR);

        return value;
    }

    public static String getInfoDate(int dia, int mes, int anio) {
        Date fecha = getFechaDateDMA(String.format("%02d-%02d-%02d", dia, mes, anio));
        if (fecha != null) {
            String diaSemana = getDayWeek(fecha);
            return String.format("%s %02d", diaSemana, dia);
        }
        return "";

    }

    public static Date getFechaDateDMA(String fecha) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaI = null;
        if (fecha != null)
            try {
                fechaI = simpleDateFormat.parse(fecha);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        return fechaI;

    }

    public static String getDate(int dia, int mes, int anio) {
        Date fecha = getFechaDateDMA(String.format("%02d-%02d-%02d", dia, mes, anio));
        return getBirthday(fecha);
    }

    public static String getCode(Reserva reserva) {
        String[] dni = new String[String.valueOf(reserva.getIdUsuario()).length()];
        for (int i = 0; i < dni.length; i++) {
            char valor = Utils.encode(String.valueOf(reserva.getIdUsuario()).charAt(i));
            dni[i] = String.valueOf(valor);
        }
        StringBuilder dniModif = new StringBuilder();
        for (int i = 0; i < dni.length; i++) {
            dniModif.append(dni[i]);
        }
        return dniModif.toString();
    }

    public static char encode(char charAt) {
        if (charAt % 2 == 0) {

            switch (charAt) {
                case '0':
                    return 'M';
                case '2':
                    return 'U';
                case '4':
                    return 'T';
                case '6':
                    return 'W';
                case '8':
                    return 'X';
                default:
                    return charAt;
            }

        } else return charAt;
    }

    public static char decode(char charAt) {
        switch (charAt) {
            case 'M':
                return '0';
            case 'U':
                return '2';
            case 'T':
                return '4';
            case 'W':
                return '6';
            case 'X':
                return '8';
            default:
                return charAt;
        }
    }
}

