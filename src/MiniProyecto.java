import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class MiniProyecto {

    private final static String newline = "\n";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner fitz = new Scanner(System.in);
        int n = 0;

        String name, ncampus;
        int camp, id, estat;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://DESKTOP-NT1UVST\\BDATOS:1433;databaseName=BDminiproyect;user=usuarioSQL;password=fitz;";
        Connection con = DriverManager.getConnection(connectionURL);
        System.out.println("Nos conectamos");

        while (n != 7) {

            System.out.println(newline + newline + newline +"----Menú----");
            System.out.println("1.- Insertar Alumno" + newline +
                    "2.- Modificar Alumno" + newline +
                    "3.- Desactivar Alumno" + newline +
                    "4.- Mostrar Alumnos Activos (con campus)" + newline +
                    "5.- Mostrar todos los alumnos (con el nombre del campus al que pertenece" + newline +
                    "6.- Insertar campus" + newline +
                    "7.- Salir");

            System.out.println("Introduce el numero de la opci\u00F3n correspondiente");
            n = fitz.nextInt();


            switch (n) {

                case 1:
                    System.out.println("Ingrese el nombre del Alumno (entre comillas simples)");
                    fitz.nextLine();
                    name = fitz.nextLine();
                    System.out.println("Ingrese el numero del campus");
                    camp = fitz.nextInt();
                    Statement instruccion = con.createStatement();
                    instruccion.execute("exec dbo.alumnosInsertar" + name + "," + camp + "");
                    System.out.println("Alumno Insertado");
                    break;

                case 2:

                    System.out.println("Ingrese el ID del alumno a modificar");
                    id = fitz.nextInt();
                    System.out.println("Ingrese el nombre del alumno modificado (entre comillas simples)");
                    fitz.nextLine();
                    name = fitz.nextLine();
                    System.out.println("Ingrese el numero del campus");
                    camp = fitz.nextInt();
                    System.out.println("Escribe el estatus con un 0 o 1");
                    estat = fitz.nextInt();
                    Statement mod = con.createStatement();
                    mod.execute("exec dbo.alumnosModificar " + id + "," + name + "," + camp + "," + estat + "");
                    System.out.println("Alumno modificado");
                    break;

                case 3:
                    System.out.println("Escribe el ID del alumno a desactivar");
                    id = fitz.nextInt();
                    Statement des = con.createStatement();
                    des.execute("exec dbo.alumnosDesAlum " + id + "");
                    System.out.println("Alumno desactivado");
                    break;

                case 4:
                    System.out.println("Mostrar Alumnos Activos (con # de campus)");
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("exec dbo.alumnosMosAct");

                    while (rs.next()) {
                        int ID = rs.getInt(1);
                        String Nombre = rs.getString(2);
                        int campus = rs.getInt(3);
                        String fecha = rs.getString(4);
                        String estatus = rs.getString(5);
                        System.out.println(ID + " " + Nombre + " " + campus + " " + fecha + " " + estatus);
                    }
                    break;

                case 5:
                    System.out.println("Mostrar todos los alumnos (con el nombre del campus al que pertenece");
                    Statement sta = con.createStatement();
                    ResultSet rsa = sta.executeQuery("exec dbo.alumnosMosCamp");

                    while (rsa.next()) {
                        int ID = rsa.getInt(1);
                        String Nombre = rsa.getString(2);
                        String campus = rsa.getString(3);
                        String fecha = rsa.getString(4);
                        String estatus = rsa.getString(5);
                        System.out.println(ID + " " + Nombre + " " + campus + " " + fecha + " " + estatus);
                    }
                    break;

                case 6:
                    System.out.println("Ingrese el nombre del campus a insertar (entre comillas simples)");
                    fitz.nextLine();
                    ncampus = fitz.nextLine();
                    Statement ingcamp = con.createStatement();
                    ingcamp.execute("exec dbo.campusInsertar" + ncampus + "");

                    break;

                case 7:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Selecciona una opción válida");
            }


        }
    }
}
