package funciones;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FuncionApp {

    public static long generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(999999 - 111111 + 1) + 111111;
    }

    public static LocalDate generateRandomDate() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(0, daysBetween + 1);
        return startDate.plusDays(randomDays);
    }


    public static String generateRandomCUIT() {
        // Generar el prefijo (tipo de persona: 20, 27, 30)
        int[] possiblePrefixes = {20, 27, 30};
        int prefix = possiblePrefixes[ThreadLocalRandom.current().nextInt(0, possiblePrefixes.length)];

        // Generar el número base de 8 dígitos (que simula el DNI o identificación)
        long dni = ThreadLocalRandom.current().nextLong(10000000L, 99999999L);

        // Concatenar el prefijo y el número base
        String baseCuit = String.format("%02d%d", prefix, dni);

        // Calcular el dígito verificador
        int verificador = calculateCheckDigit(baseCuit);

        // Retornar el CUIT completo con el dígito verificador
        return baseCuit + verificador;
    }

    private static int calculateCheckDigit(String baseCuit) {
        int[] coefficients = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2}; // Coeficientes para el cálculo del dígito verificador
        int sum = 0;

        // Aplicar el algoritmo a los primeros 10 dígitos
        for (int i = 0; i < baseCuit.length(); i++) {
            sum += Character.getNumericValue(baseCuit.charAt(i)) * coefficients[i];
        }

        // Calcular el dígito verificador
        int remainder = sum % 11;
        int checkDigit = 11 - remainder;

        if (checkDigit == 11) {
            return 0;
        } else if (checkDigit == 10) {
            return 9;
        } else {
            return checkDigit;
        }
    }

}
