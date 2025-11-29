package cifrari;
import java.util.Scanner;

/* Dati utili per prove di calcolo di primalita' di un numero con l'algoritmo di Miller-Rabin:
   
   Numeri primi da 1 a 100:
   2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
   
   Probabilita' di errore nel test:
   Minore di 1/4 */

public class MillerRabin{
    // Metodo di scelta di una delle opzioni proposte avviando l'algoritmo di Miller-Rabin
    protected static void avvio(){
        byte opzione;
        Scanner scanner = new Scanner(System.in);
        System.out.print("############### MILLER RABIN ###############");
        while(true){
            System.out.print("\nOpzioni possibili:\n"
                              +"1 - Calcolo della primalita' di un numero con l'algoritmo di Miller-Rabin\n"
                              +"2 - Uscita dal programma\n"
                              +"Selezionare una delle precedenti opzioni: ");
            opzione = scanner.nextByte();
            System.out.println("");
            switch(opzione){
                case 1:
                    primalita();
                break;
                case 2:
                    System.out.println("Sessione terminata");
                    System.out.println("############### MILLER RABIN ###############");
                    System.exit(0);
                break;
                default:
                    System.out.println("Scelta menu' non valida");
                break;
            }
        }
    }
    
    // Metodo di svolgimento della potenza e del modulo tra i numeri "a", "y" ed "n"
    protected static long potenza(long a,long y,long n){
        long risultato = 1;
        while(y > 0){
            if(y%2 == 1){
                risultato = (risultato*a)%n;
            }
            y = y/2;
            a = (a*a)%n;
        }
        return risultato;
    }
    
    // Metodo di calcolo della primalita' di un numero
    protected static void primalita(){
        long a,c,k = 1,intero,numero,numeroIniziale,potenza;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Inserire un numero da verificare (Maggiore di 3): ");
            numero = scanner.nextLong();
            System.out.println("");
            if(numero <= 3){
                System.out.println("Numero inserito: "+numero);
                System.out.println("Errore! Il numero inserito e' minore o uguale a 3\n");
            }
            else{
                break;
            }
        }
        numeroIniziale = numero;
        if((numero%2) == 0 || (numero%3) == 0 || ((numero%5) == 0 && numero != 5)){
            System.out.println("Numero inserito: "+numero);
            System.out.println("Tipologia numero: Composito");
            return;
        }
        numero--;
        while(true){
            potenza = (long)Math.pow(2,k);
            c = numero/potenza;
            System.out.println("----- Tentativo "+k+" affinchè il numero 'c' sia dispari -----");
            if((c%2) != 0){
                System.out.println("Numero 'c' trovato (c = [(n-1)/(2^k)])");
                System.out.println("c = "+c+", dispari");
                System.out.println("k = "+k);
                System.out.println("----- Ricerca del numero 'c' dispari al "+k+"° tentativo riuscita -----");
                System.out.println("Sia 'a' un numero casuale compreso tra 2 e n-2."+
                                   "Condizioni necessarie affinche' 'n', ossia il numero "+numeroIniziale+" inserito dall'utente, sia primo:\n"+
                                   "1) Se (a^2 mod n) = 1, avendo accertato in precedenza una delle due sottocondizioni:\n"+
                                   "        1.1) (a mod n) = 1\n"+
                                   "        1.2) (a mod n) = n-1\n"+
                                   "2) (a^c mod n) = 1\n"+
                                   "3) {[a^(2^j)]*c} = n-1");
                break;
            }
            else if((c%2) == 0){
                System.out.println("c = "+c+", pari");
                System.out.println("k = "+k);
                System.out.println("Si procede con un altro tentativo");
                System.out.println("----- Ricerca del numero 'c' dispari al "+k+"° tentativo non riuscita -----");
            }
            k++;
        }
        for(int i = 0;i < 1000;i++){
            a = (long)Math.floor(Math.random()*((numeroIniziale-2)-(2+1))+2);
            if(a%numeroIniziale == 1 || a%numeroIniziale == numeroIniziale-1){
                if(potenza(a,2,numeroIniziale) == 1){
                    System.out.println("Condizione 1 rispettata ---> [(a^2 mod n) = 1]\n");
                    System.out.println("Numero inserito: "+numeroIniziale);
                    System.out.println("Tipologia numero: Primo");
                    return;
                }
            }
            if(potenza(a,c,numeroIniziale) == 1){
                System.out.println("Condizione 2 rispettata ---> [(a^c mod n) = 1]\n");
                System.out.println("Numero inserito: "+numeroIniziale);
                System.out.println("Tipologia numero: Primo");
                return;
            }
            for(long j = 0;j < k;j++){
                intero = (long)Math.pow(2,j)*c;
                if(potenza(a,intero,numeroIniziale) == numeroIniziale-1){
                    System.out.println("Condizione 3 rispettata ---> [{[a^(2^j)]*c} = n-1]1\n");
                    System.out.println("Numero inserito: "+numeroIniziale);
                    System.out.println("Tipologia numero: Primo");
                    return;
                }
            }
        }
        System.out.println("");
    }
}