package cifrari;
import java.util.Scanner;

/* Dati utili per prove di criptazione e decriptazione con l'algoritmo RSA:
   
   Intero da cifrare 1: 7
   P: 5
   Q: 11
   E: 3
   D: 27
   Intero cifrato 1: 13

   Intero da cifrare 2: 6882326879666683
   P: 47
   Q: 71
   E: 79
   D: 1019
   Intero cifrato 2: 15702756209122762423158
*/

public class RSA{
    // Metodo di scelta di una delle opzioni proposte avviando l'algoritmo RSA
    protected static void avvio(){
        long[] array;
        short menu;
        String testoCifrato,testoInChiaro;
        Scanner scanner = new Scanner(System.in);
        System.out.print("############### RSA ###############");
        while(true){
            System.out.print("\nOpzioni possibili:\n"
                              +"1 - Inserimento manuale di quattro valori P, Q, E e D per la codifica con l'algoritmo RSA\n"
                              +"2 - Inserimento manuale di quattro valori P, Q, E e D per la decodifica con l'algoritmo RSA\n"
                              +"3 - Inserimento manuale di due valori P e Q e generazione automatica di due valori E e D per la codifica con l'algoritmo RSA\n"
                              +"4 - Generazione automatica di quattro valori P, Q, E e D per la codifica con l'algoritmo RSA\n"
                              +"5 - Inserimento manuale di due valori P e Q e generazione automatica di due valori E e D per la decodifica con l'algoritmo RSA\n"
                              +"6 - Generazione automatica di quattro valori P, Q, E e D per la decodifica con l'algoritmo RSA\n"
                              +"7 - Uscita dal programma\n"
                              +"Selezionare una delle precedenti opzioni: ");
            menu = scanner.nextShort();
            System.out.println("");
            switch(menu){
                case 1:
                    array = generazioneChiave(1,1);
                    System.out.print("Inserire il valore intero da criptare: ");
                    testoInChiaro = scanner.next();
                    testoCifrato = crittografia(testoInChiaro,array[0],array[2]);
                    System.out.println("");
                    System.out.println("Testo in chiaro: "+testoInChiaro);
                    System.out.println("Testo cifrato:  "+testoCifrato);
                break;
                case 2:
                    array = generazioneChiave(1,1);
                    System.out.print("Inserire il valore intero da decriptare: ");
                    testoCifrato = scanner.next();
                    testoInChiaro = decrittografia(testoCifrato,array[1],array[2]);
                    System.out.println("");
                    System.out.println("Testo cifrato:  "+testoCifrato);
                    System.out.println("Testo in chiaro: "+testoInChiaro);
                break;
                case 3:
                    array = generazioneChiave(1,2);
                    System.out.print("Inserire il valore intero da criptare: ");
                    testoInChiaro = scanner.next();
                    testoCifrato = crittografia(testoInChiaro,array[0],array[2]);
                    System.out.println("");
                    System.out.println("Testo in chiaro: "+testoInChiaro);
                    System.out.println("Testo cifrato:  "+testoCifrato);
                break;
                case 4:
                    array = generazioneChiave(2,2);
                    System.out.print("Inserire il valore intero da criptare: ");
                    testoInChiaro = scanner.next();
                    testoCifrato = crittografia(testoInChiaro,array[0],array[2]);
                    System.out.println("");
                    System.out.println("Testo in chiaro: "+testoInChiaro);
                    System.out.println("Testo cifrato:  "+testoCifrato);
                break;
                case 5:
                    array = generazioneChiave(1,2);
                    System.out.print("Inserire il valore intero da decriptare: ");
                    testoCifrato = scanner.next();
                    testoInChiaro = decrittografia(testoCifrato,array[1],array[2]);
                    System.out.println("");
                    System.out.println("Testo cifrato:  "+testoCifrato);
                    System.out.println("Testo in chiaro: "+testoInChiaro);
                break;
                case 6:
                    array = generazioneChiave(2,2);
                    System.out.print("Inserire il valore intero da decriptare: ");
                    testoCifrato = scanner.next();
                    testoInChiaro = decrittografia(testoCifrato,array[1],array[2]);
                    System.out.println("");
                    System.out.println("Testo cifrato:  "+testoCifrato);
                    System.out.println("Testo in chiaro: "+testoInChiaro);
                break;
                case 7:
                    System.out.println("Sessione terminata");
                    System.out.println("############### RSA ###############");
                    System.exit(0);
                break;
                default:
                    System.out.println("Scelta menu' non valida");
                break;
            }
        }
    }
    
