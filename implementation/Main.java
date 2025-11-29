package cifrari;
import java.io.IOException;
import java.util.Scanner;

public class Main{
    // Metodo di avvio del programma di crittografia e decrittografia di stringhe
    public static void main(String[] args) throws IOException,Exception{
        byte cifrario;
        System.out.println("Programma per criptare e decriptare stringhe\n");
        cifrario = controlloCifrario();
        lancioCifrario(cifrario);
    }
    
    // Metodo di selezione del cifrario da avviare
    protected static byte controlloCifrario() throws Exception{
        boolean controllore = true;
        byte cifrario = 0;
        Scanner scanner = new Scanner(System.in);
        while(controllore){
            System.out.print("Cifrari utilizzabili:\n"
                              +"1 - Cifrario del 1° gruppo: Trasposizione\n"
                              +"2 - Cifrario del 2° gruppo: DES con modo di funzionamento CBC\n"
                              +"3 - Cifrario del 3° gruppo: AES con modo di funzionamento CTR\n"
                              +"4 - Cifrario del 4° gruppo: Algoritmo di Miller-Rabin\n"
                              +"5 - Cifrario del 5° gruppo: RSA\n"
                              +"Selezionare il cifrario da utilizzare: ");
            cifrario = scanner.nextByte();
            System.out.println("");
            if(cifrario <= 0){
                System.out.println("Errore! E' stato inserito un numero negativo o nullo! Bisogna inserire un numero intero positivo!");
            }
            else if(cifrario > 5){
                System.out.println("Errore! E' stato inserito un numero non compreso tra 1 e 5");
            }
            else{
                controllore = false;
            }
        }
        return cifrario;
    }
    
    // Metodo di avvio di uno dei cifrari scelti dall'utente
    protected static void lancioCifrario(byte cifrario) throws Exception{
        switch(cifrario){
            case 1:
                Trasposizione.avvio();
            break;
            case 2:
                DES_CBC.avvio();
            break;
            case 3:
                AES_CTR.avvio();
            break;
            case 4:
                MillerRabin.avvio();
            break;
            case 5:
                RSA.avvio();
            break;
            default:
                System.out.println("Errore durante il lancio di un cifrario!");
            break;
        }
    }
}