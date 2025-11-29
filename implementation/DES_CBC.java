package cifrari;
import java.util.*;

/* Dati utili per prove di criptazione e decriptazione con l'algoritmo DES con modo di funzionamento CBC:
   
   Testo in chiaro 1: 1100001000111011001111111110001000010100010110011100010110010001
   Chiave 1: 1000110011110100111111111001010110000101111011100010110101000011
   Testo cifrato 1: 1011101111011111000011011001110100011100111100010110010110110101
   
   Testo in chiaro 2: 1001011101010011100101101110100010100001010011110101110100110111
   Chiave 2: 1110100010110100101111011011000111010111101010000110100010101111
   Testo cifrato 2: 0000111101111010000000111000111000000000101100110110110110011101 */

public class DES_CBC{
    // Metodo di scelta di una delle opzioni proposte avviando l'algoritmo DES
    protected static void avvio(){
        int scelta;
        Scanner scanner = new Scanner(System.in);
        System.out.print("############### DES CBC ###############");
        while(true){
            System.out.print("\nOpzioni possibili:\n"
                          +"1 - Crittografia con l'algoritmo DES con modo di funzionamento CBC\n"
                          +"2 - Decrittografia con l'algoritmo DES con modo di funzionamento CBC\n"
                          +"3 - Uscita dal programma\n"
                          +"Selezionare una delle precedenti opzioni: ");
            scelta = scanner.nextInt();
            System.out.println("");
            switch(scelta){
                case 1:
                    crittografia();
                break;
                case 2:
                    decrittografia();
                break;
            case 3:
                System.out.println("Sessione terminata");
                System.out.println("############### DES CBC ###############");
                System.exit(0);
                break;
            default:
                System.out.println("Scelta menu' non valida");
                break;
            }
        }
    }
    
