package cifrari;
import java.util.Scanner;

/* Dati utili per prove di criptazione e decriptazione con l'algoritmo di trasposizione:
   
   Testo in chiaro 1: messaggioprova
   Chiave 1: 5261743
   Testo cifrato 1: sreogagvmispao 

   Testo in chiaro 2: artiglieriainizierafuocointerdizionedamontesabotinooreseitrentaz
   Chiave 2: 54218763
   Testo cifrato 2: iifeesoetaatneoreiozotezrirnotntareiiniiizcimosaliodabetgnurdarn */

public class Trasposizione{
    // Metodo di scelta di una delle opzioni proposte avviando l'algoritmo di trasposizione
    protected static void avvio(){
        byte opzione;
        Scanner scanner = new Scanner(System.in);
        System.out.print("############### TRASPOSIZIONE ###############");
        while(true){
            System.out.print("\nOpzioni possibili:\n"
                              +"1 - Crittografia con l'algoritmo di trasposizione\n"
                              +"2 - Decrittografia con l'algoritmo di trasposizione\n"
                              +"3 - Uscita dal programma\n"
                              +"Selezionare una delle precedenti opzioni: ");
            opzione = scanner.nextByte();
            System.out.println("");
            switch(opzione){
                case 1:
                    crittografia();
                break;
                case 2:
                    decrittografia();
                break;
                case 3:
                    System.out.println("Sessione terminata");
                    System.out.println("############### TRASPOSIZIONE ###############");
                    System.exit(0);
                break;
                default:
                    System.out.println("Scelta menu' non valida");
                break;
            }
        }
    }
    
    // Metodo di applicazione dell'algoritmo di trasposizione per crittografia
    protected static void crittografia(){
        boolean controllore1 = true;
        char[] array,arrayTestoInChiaro;
        char[][] matrice;
        int colonne,controllo,indice,prova,resto,righe;
        String chiave,testoCifrato = "",testoInChiaro;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserire il testo in chiaro da cifrare: ");
        testoInChiaro = scanner.next();
        System.out.print("Inserire il numero di colonne: ");
        colonne = scanner.nextInt();
        while(true){
            System.out.print("Inserire una chiave numerica costituita da tante cifre quante sono le colonne: ");
            chiave = scanner.next();
            array = chiave.toCharArray();
            for(int i = 0;i < chiave.length();i++){
                controllo = array[i]-'0';
                if(controllo > colonne){
                    controllore1 = false;
                }
            }
            if(controllore1 == true){
                break;
            }
            System.out.println("La chiave inserita non è valida perchè deve essere compresa nell'intervallo (0 - "+colonne+")");
        }
        resto = testoInChiaro.length()%colonne;
        if(resto != 0){
            for(int i = 0;i < (colonne-resto);i++){
                testoInChiaro += "x";
            }
        }
        righe = (testoInChiaro.length())/colonne;
        arrayTestoInChiaro = testoInChiaro.toCharArray();
        matrice = new char[righe][colonne];
        for(int i = 0;i < righe;i++){
            for(int j = 0;j < colonne;j++){
                matrice[i][j] = arrayTestoInChiaro[j+(colonne*i)];
                System.out.print(matrice[i][j]+" ");
            }
            System.out.println();
        }
        for(int k = 0;k < colonne;k++){
            prova = k+1;
            indice = chiave.indexOf(prova+48);
            for(int j = 0;j < righe;j++){
                testoCifrato = testoCifrato+matrice[j][indice];
            }
        }
        System.out.println("Testo in chiaro: "+testoInChiaro);
        System.out.println("Chiave: "+chiave);
        System.out.println("Testo cifrato: "+testoCifrato);
    }
    
    // Metodo di applicazione dell'algoritmo di trasposizione per decrittografia
    protected static void decrittografia(){
        char[][] matrice,nuovaMatrice;
        int colonne,indice,prova,righe;
        String chiave,testoCifrato,testoInChiaro = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il testo cifrato da decifrare: ");
        testoCifrato = scanner.next();
        System.out.print("Inserisci il numero di colonne: ");
        colonne = scanner.nextInt();
        System.out.print("Inserire una chiave numerica costituita da tante cifre quante sono le colonne: ");
        chiave = scanner.next();
        righe = (int)Math.ceil(testoCifrato.length()/(double)colonne);
        matrice = new char[righe][colonne];
        nuovaMatrice = new char[righe][colonne];
        for(int i = 0;i < colonne;i++){
            String colonna = (testoCifrato.substring(i*righe,(i+1)*righe));
            for (int k = 0;k < righe;k++){
                matrice[k][i] = colonna.charAt(k);
            }
        }
        for(int i = 0;i < colonne;i++){
            prova = i+1;
            indice = chiave.indexOf(prova+48);
            for(int j = 0;j < righe;j++){
                nuovaMatrice[j][indice] = matrice[j][i];
            }
        }
        for(int i = 0;i < righe;i++){
            for(int j = 0;j < colonne;j++){
                testoInChiaro += nuovaMatrice[i][j];
            }
        }
        System.out.println("Testo cifrato: "+testoCifrato);
        System.out.println("Chiave: "+chiave);
        System.out.println("Testo in chiaro: "+testoInChiaro);
    }
}