    // Metodo di calcolo del massimo comune divisore
    protected static long calcoloGCD(long toziente,long e){
        long a,b,GCD = 0,r;
        a = toziente;
        b = e;
        if(a == 0 || b == 0){
            if(a == 0){
                return b;
            }
            else{
                return a;
            }
        }
        else if(a == 0 && b == 0){
            return GCD;
        }
        else{
            r = a%b;
            if(r == 0){
                return b;
            }
            else{
                do{
                    a = b;
                    b = r;
                    r = a%b;
                }while(r != 0);
                return b;
            }
        }
    }
    
    // Metodo di svolgimento dei calcoli per il controllo di primalita' di un numero
    protected static boolean calcoloPrimalita(long numero){
        boolean flag = false;
        long a,c,k = 1,intero,numeroIniziale = numero,potenza;
        if(numero <= 3 || (numero%2) == 0 || (numero%3) == 0 || ((numero%5) == 0 && numero != 5)){
            return false;
        }
        numero--;
        while(true){
            potenza = (long)Math.pow(2,k);
            c = numero/potenza;
            if((c%2) != 0){
                break;
            }
            k++;
        }
        for(int i = 0;i < 1000;i++){
            a = (long)Math.floor(Math.random()*((numeroIniziale-2)-(2+1))+2);
            if(a%numeroIniziale == 1 || a%numeroIniziale == numeroIniziale-1){
                if(potenza(a,2,numeroIniziale) == 1){
                    continue;
                }
                else{
                    return false;
                }
            }
            if(potenza(a,c,numeroIniziale) == 1){
                continue;
            }
            for(long j = 0;j < k;j++){
                intero = (long)Math.pow(2,j)*c;
                if(potenza(a,intero,numeroIniziale) == numeroIniziale-1){
                    flag = true;
                }
            }
            if(flag == true){
                continue;
            }
            return false;
        }
        return true;
    }
    
    // Metodo di applicazione dell'algoritmo RSA per crittografia
    protected static String crittografia(String testoInChiaro,long e,long n){
        int blocco,cont = 0,contatore = 1,controllo,lung = testoInChiaro.length(),lunghezza,moltiplicazione;
        long risultato;
        String blocchi,stringaDiLavoro,testoCifrato = "";
        StringBuilder stringa = new StringBuilder();
        stringa.append(testoInChiaro);
        if(lung%3 != 0){
            controllo = lung/3;
            moltiplicazione = (controllo+1)*3;
            lunghezza = moltiplicazione-testoInChiaro.length();
            if(lunghezza == 1){
                stringa.insert(lung-2,"0");
            }
            if(lunghezza == 2){
                stringa.insert(lung-1,"00");
            }
        }
        lung = stringa.length();
        do{
            stringaDiLavoro = stringa.substring(cont,cont+3);
            cont += 3;
            System.out.println("");
            System.out.println("Blocco "+contatore+" ("+stringaDiLavoro+")");
            blocco = Integer.parseInt(stringaDiLavoro);
            risultato = potenza(blocco,e,n);
            System.out.println("Potenza: "+risultato);
            blocchi = String.valueOf(risultato);
            if(contatore == 1){
                System.out.println("Assemblaggio: "+blocchi);
            }
            else{
                System.out.println("Assemblaggio: "+testoCifrato+" + "+blocchi);
            }
            testoCifrato = testoCifrato+blocchi;
            contatore++;
        }while(cont != lung);
        return testoCifrato;
    }
    