    // Metodo di applicazione dell'algoritmo DES per crittografia
    protected static void crittografia(){
        int blocchi1,blocchi2,lunghezzaResidua1,lunghezzaResidua2,moltiplicazione,resto,separatore = 0;
        int[] bloccoInteri,risultato;
        int[][] arrayCBC;
        String blocco,chiave,testoInChiaro;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserire il testo in chiaro in bit: ");
        testoInChiaro = scanner.next();
        System.out.print("Inserire la chiave in 64 bit: ");
        chiave = scanner.next();
        int IV[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        if(testoInChiaro.length() < 64){
            lunghezzaResidua1 = 64-testoInChiaro.length();
            for(int i = 0;i < lunghezzaResidua1;i++){
                testoInChiaro = testoInChiaro+'0';
            }
        }
        resto = testoInChiaro.length()%64;
        if(resto != 0){
            blocchi1 = testoInChiaro.length()/64;
            moltiplicazione = (blocchi1+1)*64;
            lunghezzaResidua2 = moltiplicazione-testoInChiaro.length();
            for(int i = 0;i < lunghezzaResidua2;i++){
                testoInChiaro = testoInChiaro+'0';
            }
        }
        blocchi2 = testoInChiaro.length()/64;
        arrayCBC = new int[blocchi2][64];
        for(int i = 0;i < blocchi2;i++){
            blocco = testoInChiaro.substring(separatore,separatore+64);
            bloccoInteri = new int[blocco.length()];
            for(int j = 0;j < blocco.length();j++){
                bloccoInteri[j] = blocco.charAt(j)-'0';
            }
            for(int k = 0;k < blocco.length();k++){
                bloccoInteri[k] = IV[k]^bloccoInteri[k];
            }
            risultato = applicazioneCrittografia(bloccoInteri,chiave);
            System.arraycopy(risultato,0,arrayCBC[i],0,64);
            System.arraycopy(risultato,0,IV,0,64);
            separatore += 64;
        }
        System.out.println("\n");
        System.out.println("Testo in chiaro: "+testoInChiaro);
        System.out.println("Chiave: "+chiave);
        System.out.print("Testo cifrato: ");
        for(int i = 0;i < blocchi2;i++){
            for(int j = 0;j < 64;j++){
                System.out.print(arrayCBC[i][j]);
            }
        }
        System.out.println("");
    }
    
    // Metodo di applicazione dell'algoritmo DES per decrittografia
    protected static void decrittografia(){
        int blocchi1,blocchi2,lunghezzaResidua1,lunghezzaResidua2,moltiplicazione,resto,separatore = 0;
        int[] bloccoInteri,risultato;
        String blocco,chiave,testoCifrato;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserire il testo cifrato in bit: ");
        testoCifrato = scanner.next();
        System.out.print("Inserire la key in 64 bit: ");
        chiave = scanner.next();
        int IV[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        if(testoCifrato.length() < 64){
            lunghezzaResidua1 = 64-testoCifrato.length();
            for(int i = 0;i < lunghezzaResidua1;i++){
                testoCifrato = testoCifrato+'0';
            }
        }
        resto = testoCifrato.length()%64;
        if(resto != 0){
            blocchi1 = testoCifrato.length()/64;
            moltiplicazione = (blocchi1+1)*64;
            lunghezzaResidua2 = moltiplicazione-testoCifrato.length();
            for(int i = 0;i < lunghezzaResidua2;i++){
                testoCifrato = testoCifrato+'0';
            }
        }
        blocchi2 = testoCifrato.length()/64;
        int[][] arrayCBC = new int[blocchi2][64];
        for(int i = 0;i < blocchi2;i++){
            blocco = testoCifrato.substring(separatore,separatore+64);
            bloccoInteri = new int[blocco.length()];
            for(int j = 0;j < blocco.length();j++){
                bloccoInteri[j] = blocco.charAt(j)-'0';
            }
            risultato = applicazioneDecrittografia(bloccoInteri,chiave);
            for(int k = 0;k < blocco.length();k++){
                risultato[k] = IV[k]^risultato[k];
            }
            System.arraycopy(risultato,0,arrayCBC[i],0,64);
            System.arraycopy(bloccoInteri,0,IV,0,64);
            separatore += 64;
        }
        System.out.println("\n");
        System.out.println("Testo cifrato: "+testoCifrato);
        System.out.println("Chiave: "+chiave);
        System.out.print("Testo in chiaro: ");
        for(int i = 0;i < blocchi2;i++){
            for(int j = 0;j < 64;j++){
                System.out.print(arrayCBC[i][j]);
            }
        }
        System.out.println("");
    }
    
    // Metodo di conversione di un numero decimale in binario
    protected static int[] conversioneInBinario(int decimale){
        int i = 0;
        int binario[] = {0,0,0,0};
        while(decimale > 0){
            binario[i++] = decimale%2;
            decimale = decimale/2;
        }
        return binario;
    }
    
    // Metodo di applicazione dello shift della chiave
    protected static int[] shift(int[] chiave){
        int intero1 = chiave[0],intero2 = chiave[28];
        for(int i = 0;i < 27;i++){
            chiave[i] = chiave[i+1];
        }
        chiave[27] = intero1;
        for(int i = 28;i < 55;i++){
            chiave[i] = chiave[i+1];
        }
        chiave[55] = intero2;
        return chiave;
    }
    
    // Metodo di svolgimento della permutazione del testo in chiaro
    protected static int[] permutazione(int[] input,int[] tabellaPermutazione,int lunghezza){
        for(int i = 0;i < lunghezza;i++){
            int intero = tabellaPermutazione[i]-1;
            tabellaPermutazione[i] = input[intero];
            System.out.print(tabellaPermutazione[i]);
        }
        return tabellaPermutazione;
    }
    
    // Metodo di passaggio di bit attraverso delle S-box
    protected static int[] evoluzioneSbox(int[] xor){
        int elemento,inverso,offset = 0,offsetFinale = 0,risultato;
        int[] sboxFinale = new int[32];
        int binario[];
        int arrayTridimensionale[][][] = 
        {
        {
        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
        {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
        {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
        {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
        },
        {
        {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
        {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
        {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
        {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
        },
        {
        {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
        {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
        {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
        {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
        },
        {
        {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
        {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
        {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
        {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
        },
        {
        {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
        {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
        {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
        {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
        },
        {
        {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
        {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
        {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
        {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
        },
        {
        {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
        {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
        {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
        {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
        },
        {
        {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
        {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
        {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
        {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
        }
        };
        for(int contatore = 0;contatore < 8;contatore++){
            if(xor[0+offset] == 0 && xor[5+offset] == 0){
                int[] array = {0,0,0,0};
                array[0] = xor[1+offset];
                array[1] = xor[2+offset];
                array[2] = xor[3+offset];
                array[3] = xor[4+offset];
                risultato = 0;
                for(int i = 3;i >= 0;i--){
                    if(array[i] == 1){
                        risultato += Math.pow(2,((4-i)-1));
                    }
                }
                elemento = arrayTridimensionale[contatore][0][risultato];
                binario = conversioneInBinario(elemento);
                int risultatoBinario[] = {0,0,0,0};
                inverso = 3;
                for(int i = 0;i < 4;i++){
                    risultatoBinario[i] = binario[inverso];
                    inverso--;
                }
                System.arraycopy(risultatoBinario,0,sboxFinale,offsetFinale,4);
                offsetFinale += 4;
            }
            if(xor[0+offset] == 0 && xor[5+offset] == 1){
                int[] array = {0,0,0,0};
                array[0] = xor[1+offset];
                array[1] = xor[2+offset];
                array[2] = xor[3+offset];
                array[3] = xor[4+offset];
                risultato = 0;
                for(int i = 3;i >= 0;i--){
                    if(array[i] == 1){
                        risultato += Math.pow(2,((4-i)-1));
                    }
                }
                elemento = arrayTridimensionale[contatore][1][risultato];
                binario = conversioneInBinario(elemento);
                int risultatoBinario[] = {0,0,0,0};
                inverso = 3;
                for(int i = 0;i < 4;i++){
                    risultatoBinario[i] = binario[inverso];
                    inverso--;
                }
                System.arraycopy(risultatoBinario,0,sboxFinale,offsetFinale,4);
                offsetFinale += 4;
            }
            if(xor[0+offset] == 1 && xor[5+offset] == 0){
                int[] array = {0,0,0,0};
                array[0] = xor[1+offset];
                array[1] = xor[2+offset];
                array[2] = xor[3+offset];
                array[3] = xor[4+offset];
                risultato = 0;
                for(int i = 3;i >= 0;i--){
                    if(array[i] == 1){
                        risultato += Math.pow(2,((4-i)-1));
                    }
                }
                elemento = arrayTridimensionale[contatore][2][risultato];
                binario = conversioneInBinario(elemento);
                int risultatoBinario[] = {0,0,0,0};
                inverso = 3;
                for(int i = 0;i < 4;i++){
                    risultatoBinario[i] = binario[inverso];
                    inverso--;
                }
                System.arraycopy(risultatoBinario,0,sboxFinale,offsetFinale,4);
                offsetFinale += 4;
            }
            if(xor[0+offset] == 1 && xor[5+offset] == 1){
                int[] array = {0,0,0,0};
                array[0] = xor[1+offset];
                array[1] = xor[2+offset];
                array[2] = xor[3+offset];
                array[3] = xor[4+offset];
                risultato = 0;
                for(int i = 3;i >= 0;i--){
                    if(array[i]==1){
                        risultato += Math.pow(2,((4-i)-1));
                    }
                }
                elemento = arrayTridimensionale[contatore][3][risultato];
                binario = conversioneInBinario(elemento);
                int risultatoBinario[] = {0,0,0,0};
                inverso = 3;
                for(int i = 0;i < 4;i++){
                    risultatoBinario[i] = binario[inverso];
                    inverso--;
                }
                System.arraycopy(risultatoBinario,0,sboxFinale,offsetFinale,4);
                offsetFinale += 4;
            }
            offset += 6;
        }
        System.out.println("");
        return sboxFinale;
    }
    
    // Metodo di applicazione del processo di crittografia e di visualizzazione dei risultati parziali
    protected static int[] applicazioneCrittografia(int[] testo,String chiave){
        int[] chiaveInteri = new int[chiave.length()],destra,sinistra;
        int CD[] = {
                        57,49,41,33,25,17,9,
                        1,58,50,42,34,26,18,
                        10,2,59,51,43,35,27,
                        19,11,3,60,52,44,36,
                        63,55,47,39,31,23,15,
                        7,62,54,46,38,30,22,
                        14,6,61,53,45,37,29,
                        21,13,5,28,20,12,4
                    };
        int IP[] = {
                        58,50,42,34,26,18,10,2,
                        60,52,44,36,28,20,12,4,
                        62,54,46,38,30,22,14,6,
                        64,56,48,40,32,24,16,8,
                        57,49,41,33,25,17,9,1,
                        59,51,43,35,27,19,11,3,
                        61,53,45,37,29,21,13,5,
                        63,55,47,39,31,23,15,7
                    };
        int keysub[] = {
                            1,2,3,4,5,6,7,8,
                            9,10,11,12,13,14,15,16,
                            17,18,19,20,21,22,23,24,
                            25,26,27,28,29,30,31,32,
                            33,34,35,36,37,38,39,40,
                            41,42,43,44,45,46,47,48,
                            49,50,51,52,53,54,55,56,
                            57,58,59,60,61,62,63,64
                        };
        for(int i = 0;i < chiave.length();i++){
            chiaveInteri[i] = chiave.charAt(i)-'0';
        }
        System.out.println("");
        System.out.print("Prima permutazione: ");
        IP = permutazione(testo,IP,64);
        System.out.println("");
        System.out.print("Keysub: ");
        keysub = permutazione(chiaveInteri,keysub,64);
        System.out.println("");
        System.out.print("CD: ");
        CD = permutazione(keysub,CD,56);
        for(int i = 1;i <= 16;i++){
            System.out.println("\n");
            System.out.println("Passaggio "+i);
            sinistra = Arrays.copyOfRange(IP,0,32);
            destra = Arrays.copyOfRange(IP,32,64);
            System.out.print("Sinistra: ");
            for(int j = 0;j < 32;j++){
                System.out.print(sinistra[j]);
            }
            System.out.println("");
            System.out.print("Destra: ");
            for(int k = 0;k < 32;k++){
                System.out.print(destra[k]);
            }
            int E[] = {
                            32,1,2,3,4,5,
                            4,5,6,7,8,9,
                            8,9,10,11,12,13,
                            12,13,14,15,16,17,
                            16,17,18,19,20,21,
                            20,21,22,23,24,25,
                            24,25,26,27,28,29,
                            28,29,30,31,32,1
                        };
            System.out.println("");
            System.out.print("Espansione: ");
            E = permutazione(destra,E,48);
            if (i == 1 || i == 2 || i == 9 || i == 16){
                CD = shift(CD);
            }
            else{
                CD = shift(CD);
                CD = shift(CD);
            }
            System.out.println("");
            System.out.print("C: ");
            for(int l = 0;l < 56;l++){
                System.out.print(CD[l]);
                if(l == 27){
                    System.out.print("  - D: ");
                }
            }
            int PC2[] = {
                            14,17,11,24,1,5,
                            3,28,15,6,21,10,
                            23,19,12,4,26,8,
                            16,7,27,20,13,2,
                            41,52,31,37,47,55,
                            30,40,51,45,33,48,
                            44,49,39,56,34,53,
                            46,42,50,36,29,32
                        };
            System.out.println("");
            System.out.print("K["+i+"]: ");
            PC2 = permutazione(CD,PC2,48);
            System.out.println("");
            System.out.print("XOR: ");
            for(int l = 0;l < 48;l++){
                E[l] = PC2[l]^E[l];
                System.out.print(E[l]);
            }
            E = evoluzioneSbox(E);
            System.out.print("Sbox finale: ");
            for(int l = 0;l < 32;l++){
                System.out.print(E[l]);
            }
            int PF[] = {
                            16,7,20,21,
                            29,12,28,17,
                            1,15,23,26,
                            5,18,31,10,
                            2,8,24,14,
                            32,27,3,9,
                            19,13,30,6,
                            22,11,4,25
                        };
            System.out.println("");
            System.out.print("Permutazione P: ");
            PF = permutazione(E,PF,32);
            System.out.println("");
            System.out.print("XOR finale (Nuova destra): ");
            for(int l = 0;l < 32;l++){
                PF[l] = sinistra[l]^PF[l];
                System.out.print(PF[l]);
            }
            System.out.println("");
            System.out.print("Nuova sinistra: ");
            for(int k = 0;k < 32;k++){
                System.out.print(destra[k]);
            }
            System.arraycopy(destra,0,IP,0,32);
            for(int l = 32;l < 64;l++){
                IP[l] = PF[l-32];
            }
            if(i == 16){
                System.arraycopy(PF,0,IP,0,32);
                for(int l = 32;l < 64;l++){
                    IP[l] = destra[l-32];
                }
            }
        }
        int IIP[] = {
                        40,8,48,16,56,24,64,32,
                        39,7,47,15,55,23,63,31,
                        38,6,46,14,54,22,62,30,
                        37,5,45,13,53,21,61,29,
                        36,4,44,12,52,20,60,28,
                        35,3,43,11,51,19,59,27,
                        34,2,42,10,50,18,58,26,
                        33,1,41,9,49,17,57,25
                    };
        IIP = permutazione(IP,IIP,64);
        return IIP;
    }
    
    // Metodo di applicazione del processo di decrittografia e di visualizzazione dei risultati parziali
    protected static int[] applicazioneDecrittografia(int[] testo,String chiave){
        int contatore = 16,inverso = 15;
        int[] chiaveInteri = new int[chiave.length()],destra,sinistra;
        int CD[] = {
                        57,49,41,33,25,17,9,
                        1,58,50,42,34,26,18,
                        10,2,59,51,43,35,27,
                        19,11,3,60,52,44,36,
                        63,55,47,39,31,23,15,
                        7,62,54,46,38,30,22,
                        14,6,61,53,45,37,29,
                        21,13,5,28,20,12,4
                    };
        
        int IP[] = {
                        58,50,42,34,26,18,10,2,
                        60,52,44,36,28,20,12,4,
                        62,54,46,38,30,22,14,6,
                        64,56,48,40,32,24,16,8,
                        57,49,41,33,25,17,9,1,
                        59,51,43,35,27,19,11,3,
                        61,53,45,37,29,21,13,5,
                        63,55,47,39,31,23,15,7
                    };
        int keysub[] = {
                            1,2,3,4,5,6,7,8,
                            9,10,11,12,13,14,15,16,
                            17,18,19,20,21,22,23,24,
                            25,26,27,28,29,30,31,32,
                            33,34,35,36,37,38,39,40,
                            41,42,43,44,45,46,47,48,
                            49,50,51,52,53,54,55,56,
                            57,58,59,60,61,62,63,64
                    };
        int[][] matriceChiave = new int[16][56];
        for(int i = 0;i < chiave.length();i++){
            chiaveInteri[i] = chiave.charAt(i)-'0';
        }
        System.out.print("Prima permutazione: ");
        IP = permutazione(testo,IP,64);
        System.out.println("");
        System.out.print("Keysub: ");
        keysub = permutazione(chiaveInteri,keysub,64);
        System.out.println("");
        System.out.print("CD: ");
        CD = permutazione(keysub,CD,56);
        for(int i = 0;i < 16;i++){
            if(i == 0 || i == 1 || i == 8 || i == 15){
                CD = shift(CD);
                System.arraycopy(CD,0,matriceChiave[i],0,56);
            }
            else{
                CD = shift(CD);
                CD = shift(CD);
                System.arraycopy(CD,0,matriceChiave[i],0,56);
            }
        }
        for(int i = 1;i <= 16;i++){
            System.out.println("");
            System.out.println("Passaggio "+i);
            System.out.println("");
            sinistra = Arrays.copyOfRange(IP,0,32);
            destra = Arrays.copyOfRange(IP,32,64);
            System.out.print("Sinistra: ");
            for(int j = 0;j < 32;j++){
                System.out.print(sinistra[j]);
            }
            System.out.println("");
            System.out.print("Destra: ");
            for(int k = 0;k < 32;k++){
                System.out.print(destra[k]);
            }
            int E[] = {
                            32,1,2,3,4,5,
                            4,5,6,7,8,9,
                            8,9,10,11,12,13,
                            12,13,14,15,16,17,
                            16,17,18,19,20,21,
                            20,21,22,23,24,25,
                            24,25,26,27,28,29,
                            28,29,30,31,32,1
                        };
            System.out.println("");
            System.out.print("Espansione: ");
            E = permutazione(destra,E,48);
            System.arraycopy(matriceChiave[inverso],0,CD,0,56);
            inverso--;
            System.out.println("");
            System.out.print("C: ");
            for(int l = 0;l < 56;l++){
                System.out.print(CD[l]);
                if(l == 27){
                    System.out.print("  - D: ");
                }
            }
            int PC2[] = {
                            14,17,11,24,1,5,
                            3,28,15,6,21,10,
                            23,19,12,4,26,8,
                            16,7,27,20,13,2,
                            41,52,31,37,47,55,
                            30,40,51,45,33,48,
                            44,49,39,56,34,53,
                            46,42,50,36,29,32
                        };
            System.out.println("");
            System.out.print("K["+contatore+"]: ");
            contatore--;
            PC2 = permutazione(CD,PC2,48);
            System.out.println("");
            System.out.print("XOR: ");
            for(int l = 0;l < 48;l++){
                E[l] = PC2[l]^E[l];
                System.out.print(E[l]);
            }
            E = evoluzioneSbox(E);
            System.out.print("Sbox finale: ");
            for(int l = 0;l < 32;l++){
                System.out.print(E[l]);
            }
            int PF[] = {
                            16,7,20,21,
                            29,12,28,17,
                            1,15,23,26,
                            5,18,31,10,
                            2,8,24,14,
                            32,27,3,9,
                            19,13,30,6,
                            22,11,4,25
                        };
            System.out.println("");
            System.out.print("Permutazione P: ");
            PF = permutazione(E,PF,32);
            System.out.print("XOR finale (Nuova destra): ");
            for(int l = 0;l < 32;l++){
                PF[l] = sinistra[l]^PF[l];
                System.out.print(PF[l]);
            }
            System.out.println("");
            System.out.print("Nuova sinistra: ");
            for(int k = 0;k < 32;k++){
                System.out.print(destra[k]);
            }
            System.arraycopy(destra,0,IP,0,32);
            for(int l = 32;l < 64;l++){
                IP[l] = PF[l-32];
            }
            if(i == 16){
                System.arraycopy(PF,0,IP,0,32);
                for(int l = 32;l < 64;l++){
                    IP[l] = destra[l-32];
                }
            }
        }
        int IIP[] = {
                        40,8,48,16,56,24,64,32,
                        39,7,47,15,55,23,63,31,
                        38,6,46,14,54,22,62,30,
                        37,5,45,13,53,21,61,29,
                        36,4,44,12,52,20,60,28,
                        35,3,43,11,51,19,59,27,
                        34,2,42,10,50,18,58,26,
                        33,1,41,9,49,17,57,25
                    };
        IIP = permutazione(IP,IIP,64);
        return IIP;
    }
}