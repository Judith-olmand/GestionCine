import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionCine {
    static int[] salaDracula = new int[50];
    static int[] salaAhora = new int[50];
    static int[] salaAvatar = new int[50];
    static int[] salaDragon = new int[50];

    static String[] carteleraClasica = {"Drácula","Ahora me ves"};
    static String[] cartelera3D = {"Avatar","Cómo entrenar a tu dragón" };
    static double precioBase = 8.40;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String horario = "";
        boolean horarioCorrecto = false;

        int asiento;
        boolean asientoCorrecto = false;

        int pelicula = 0;
        boolean peliculaCorrecta = false;

        String cartelera;
        boolean carteleraCorrecta;

        int reserva = 0;
        boolean reservaCorrecta = false;
        int acumulador = 0;

        Proyeccion proyeccionSeleccionada = null;
        int[] salaSeleccionada = null;

        String repetir;
        boolean reservarMas = true;

        iniciarSalas();


        do{
            /**
             * Pregunto al usuario para invocar o no al metodo mostrarCartelera
             */
            carteleraCorrecta = false;
            do {
                System.out.println("¿Quieres ver la cartelera? S/N");
                cartelera = sc.nextLine();
                if (cartelera.equalsIgnoreCase("S")) {
                    mostrarCartelera();
                    carteleraCorrecta = true;
                } else if (cartelera.equalsIgnoreCase("N")) {
                    System.out.println("No se mostrará la cartelera.");
                    carteleraCorrecta = true;
                } else {
                    System.out.println("Por favor, indique correctamente la respuesta.");
                }
            } while (!carteleraCorrecta);

            /**
             * Pregunto al usuario que pelicula quiere ver
             * Si no se indica un numero valido repite la pregunta y muestra un mensaje.
             * Si se introduce texto muestra un error y repite la pregunta.
             */
            do {
                peliculaCorrecta = false;
                try {
                    System.out.println("¿Qué película desea ver?");
                    System.out.println("1. Drácula");
                    System.out.println("2. Ahora me vés");
                    System.out.println("3. Avatar");
                    System.out.println("4. Cómo entrenar a tu dragón");
                    pelicula = sc.nextInt();
                    sc.nextLine();

                    if (pelicula >= 1 && pelicula <= 4) {
                        peliculaCorrecta = true;
                    } else {
                        System.out.println("Elija una película válida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error. Elija una película válida.");
                    sc.nextLine();
                }
            } while (!peliculaCorrecta);

            /**
             * Dependiendo de cual sea, muestra el precio por entrada.
             */
            if (pelicula == 1) {
                proyeccionSeleccionada = new ProyeccionClasica(carteleraClasica[0], precioBase);
                salaSeleccionada = salaDracula;
                System.out.printf("El precio por entrada es de %.2f€\n", proyeccionSeleccionada.calcularPrecio());
            } else if (pelicula == 2) {
                proyeccionSeleccionada = new ProyeccionClasica(carteleraClasica[1], precioBase);
                salaSeleccionada = salaAhora;
                System.out.printf("El precio por entrada es de %.2f€\n", proyeccionSeleccionada.calcularPrecio());
            } else if (pelicula == 3) {
                proyeccionSeleccionada = new Proyeccion3D(cartelera3D[0], precioBase);
                salaSeleccionada = salaAvatar;
                System.out.printf("El precio por entrada es de %.2f€\n", proyeccionSeleccionada.calcularPrecio());
            } else {
                proyeccionSeleccionada = new Proyeccion3D(cartelera3D[1], precioBase);
                salaSeleccionada = salaDragon;
                System.out.printf("El precio por entrada es de %.2f€\n", proyeccionSeleccionada.calcularPrecio());
            }

            /**
             * Pregunto al usuario cuantas entradas quiere reservar.
             * Si el numero es menor que 0 muestra un texto y vuelve a pedirlo
             * Si se introduce texto lanza un error con mensaje y vuelve a pedirlo.
             */
            do {
                reservaCorrecta = false;
                try {
                    System.out.println("¿Cuántos asientos quiere reservar?");
                    reserva = sc.nextInt();
                    sc.nextLine();
                    if (reserva > 0) {
                        reservaCorrecta = true;
                    } else {
                        System.out.println("Error. Indique un número válido");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error. Indique un número válido");
                    sc.nextLine();
                }
            } while (!reservaCorrecta);

            /**
             * Pregunto al usuario que horario quiere ver
             * Dependiendo del horario muestra la sala con los asientos y solicito los asientos a reservar.
             * Si el asiento ya esta reservado o introduce una letra lanza un error con mensaje personalizado y vuelve a pedirlo
             */
            do {
                horarioCorrecto = false;
                System.out.println("¿En que horario quiere ver la película? M/T");
                horario = sc.next();
                sc.nextLine();
                acumulador = 0;
                if (horario.equalsIgnoreCase("M")) {
                    horario = "Mañana";
                    horarioCorrecto = true;
                } else if (horario.equalsIgnoreCase("T")) {
                    horario = "Tarde";
                    horarioCorrecto = true;
                } else {
                    System.out.println("Elija un horario correcto");
                }
            } while (!horarioCorrecto);

            System.out.println("Estos son los asientos disponibles");
            mostrarSala(salaSeleccionada, horario);
            System.out.println();
            do {
                System.out.println("Selección de asientos:");
                do {
                    asientoCorrecto = false;
                    try {
                        System.out.println("¿Qué asiento quiere elegir?");
                        asiento = sc.nextInt();
                        sc.nextLine();
                        if (asiento <= 0 || asiento > 50) {
                            System.out.println("Asiento no válido");
                        } else {
                            try {
                                reservarAsiento(salaSeleccionada, asiento);
                                asientoCorrecto = true;
                                mostrarSala(salaSeleccionada, horario);
                                System.out.println();
                                System.out.println("Reserva realizada con éxito");
                            } catch (AsientoNoDisponibleException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Asiento no válido.");
                        sc.nextLine();
                    }
                } while (!asientoCorrecto);
                acumulador++;
            } while (acumulador != reserva);

            mostrarResumen(horario, proyeccionSeleccionada, reserva);

            System.out.println("¿Quiere reservar más asientos o elegir para otra película? S/N");
            repetir = sc.nextLine();
            if (repetir.equalsIgnoreCase("S")) {
                reservarMas = true;
            } else if (repetir.equalsIgnoreCase("N")) {
                System.out.println("Hasta pronto");
                reservarMas = false;
            }
        }while (reservarMas);
    }

    public static void mostrarResumen(String horario, Proyeccion proyeccionSeleccionada, int reserva) {
        System.out.println();
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║         RESUMEN DE COMPRA          ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("  Película seleccionada: " + proyeccionSeleccionada.getTitulo());
        System.out.println("  Sesión de: " + horario);
        System.out.printf("  Precio por entrada: %.2f€.", proyeccionSeleccionada.calcularPrecio());
        System.out.println();
        System.out.println("  Cantidad de asientos reservados: " + reserva);
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("  PRECIO TOTAL DE LA COMPRA: %.2f€.", proyeccionSeleccionada.calcularPrecio() * reserva);
        System.out.println();
        System.out.println();
    }

//══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
    //                  METODO PARA MOSTRAR LA CARTELERA
//══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
    public static void mostrarCartelera(){
        System.out.println();
        System.out.println("    ╔════════════════════════════════════╗");
        System.out.println("    ║         CARTELERA CLÁSICA          ║");
        System.out.println("    ╠════════════════════════════════════╣");
        System.out.println("    ╔══════════════╗ ╔═══════════════════╗");
        for (int i = 0; i < carteleraClasica.length; i++) {
            if (i==0){
                System.out.print("    ");
            }
            System.out.print("║    " + carteleraClasica[i] + "   ║" + " ");
        }
        System.out.println();
        System.out.println("    ╠══════════════╣ ╠═══════════════════╣");

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                 CARTELERA 3D                 ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("╔════════════╗ ╔═══════════════════════════════╗");
        for (int i = 0; i < cartelera3D.length; i++) {
            System.out.print("║   " + cartelera3D[i] + "   ║" + " ");
        }
        System.out.println();
        System.out.println("╠════════════╣ ╠═══════════════════════════════╣");
        System.out.println();
    }
//══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
    //             METODO PARA LLENAR LOS ARRAY DE LAS SALAS
//══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
    /**
     * Metodo para llenar los array con los numeros de butacas
     */
    public static void iniciarSalas(){
        for (int i = 0; i < 50 ; i++) {
            salaDracula[i] = i+1;
            salaAvatar[i] = i+1;
            salaAhora[i] = i+1;
            salaDragon[i] = i+1;
        }
    }

//══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
    //             METODOS GENERICOS
//══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
    /**
     * Metodo para mostrar la sala, recibe por parametro
     * @param sala --> el array de la sala que se vaya a usar
     * @param horario --> y el horario
     */
    public static void mostrarSala(int[] sala, String horario) {
        System.out.println(); // - alinea a la izquierda; 18 ancho en caracreres; s tipo String
        System.out.println("╔═════════════════════════════════╗");
        System.out.printf( "║        SESIÓN DE %-14s ║\n", horario.toUpperCase());
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("   ========= PANTALLA ==========");
        for (int i = 0; i < sala.length; i++) {
            if (sala[i]>0 && sala[i]< 10) {
                if (i == 0) {
                    System.out.print("   0" + sala[i] + " ");
                }else{
                    System.out.print("0" + sala[i] + " ");
                }
            } else if (sala[i]== 0) {
                if (i == 0 || i == 10 || i == 20 || i == 30 || i == 40) {
                    System.out.println();
                    System.out.print("   " + "\uD83D\uDC64" + " ");
                }else {
                    System.out.print("\uD83D\uDC64" + " ");
                }
            } else {
                if (i == 10 || i == 20 || i == 30 || i == 40) {
                    System.out.println();
                    System.out.print("   " + sala[i] + " ");
                }else {
                    System.out.print(sala[i] + " ");
                }
            }
        }
        System.out.println();
    }

    /**
     *
     * * Metodo para reservar los asientos, recibe por parametro
     * @param sala --> el array de la sala que se vaya a usar
     * @param asiento --> el asiento que se quiere reservar
     * @throws AsientoNoDisponibleException --> y lanza una excepcion si ya esta reservado
     */
    public static void reservarAsiento(int[] sala, int asiento) throws AsientoNoDisponibleException {
        if (sala[asiento-1] == 0){
            throw new AsientoNoDisponibleException("El asiento " + asiento + " ya está ocupado.");
        } else {
            sala[asiento-1] = 0;
        }
    }
}