    // Metodo di applicazione dell'algoritmo RSA per decrittografia
    protected static String decrittografia(String testoCifrato,long d,long n){
        int blocco,cont = 0,contat = 0,contatore = 1,flag = 0,lunghezza = testoCifrato.length();
        long risultato;
        String blocchi,stringaDiLavoro,testoInChiaro = "";
        StringBuilder stringa = new StringBuilder(),stringaTemporanea;
        stringa.append(testoCifrato);
        do{
            if((cont+4) < lunghezza){
                stringaDiLavoro = testoCifrato.substring(cont,cont+4);
                cont += 4;
            }
            else{
                stringaDiLavoro = testoCifrato.substring(cont,lunghezza);
                flag = 1;
            }
            System.out.println("");
            System.out.println("Blocco "+contatore+" ("+stringaDiLavoro+")");
            blocco = Integer.parseInt(stringaDiLavoro);
            risultato = potenza(blocco,d,n);
            System.out.println("Potenza: "+risultato);
            blocchi = String.valueOf(risultato);
            stringaTemporanea = new StringBuilder();
            stringaTemporanea.append(blocchi);
            blocchi = stringaTemporanea.toString();
            stringaTemporanea.setLength(0);
            if(contatore == 1){
                System.out.println("Assemblaggio: "+blocchi);
            }
            else{
                System.out.println("Assemblaggio: "+testoInChiaro+" + "+blocchi);
            }
            testoInChiaro = testoInChiaro+blocchi;
            contat++;
            contatore++;
            if(flag == 1){
                break;
            }
        }while(contat != lunghezza);
        return testoInChiaro;
    }
    
    // Metodo di generazione di chiave pubblica e privata
    protected static long[] generazioneChiave(int modalita,int flag){
        long d = 0,e = 0,n,p = 0,q = 0,prova1 = 0,prova2 = 0,toziente = 0;
        long[] array = new long[3];
        Scanner scanner = new Scanner(System.in);
        switch(modalita){
            case 1:
                do{
                    System.out.print("Inserire P (numero primo maggiore di 3): ");
                    p = scanner.nextLong();
                    if(!calcoloPrimalita(p)){
                        System.out.println("Errore in fase di inserimento del numero P! Il numero inserito non e' primo");
                    }
                }while(!calcoloPrimalita(p));
                do{
                    System.out.print("Inserire Q (numero primo maggiore di 3): ");
                    q = scanner.nextLong();
                    if(!calcoloPrimalita(q)){
                        System.out.println("Errore in fase di inserimento del numero Q! Il numero inserito non e' primo");
                    }
                }while(!calcoloPrimalita(q));
            break;
            case 2:
                do{
                    p = (long)Math.floor(Math.random()*(9999-3+1))+3;
                    prova1++;
                }while(!calcoloPrimalita(p));
                do{
                    q = (long)Math.floor(Math.random()*(9999-3+1))+3;
                    prova2++;
                }while(!calcoloPrimalita(q));
            break;
        }
        if(flag == 1){
            System.out.print("Inserire E: ");
            e = scanner.nextLong();
            System.out.print("Inserire D: ");
            d = scanner.nextLong();
        }
        else if(flag == 2){
            do{
                e = (long)Math.floor(Math.random()*(toziente-1+1))+1;
            }while(calcoloGCD(toziente,e) != 1);
            d = inversoMoltiplicativoModulare(e,toziente);
        }
        System.out.println("");
        n = p*q;
        toziente = (p-1)*(q-1);
        if(modalita == 1){
            System.out.println("Valore P: "+p+"\n"
                              +"Valore Q: "+q+"\n"
                              +"Valore N: "+n+"\n"
                              +"Toziente N: "+toziente+"\n"
                              +"Numero E: "+e+"\n"
                              +"Valore D: "+d+"\n"
                              +"KU: ("+n+","+e+")"+"\n"
                              +"KR: ("+n+","+d+")");
        }
        else if(modalita == 2){
            System.out.println("Valore P: "+p+"\n"
                              +"Tentativi: "+prova1+"\n"
                              +"Valore Q: "+q+"\n"
                              +"Tentativi: "+prova2+"\n"
                              +"Valore N: "+n+"\n"
                              +"Toziente N: "+toziente+"\n"
                              +"Numero E: "+e+"\n"
                              +"Valore D: "+d+"\n"
                              +"KU: ("+n+","+e+")"+"\n"
                              +"KR: ("+n+","+d+")");
        }
        System.out.println("");
        array[0] = e;
        array[1] = d;
        array[2] = n;
        return array;
    }
    
    // Metodo di calcolo dell'inverso moltiplicativo modulare
    protected static long inversoMoltiplicativoModulare(long a,long m){
        for(long i = 1;i < m;i++){
            if(((a%m)*(i%m))%m == 1){
                return i;
            }
        }
        return 1;
    }
    
    // Metodo di calcolo del risultato della potenza tra i numeri primi scelti
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
}